package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class RecordInvertedIndexImplTest {
    RecordInvertedIndexImpl<Integer> rii;

    @Before
    public void setUp() {
        rii = new RecordInvertedIndexImpl<Integer>();
    }

    @Test
    public void getRecordsEmpty() {
        assertEquals(null, rii.getRecords("a"));
    }

    @Test
    public void doesNotContainGetRecord() {
        rii.addRecord("a", 0);
        assertEquals(null, rii.getRecords("b"));
    }

    @Test
    public void getRecord() {
        rii.addRecord("a", 0);
        HashSet<Integer> mapping = new HashSet<Integer>();
        mapping.add(0);
        assertEquals(mapping, rii.getRecords("a"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullKeyAddRecord() {
        rii.addRecord(null, 0);
    }

    @Test
    public void addRecord() {
        rii.addRecord("a", 0);
        assertTrue(rii.containsKey("a"));
        HashSet<Integer> mapping = new HashSet<Integer>();
        mapping.add(0);
        assertEquals(mapping, rii.getRecords("a"));
    }

    @Test
    public void addNullRecord() {
        rii.addRecord("a", null);
        assertTrue(rii.containsKey("a"));
        HashSet<Integer> mapping = new HashSet<Integer>();
        mapping.add(null);
        assertEquals(mapping, rii.getRecords("a"));
    }

    @Test
    public void containsKeyEmpty() {
        assertFalse(rii.containsKey(""));
    }

    @Test
    public void deleteDoesNotContainKey() {
        assertEquals(null, rii.deleteRecords(""));
    }

    @Test
    public void deleteContainsKeySingleMapping() {
        rii.addRecord("a", 0);
        HashSet<Integer> mapping = new HashSet<Integer>();
        mapping.add(0);
        assertEquals(mapping, rii.deleteRecords("a"));
        assertFalse(rii.containsKey("a"));
    }

    @Test
    public void deleteContainsKeyMultipleMappings() {
        rii.addRecord("a", 0);
        rii.addRecord("a", 1);
        rii.addRecord("a", 2);
        HashSet<Integer> mapping = new HashSet<Integer>();
        mapping.add(0);
        mapping.add(1);
        mapping.add(2);
        assertEquals(mapping, rii.deleteRecords("a"));
        assertFalse(rii.containsKey("a"));
    }

    @Test
    public void doesNotContainKey() {
        rii.addRecord("a", 0);
        assertFalse(rii.containsKey(""));
    }

    @Test
    public void containsKey() {
        rii.addRecord("a", 0);
        assertTrue(rii.containsKey("a"));
    }

    @Test
    public void containsKeyAfterRemove() {
        rii.addRecord("a", 0);
        rii.deleteRecords("a");
        assertFalse(rii.containsKey("a"));
    }

    @Test
    public void emptyKeySet() {
        HashSet<String> keys = new HashSet<String>();
        assertEquals(keys, rii.keySet());
    }

    @Test
    public void singleKeySet() {
        rii.addRecord("a", 0);
        HashSet<String> keys = new HashSet<String>();
        keys.add("a");
        assertEquals(keys, rii.keySet());
    }

    @Test
    public void multipleKeySet() {
        rii.addRecord("a", 0);
        rii.addRecord("a", 1);
        rii.addRecord("b", 100);
        rii.addRecord("c", 5);
        rii.addRecord("", 2);
        HashSet<String> keys = new HashSet<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");
        keys.add("");
        assertEquals(keys, rii.keySet());
    }

    @Test
    public void addThenDeleteKeySet() {
        rii.addRecord("a", 0);
        rii.deleteRecords("a");
        HashSet<String> keys = new HashSet<String>();
        assertEquals(keys, rii.keySet());
    }

    @Test
    public void emptySize() {
        assertEquals(0, rii.size());
    }

    @Test
    public void singleElementSize() {
        rii.addRecord("a", 0);
        assertEquals(1, rii.size());
    }

    @Test
    public void oneElementMultipleMappingsSameSize() {
        rii.addRecord("a", 0);
        rii.addRecord("a", 1);
        rii.addRecord("a", 4);
        rii.addRecord("a", 9);
        assertEquals(1, rii.size());
    }

    @Test
    public void multipleElementsSize() {
        rii.addRecord("a", 0);
        rii.addRecord("a", 1);
        rii.addRecord("b", 100);
        rii.addRecord("c", 5);
        rii.addRecord("", 2);
        assertEquals(4, rii.size());
    }

    @Test
    public void addThenDeleteSize() {
        rii.addRecord("a", 0);
        rii.deleteRecords("a");
        assertEquals(0, rii.size());
    }

    @Test
    public void nullKeyCount() {
        assertEquals(0, rii.count(null));
    }

    @Test
    public void doesNotContainKeyCount() {
        assertEquals(0, rii.count(""));
    }

    @Test
    public void containsOneMappingCount() {
        rii.addRecord("a", 0);
        assertEquals(1, rii.count("a"));
    }

    @Test
    public void containsMultipleMappingsCount() {
        rii.addRecord("a", 0);
        rii.addRecord("a", 1);
        rii.addRecord("a", 4);
        rii.addRecord("a", 9);
        assertEquals(4, rii.count("a"));
    }

    @Test
    public void addThenDeleteCount() {
        rii.addRecord("a", 0);
        rii.deleteRecords("a");
        assertEquals(0, rii.count("a"));
    }

}
