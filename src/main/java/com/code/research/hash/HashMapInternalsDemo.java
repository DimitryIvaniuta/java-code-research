package com.code.research.hash;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

/**
 * HashMap internals demo (Java 8+). Shows:
 *  - Lazy table init (table==null)
 *  - Array of bins (power-of-two length)
 *  - Per-bin list -> red-black tree (after 8 nodes when capacity >= 64)
 *  - Resize doubling & single-bit bin split
 *  - Fail-fast iterators
 *  - One null key and null values allowed
 *
 * Run with (JDK 16+): --add-opens java.base/java.util=ALL-UNNAMED
 */
@Slf4j
public class HashMapInternalsDemo {

    /* ------------ 1) Keys that collide into the same bin ------------ */

    /** Keys with identical hashCode but distinct equality -> same bin, growing chain/tree. */
    static final class CollidingKey {
        final int id;
        final int hash; // constant across all instances in this demo

        CollidingKey(int id, int hash) { this.id = id; this.hash = hash; }

        @Override public int hashCode() { return hash; }
        @Override public boolean equals(Object o) {
            return (o instanceof CollidingKey ck) && this.id == ck.id;
        }
        @Override public String toString() { return "K" + id; }
    }

    /* ------------ 2) Keys crafted to demonstrate resize "bit split" ------------ */

    /**
     * Two keys whose hashes differ by exactly the oldCapacity bit.
     * Before resize (capacity = oldCap), their bin indexes are equal;
     * after resize (capacity = 2*oldCap), they split into different bins.
     */
    static final class SplitKey {
        final String name;
        final int hash;
        SplitKey(String name, int hash) { this.name = name; this.hash = hash; }
        @Override public int hashCode() { return hash; }
        @Override public boolean equals(Object o) {
            return (o instanceof SplitKey sk) && this.name.equals(sk.name);
        }
        @Override public String toString() { return name; }

        static SplitKey ofBase(String name, int base) { return new SplitKey(name, base); }
        static SplitKey ofBasePlusCap(String name, int base, int oldCapBit) {
            return new SplitKey(name, base | oldCapBit);
        }
    }

    /* ------------ Helpers to introspect HashMap bins (reflection) ------------ */

    private static Object[] tableOf(HashMap<?,?> m) {
        try {
            Field f = HashMap.class.getDeclaredField("table");
            f.setAccessible(true);
            return (Object[]) f.get(m);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Set --add-opens java.base/java.util=ALL-UNNAMED", e);
        }
    }

    private static Object nextOf(Object node) {
        try {
            Field nf = node.getClass().getDeclaredField("next"); // for Node and TreeNode
            nf.setAccessible(true);
            return nf.get(node);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    /** Count chain by following 'next' pointers (works for list bins and tree bins' linked iteration chain). */
    private static int chainLength(Object head) {
        int n = 0;
        for (Object x = head; x != null; x = nextOf(x)) n++;
        return n;
    }

    private static boolean isTreeBin(Object head) {
        return head != null && head.getClass().getName().contains("TreeNode");
    }

    /** Print a compact view of non-empty bins: index, type (LIST/TREE), and chain size. */
    private static void dumpBins(HashMap<?,?> m, String label) {
        Object[] table = tableOf(m);
        System.out.println("\n-- " + label + " --");
        System.out.println("capacity (table.length) = " + (table == null ? "null (lazy)" : table.length));
        if (table == null) return;

        for (int i = 0; i < table.length; i++) {
            Object head = table[i];
            if (head != null) {
                String mode = isTreeBin(head) ? "TREE" : "LIST";
                int len = chainLength(head);
                System.out.printf("  bin[%d] : %s, nodes=%d, headClass=%s%n",
                        i, mode, len, head.getClass().getSimpleName());
            }
        }
    }

    /* ------------ Hash index helper (same mixing as HashMap) ------------ */
    private static int spreadIndex(int hash, int length) {
        int h = hash ^ (hash >>> 16);
        return h & (length - 1);
    }

    public static void main(String[] args) {
        /* ========== PHASE A: Lazy init, null key/values, fail-fast ========== */

        HashMap<String, String> base = new HashMap<>(); // table is not allocated yet
        dumpBins(base, "A1) Fresh map (lazy table: null)");

        // one null key + null values allowed
        base.put(null, null);
        base.put("x", null);
        base.put("y", "Y");
        dumpBins(base, "A2) After some puts (table allocated on first put)");
        System.out.println("get(null) = " + base.get(null));

        // Fail-fast iterator: structural modification during iteration throws CME
        try {
            Iterator<Map.Entry<String,String>> it = base.entrySet().iterator();
            if (it.hasNext()) {
                Map.Entry<String,String> e = it.next();
                System.out.println("Iterating first entry = " + e);
                base.put("z", "Z");  // structural change during iteration
                it.next();           // triggers ConcurrentModificationException
            }
        } catch (ConcurrentModificationException cme) {
            System.out.println("Fail-fast iterator observed: " + cme.getClass().getSimpleName());
        }

        /* ========== PHASE B: Treeification (list -> red-black tree) ========== */

        // Make capacity >= 64 so treeification will happen instead of resize-on-collision.
        HashMap<CollidingKey, String> collide = new HashMap<>(64);
        int fixedHash = 0x1234_5678; // all keys share the same hash -> same bin
        // Insert 7 colliding keys -> still a LIST bin
        for (int i = 1; i <= 7; i++) collide.put(new CollidingKey(i, fixedHash), "V" + i);
        dumpBins(collide, "B1) 7 colliding keys (still LIST)");

        // Insert the 8th colliding key -> treeify to red-black TREE bin (since capacity >= 64)
        collide.put(new CollidingKey(8, fixedHash), "V8");
        dumpBins(collide, "B2) 8th colliding key (TREE bin expected)");

        // (Optional) Drop below UNTREEIFY_THRESHOLD by removing entries -> may revert to LIST
        collide.remove(new CollidingKey(1, fixedHash));
        collide.remove(new CollidingKey(2, fixedHash));
        dumpBins(collide, "B3) After removals (may UNTREEIFY if small enough)");

        /* ========== PHASE C: Resize doubling & one-bit bin split ========== */

        // Start with capacity 16 (power of two). Threshold = 16 * 0.75 = 12 entries.
        HashMap<SplitKey, String> resizeMap = new HashMap<>(16, 0.75f);

        // Prepare two keys that collide at capacity 16 but split after resize to 32.
        int oldCap = 16;                  // bit to toggle is 0b1_0000 = 16
        int baseHash = 0x0000_0001;       // low bits only (index depends on low 4 bits pre-resize)
        SplitKey a = SplitKey.ofBase("A", baseHash);
        SplitKey b = SplitKey.ofBasePlusCap("B", baseHash, oldCap); // differs by oldCap bit

        // Put A,B first (same bin at length=16). We'll prove with spreadIndex() after we can read table length.
        resizeMap.put(a, "va");
        resizeMap.put(b, "vb");

        // Fill with more entries to exceed threshold and force resize to 32.
        for (int i = 0; i < 20; i++) {
            resizeMap.put(new SplitKey("F"+i, i+1000), "f"+i);
        }

        dumpBins(resizeMap, "C1) After resize due to threshold (capacity should be 32)");

        // Show how the single high bit causes split:
        Object[] table = tableOf(resizeMap);
        int cap = table.length;
        int idxA = spreadIndex(a.hashCode(), cap);
        int idxB = spreadIndex(b.hashCode(), cap);
        System.out.printf("C2) Index(A)=%d, Index(B)=%d at capacity=%d (should differ now)%n", idxA, idxB, cap);

        /* ========== Recap printed at the end ========== */

        System.out.println("\n=== RECAP ===");
        System.out.println("• Array of bins: table length is always power-of-two; printed per-phase.");
        System.out.println("• Per-bin LIST -> TREE at 8+ nodes when capacity ≥ 64 (see B1->B2).");
        System.out.println("• Resize doubled capacity and split a bin by one high bit (see C1/C2).");
        System.out.println("• Iterators are fail-fast (see A2).");
        System.out.println("• One null key and null values allowed (see A2 get(null)).");
        System.out.println("Time: " + Instant.now());
    }
}
