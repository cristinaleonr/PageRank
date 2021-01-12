package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.index.RecordInvertedIndex;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a {@link RecordInvertedIndex}. For testing purposes, this
 * class must provide a public default no-argument constructor. This means that
 * you do not need to write any constructor at all; if you do write other
 * constructors, be sure you have one that is public and takes no arguments.
 *
 * @param <T>
 *            the type of the values in the inverted index
 * @author davix
 */
public class RecordInvertedIndexImpl<T> implements RecordInvertedIndex<String, T> {
    HashMap<String, Set<T>> multimap = new HashMap<String, Set<T>>();

    @Override
    public void addRecord(String key, T t) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (containsKey(key)) {
            multimap.get(key).add(t);
        } else {
            HashSet<T> mapping = new HashSet<T>();
            mapping.add(t);
            multimap.put(key, mapping);
        }
    }

    @Override
    public Collection<T> getRecords(String key) {
        if (!containsKey(key)) {
            return null;
        }
        return multimap.get(key);
    }

    @Override
    public Collection<T> deleteRecords(String key) {
        if (!containsKey(key)) {
            return null;
        }
        Collection<T> removed = multimap.get(key);
        multimap.remove(key);
        return removed;
    }

    @Override
    public boolean containsKey(String key) {
        return multimap.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return multimap.keySet();
    }

    @Override
    public int size() {
        return multimap.size();
    }

    @Override
    public int count(String key) {
        if (key == null || !containsKey(key)) {
            return 0;
        }
        return multimap.get(key).size();
    }
}
