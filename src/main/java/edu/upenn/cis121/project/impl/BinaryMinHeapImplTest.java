package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.data.BinaryMinHeap;

public class BinaryMinHeapImplTest {
    BinaryMinHeap<Character, Integer> bmh;

    @Before
    public void setUp() {
        bmh = new BinaryMinHeapImpl<Character, Integer>();
    }

    @Test
    public void emptySize() {
        assertEquals(0, bmh.size());
    }

    @Test
    public void isEmptyTrue() {
        assertTrue(bmh.isEmpty());
    }

    @Test
    public void emptyDoesNotContainValue() {
        assertFalse(bmh.containsValue('a'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullKey() {
        bmh.add('a', null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addValueAlreadyPresent() {
        bmh.add('a', 0);
        bmh.add('a', 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void decreaseDoesNotContainValue() {
        bmh.decreaseKey('a', 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newKeyIsNull() {
        bmh.add('a', 1);
        bmh.decreaseKey('a', null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decreaseKeyIsGreater() {
        bmh.add('a', 1);
        bmh.decreaseKey('a', 2);
    }

    @Test
    public void peekEmpty() {
        assertEquals(null, bmh.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void extractMinEmpty() {
        bmh.extractMin();
    }

    @Test
    public void emptyValues() {
        assertEquals(new HashSet<Integer>(), bmh.values());
    }

    @Test
    public void addSize() {
        bmh.add('a', 0);
        assertEquals(1, bmh.size());
    }

    @Test
    public void addThenRemoveSize() {
        bmh.add('a', 0);
        bmh.extractMin();
        assertEquals(0, bmh.size());
    }

    @Test
    public void addIsNotEmpty() {
        bmh.add('a', 0);
        assertFalse(bmh.isEmpty());
    }

    @Test
    public void addThenRemoveIsEmptyAgain() {
        bmh.add('a', 0);
        bmh.extractMin();
        assertTrue(bmh.isEmpty());
    }

    @Test
    public void containsValue() {
        bmh.add('a', 0);
        assertTrue(bmh.containsValue('a'));
    }

    @Test
    public void containsNullValue() {
        bmh.add(null, 0);
        assertTrue(bmh.containsValue(null));
    }

    @Test
    public void decreaseKeyMinElement() {
        bmh.add('a', 0);
        bmh.add('b', 1);
        bmh.decreaseKey('a', -1);
        Character a = 'a';
        assertEquals(a, bmh.peek());
    }

    @Test
    public void decreaseKeyLastElement() {
        bmh.add('a', 0);
        bmh.add('b', 1);
        bmh.decreaseKey('b', -1);
        Character b = 'b';
        assertEquals(b, bmh.peek());
    }

    @Test
    public void decreaseKeyRepeatedKey() {
        bmh.add('a', 0);
        bmh.add('b', 0);
        bmh.decreaseKey('b', -1);
        Character b = 'b';
        assertEquals(b, bmh.peek());
    }

    @Test
    public void decreaseKeyNotSmallerThanMin() {
        bmh.add('a', 0);
        bmh.add('b', 1);
        bmh.decreaseKey('b', 0);
        Character a = 'a';
        assertEquals(a, bmh.peek());
    }

    @Test
    public void peekNonEmpty() {
        bmh.add('a', 0);
        Character a = 'a';
        assertEquals(a, bmh.peek());
    }

    @Test
    public void peekNull() {
        bmh.add(null, 0);
        assertEquals(null, bmh.peek());
    }

    @Test
    public void extractMinSingleElement() {
        bmh.add('a', 0);
        Character a = 'a';
        assertEquals(a, bmh.extractMin());
        assertTrue(bmh.isEmpty());
        assertEquals(0, bmh.size());
    }

    @Test
    public void extractMinMultipleElements() {
        bmh.add('a', 0);
        bmh.add('b', 1);
        bmh.add('c', 2);
        Character a = 'a';
        assertEquals(a, bmh.extractMin());
        Character b = 'b';
        assertEquals(b, bmh.extractMin());
        Character c = 'c';
        assertEquals(c, bmh.extractMin());
    }

    @Test
    public void extractMinMultipleElementsRepeatedKey() {
        bmh.add('a', 0);
        bmh.add('b', 1);
        bmh.add('c', 1);
        Character a = 'a';
        assertEquals(a, bmh.extractMin());
        Character b = 'b';
        Character c = 'c';
        assertEquals(c, bmh.extractMin());
        assertEquals(b, bmh.extractMin());
    }

    @Test
    public void onlineTest() {
        bmh.add('a', 10);
        bmh.add('b', 5);
        bmh.add('c', 7);
        bmh.decreaseKey('a', 4);
        Character a = 'a';
        assertEquals(a, bmh.extractMin());
    }

}
