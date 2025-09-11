package com.code.research.customhash;

/**
 * CustomSortedMap: extends CustomMap with sorted-key operations.
 */
public interface CustomSortedMap<K, V> extends CustomMap<K, V> {

    /**
     * returns smallest key
     *
     * @return K
     */
    K firstKey();

    /**
     * returns largest key
     *
     * @return K
     */
    K lastKey();

    /**
     * range view
     *
     * @param fromKey K
     * @param toKey   K
     * @return CustomSortedMap<K, V>
     */
    CustomSortedMap<K, V> subMap(K fromKey, K toKey);

}
