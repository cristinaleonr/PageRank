package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PageVertexTest {
    PageVertex v;

    @Before
    public void setUp() {
        v = new PageVertex("");
    }

    @Test
    public void newVertex() {
        assertTrue(v.getIn().isEmpty());
        assertTrue(v.getOut().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInLink() {
        v.addInLink(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullOutLink() {
        v.addOutLink(null);
    }

    @Test
    public void addIn() {
        String s = "hi";
        v.addInLink(s);
        HashSet<String> set = new HashSet<String>();
        set.add(s);
        assertEquals(v.getIn(), set);
    }

    @Test
    public void addOut() {
        String s = "hi";
        v.addOutLink(s);
        HashSet<String> set = new HashSet<String>();
        set.add(s);
        assertEquals(v.getOut(), set);
    }
}
