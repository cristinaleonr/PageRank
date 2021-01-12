package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.data.BinaryMinHeap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V>
 *            {@inheritDoc}
 * @param <Key>
 *            {@inheritDoc}
 *
 * @author joncho, 16sp
 */
public class BinaryMinHeapImpl<V, Key extends Comparable<Key>> implements BinaryMinHeap<V, Key> {
    private int size;
    ArrayList<Entry<V, Key>> pq;
    HashMap<V, Key> mappings;
    HashMap<V, Integer> indices;

    public BinaryMinHeapImpl() {
        pq = new ArrayList<Entry<V, Key>>();
        pq.add(null);
        mappings = new HashMap<V, Key>();
        indices = new HashMap<V, Integer>();
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (size() <= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return mappings.containsKey(value);
    }

    void swimUp(int i) {

        while (i > 1 && ((pq.get(i).getKey().compareTo(pq.get(i / 2).getKey())) < 0)) {
            Entry<V, Key> child = pq.get(i);
            Entry<V, Key> parent = pq.get(i / 2);

            pq.set(i, parent);
            pq.set(i / 2, child);

            indices.put(child.getValue(), i / 2);
            indices.put(parent.getValue(), i);

            i = i / 2;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(V value, Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (containsValue(value)) {
            throw new IllegalArgumentException();
        }
        mappings.put(value, key);
        size++;
        indices.put(value, size);
        pq.add(new Entry<V, Key>(value, key));
        swimUp(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null || mappings.get(value).compareTo(newKey) < 0) {
            throw new IllegalArgumentException();
        }

        mappings.remove(value);
        mappings.put(value, newKey);
        pq.set(indices.get(value), new Entry<V, Key>(value, newKey));
        swimUp(indices.get(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V peek() {
        V value = null;
        if (size() > 0) {
            value = pq.get(1).getValue();
        }
        return value;
    }

    void swimDown() {
        int i = 1;

        while (hasLeft(i)) {
            int leftIndex = i * 2;
            int rightIndex = i * 2 + 1;
            int smaller = leftIndex;

            if (hasRight(i)
                    && pq.get(rightIndex).getKey().compareTo(pq.get(leftIndex).getKey()) < 0) {
                smaller = rightIndex;
            }
            if (pq.get(i).getKey().compareTo(pq.get(smaller).getKey()) > 0) {
                Entry<V, Key> temp = pq.get(i);
                pq.set(i, pq.get(smaller));
                pq.set(smaller, temp);

                indices.put(temp.value, smaller);
                indices.put(pq.get(smaller).value, i);

                i = smaller;
            } else {
                break;
            }

        }

    }

    boolean hasLeft(int i) {
        return ((i * 2) <= size);
    }

    boolean hasRight(int i) {
        return ((i * 2 + 1) <= size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        V value = pq.get(1).getValue();
        mappings.remove(pq.get(1).getValue());
        indices.remove(pq.get(1).getValue());
        pq.set(1, pq.get(size));
        pq.set(size(), null);
        size--;
        swimDown();

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        return mappings.keySet();
    }

    /**
     * Helper entry class for maintaining value-key pairs. The underlying
     * indexed list for your heap will contain these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A value;
        private B key;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return the value stored in the entry
         */
        public A getValue() {
            return this.value;
        }

        /**
         * @return the key stored in the entry
         */
        public B getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key
         *            the new key
         * @return the old key
         */
        public B setKey(B key) {
            B oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}
