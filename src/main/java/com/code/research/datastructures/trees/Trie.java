package com.code.research.datastructures.trees;


import java.util.HashMap;
import java.util.Map;

/**
 * Trie is a prefix tree that supports efficient insertion and search of strings,
 * along with prefix matching.
 */
public class Trie {

    /**
     * TrieNode represents a single node in the Trie.
     */
    public static class TrieNode {
        /** Map of child nodes indexed by characters. */
        private final Map<Character, TrieNode> children;
        /** Indicates whether this node marks the end of a valid word. */
        private boolean isEndOfWord;

        /**
         * Constructs a new TrieNode.
         */
        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    /** The root node of the Trie. */
    private final TrieNode root;

    /**
     * Constructs an empty Trie.
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie.
     *
     * @param word the word to insert.
     */
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    /**
     * Searches for a word in the Trie.
     *
     * @param word the word to search for.
     * @return true if the word exists in the Trie, false otherwise.
     */
    public boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord;
    }

    /**
     * Checks if there is any word in the Trie that starts with the given prefix.
     *
     * @param prefix the prefix to check.
     * @return true if at least one word with the given prefix exists, false otherwise.
     */
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

}