package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.graph.ForeignVertexException;

public class MyDirectedGraphImplTest {
    MyDirectedGraphImpl g;

    @Before
    public void setUp() {
        g = new MyDirectedGraphImpl();
    }

    @Test
    public void addVertex() {
        PageVertex v = new PageVertex("");
        g.addVertex(v);
        assertTrue(g.vertexSet().contains(v));
    }

    @Test
    public void addSameVertexTwice() {
        PageVertex v = new PageVertex("");
        PageVertex v2 = v;
        g.addVertex(v);
        g.addVertex(v2);
        assertTrue(g.vertexSet().contains(v));
        assertEquals(1, g.vertexSet().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNull() {
        g.checkNull(null);
    }

    @Test(expected = ForeignVertexException.class)
    public void exists() {
        g.exists(new PageVertex(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void inNull() {
        g.inNeighbors(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outNull() {
        g.outNeighbors(null);
    }

    @Test(expected = ForeignVertexException.class)
    public void inDoesNotExist() {
        g.inNeighbors(new PageVertex(""));
    }

    @Test(expected = ForeignVertexException.class)
    public void outDoesNotExist() {
        g.outNeighbors(new PageVertex(""));
    }

    @Test
    public void getInNeighbors() {
        PageVertex v = new PageVertex("");
        PageVertex v2 = new PageVertex("a");
        g.addVertex(v);
        g.addVertex(v2);
        v.addInLink("a");
        HashSet<PageVertex> in = new HashSet<PageVertex>();
        in.add(v2);
        assertEquals(in, g.inNeighbors(v));
    }

    @Test
    public void getOutNeighbors() {
        PageVertex v = new PageVertex("");
        PageVertex v2 = new PageVertex("a");
        g.addVertex(v);
        g.addVertex(v2);
        v.addOutLink("a");
        HashSet<PageVertex> out = new HashSet<PageVertex>();
        out.add(v2);
        assertEquals(out, g.outNeighbors(v));
    }

    @Test
    public void getBoth() {
        PageVertex v = new PageVertex("");
        PageVertex v2 = new PageVertex("a");
        g.addVertex(v);
        g.addVertex(v2);
        v.addOutLink("a");
        v2.addInLink("");
        HashSet<PageVertex> in = new HashSet<PageVertex>();
        in.add(v);
        HashSet<PageVertex> out = new HashSet<PageVertex>();
        out.add(v2);
        assertEquals(out, g.outNeighbors(v));
        assertEquals(in, g.inNeighbors(v2));
    }
}
