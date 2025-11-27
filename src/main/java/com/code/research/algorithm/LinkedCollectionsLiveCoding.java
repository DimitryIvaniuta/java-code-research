package com.code.research.algorithm;

import java.util.*;

/**
 * Must-know live-coding patterns with **LinkedHashMap** (“linked map”) and **LinkedList** (“linked list”).
 * Short, obvious, and interview-ready. Each snippet is self-contained.
 */
public final class LinkedCollectionsLiveCoding {

    /* ------------------------------------------------------------------
     * 1) LRU Cache (tiny) – LinkedHashMap with accessOrder=true + manual eviction
     *    get/put: O(1) average
     * ------------------------------------------------------------------ */
    public static final class LRUCache {
        private final int cap;                                       // max size
        private final LinkedHashMap<Integer, Integer> m =
                new LinkedHashMap<>(16, 0.75f, true);                // true ⇒ move on access

        public LRUCache(int capacity) {
            this.cap = capacity;
        }

        public int get(int k) {                                      // returns -1 if not found
            Integer v = m.get(k);                                    // also bumps recency
            return v == null ? -1 : v;
        }

        public void put(int k, int v) {
            m.put(k, v);                                             // insert/update
            if (m.size() > cap) {                                    // evict LRU (first entry)
                var it = m.entrySet().iterator();
                it.next();
                it.remove();
            }
        }
    }

    /* ------------------------------------------------------------------
     * 2) Order-preserving frequency (first-appearance order) – LinkedHashMap
     * ------------------------------------------------------------------ */
    public static Map<String, Integer> wordFreqPreserveOrder(String text) {
        Map<String, Integer> f = new LinkedHashMap<>();               // insertion order
        for (String w : text.toLowerCase().split("[^a-z0-9]+")) {
            if (!w.isEmpty()) f.merge(w, 1, Integer::sum);           // count
        }
        return f;                                                    // keeps first-seen order
    }

    /* ------------------------------------------------------------------
     * 3) Deduplicate while keeping first occurrences – LinkedHashSet
     * ------------------------------------------------------------------ */
    public static <T> List<T> dedupeKeepOrder(List<T> in) {
        return new ArrayList<>(new LinkedHashSet<>(in));             // unique + insertion order
    }

    /* ------------------------------------------------------------------
     * 4) Recent N items (MRU list) – LinkedList as deque
     * ------------------------------------------------------------------ */
    public static final class RecentN<T> {
        private final int cap;
        private final LinkedList<T> q = new LinkedList<>();          // head = oldest, tail = newest

        public RecentN(int cap) {
            this.cap = cap;
        }

        public void add(T x) {
            q.addLast(x);                                            // push newest
            if (q.size() > cap) q.removeFirst();                     // drop oldest
        }

        public List<T> snapshot() {
            return List.copyOf(q);
        }         // immutable view
    }

    /* ------------------------------------------------------------------
     * 5) Queue & Stack via LinkedList – O(1) ends
     * ------------------------------------------------------------------ */
    public static final class MyQueue<T> {
        private final LinkedList<T> d = new LinkedList<>();

        public void offer(T x) {
            d.addLast(x);
        }                     // enqueue

        public T poll() {
            return d.isEmpty() ? null : d.removeFirst();
        }

        public T peek() {
            return d.peekFirst();
        }
    }

    public static final class MyStack<T> {
        private final LinkedList<T> d = new LinkedList<>();

        public void push(T x) {
            d.addFirst(x);
        }                     // push

        public T pop() {
            return d.isEmpty() ? null : d.removeFirst();
        }

        public T top() {
            return d.peekFirst();
        }
    }

    /* ------------------------------------------------------------------
     * 6) Remove duplicates from singly linked list (keep first) – LinkedHashSet
     * ------------------------------------------------------------------ */
    public static ListNode removeDupKeepFirst(ListNode head) {
        Set<Integer> seen = new LinkedHashSet<>();
        ListNode dummy = new ListNode(0), tail = dummy;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            if (seen.add(cur.val)) {                                 // first time seen
                tail.next = new ListNode(cur.val);                   // rebuild clean list
                tail = tail.next;
            }
        }
        return dummy.next;
    }

    /* ------------------------------------------------------------------
     * 7) Reverse a singly linked list – iterative O(n), O(1) extra
     * ------------------------------------------------------------------ */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;                                 // save next
            cur.next = prev;                                         // reverse link
            prev = cur;                                              // advance prev
            cur = nxt;                                               // advance cur
        }
        return prev;
    }

    /* ------------------------------------------------------------------
     * 8) Middle of linked list – fast/slow pointers
     * ------------------------------------------------------------------ */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;                                                 // second middle for even length
    }

    /* ------------------------------------------------------------------
     * 9) Merge two sorted linked lists – classic
     * ------------------------------------------------------------------ */
    public static ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode d = new ListNode(0), t = d;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                t.next = a;
                a = a.next;
            } else {
                t.next = b;
                b = b.next;
            }
            t = t.next;
        }
        t.next = (a != null) ? a : b;
        return d.next;
    }

    /* ------------------------------------------------------------------
     * 10) Detect cycle in linked list – Floyd O(n)/O(1)
     * ------------------------------------------------------------------ */
    public static boolean hasCycle(ListNode head) {
        ListNode s = head, f = head;
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
            if (s == f) return true;
        }
        return false;
    }

    /* ------------------------------------------------------------------
     * 11) Ordered dictionary w/ “moveToEnd on get” – LinkedHashMap
     *     (Like MRU list; accessOrder=true)
     * ------------------------------------------------------------------ */
    public static final class OrderedDict<K, V> {
        private final LinkedHashMap<K, V> m = new LinkedHashMap<>(16, 0.75f, true);

        public void put(K k, V v) {
            m.put(k, v);
        }                   // insert/update

        public V get(K k) {
            return m.get(k);
        }                // moves k to end if present

        public List<K> keysInAccessOrder() {
            return new ArrayList<>(m.keySet());
        }
    }

    /* ------------------------------------------------------------------
     * 12) First unique character (streaming) – LinkedHashMap preserves order
     * ------------------------------------------------------------------ */
    public static char firstUniqueChar(String s) {
        Map<Character, Integer> f = new LinkedHashMap<>();
        for (char c : s.toCharArray()) f.merge(c, 1, Integer::sum);  // count in order
        for (var e : f.entrySet()) if (e.getValue() == 1) return e.getKey();
        return '\0';
    }

    /* ------------------------------ Support ------------------------------ */
    public static final class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int v) {
            val = v;
        }

        public ListNode(int v, ListNode n) {
            val = v;
            next = n;
        }

        @Override
        public String toString() {                         // print chain
            StringBuilder sb = new StringBuilder();
            for (ListNode cur = this; cur != null; cur = cur.next) {
                sb.append(cur.val).append(cur.next != null ? "->" : "");
            }
            return sb.toString();
        }
    }

    /* -------------------------------- demo ------------------------------- */
    public static void main(String[] args) {
        // 1) LRU
        LRUCache lru = new LRUCache(2);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.get(1);
        lru.put(3, 3);
        System.out.println(lru.get(2));                                // -1 (evicted)

        // 2) Order-preserving freq
        System.out.println(wordFreqPreserveOrder("To be, or not to be.")); // {to=2, be=2, or=1, not=1}

        // 3) Dedup keep order
        System.out.println(dedupeKeepOrder(List.of(3, 1, 3, 2, 1)));        // [3,1,2]

        // 4) Recent N
        RecentN<Integer> recent = new RecentN<>(3);
        recent.add(1);
        recent.add(2);
        recent.add(3);
        recent.add(4);
        System.out.println(recent.snapshot());                          // [2,3,4]

        // 5) Queue/Stack
        MyQueue<Integer> q = new MyQueue<>();
        q.offer(10);
        q.offer(20);
        System.out.println(q.poll()); // 10
        MyStack<Integer> st = new MyStack<>();
        st.push(7);
        st.push(9);
        System.out.println(st.top());  // 9

        // 6–10) Linked list utilities
        ListNode a = new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(2))));
        System.out.println(removeDupKeepFirst(a));                       // 1->2
        System.out.println(reverseList(new ListNode(1, new ListNode(2, new ListNode(3))))); // 3->2->1
        System.out.println(middleNode(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))))).val); // 3
        System.out.println(mergeTwoLists(new ListNode(1, new ListNode(3)), new ListNode(2, new ListNode(4))));  // 1->2->3->4
        System.out.println(hasCycle(new ListNode(1, new ListNode(2))));  // false

        // 11) Ordered dict (access order)
        OrderedDict<Integer, String> od = new OrderedDict<>();
        od.put(1, "a");
        od.put(2, "b");
        od.get(1);                        // 1 becomes most recent
        System.out.println(od.keysInAccessOrder());                     // [2, 1]

        // 12) First unique char
        System.out.println(firstUniqueChar("aabbcddee"));               // 'c'
    }
}
