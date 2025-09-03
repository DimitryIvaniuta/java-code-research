package com.code.research.hash;

public class HashMapBinMathDemo {
    static int spread(int h) { return h ^ (h >>> 16); }
    static int idx(Object key, int cap) {
        int h = (key == null ? 0 : key.hashCode());
        return spread(h) & (cap - 1);
    }
    public static void main(String[] args) {
        int oldCap = 16, newCap = oldCap * 2;             // power of two
        int baseHash = 0x0000_0001;
        Object A = new Object(){ public int hashCode(){ return baseHash; } };
        Object B = new Object(){ public int hashCode(){ return baseHash | oldCap; } }; // differs by oldCap bit

        System.out.printf("Before resize (cap=%d): idx(A)=%d, idx(B)=%d%n",
                oldCap, idx(A, oldCap), idx(B, oldCap));      // same bin
        System.out.printf("After  resize (cap=%d): idx(A)=%d, idx(B)=%d%n",
                newCap, idx(A, newCap), idx(B, newCap));      // split bins

        // Treeify rule (conceptual): at capacity >= 64, 8+ nodes in a bin -> tree
        int cap = 64;
        int collisions = 0;
        int hash = 0x1234_5678;
        int index = idx(hash, cap);
        for (int i=1; i<=10; i++) {
            collisions++;
            boolean tree = (collisions >= 8) && (cap >= 64);
            System.out.printf("bin[%d] nodes=%d -> %s%n", index, collisions, tree ? "TREE" : "LIST");
        }
    }
}
