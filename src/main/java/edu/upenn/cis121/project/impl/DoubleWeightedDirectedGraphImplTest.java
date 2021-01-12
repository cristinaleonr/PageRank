package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class DoubleWeightedDirectedGraphImplTest {
    DoubleWeightedDirectedGraphImpl<Integer> g;

    @Before
    public void setUp() {
        g = new DoubleWeightedDirectedGraphImpl<Integer>();
    }

    @Test
    public void emptySize() {
        assertEquals(0, g.getSize());
    }

    @Test
    public void nonEmptySize() {
        g.addVertex(1);
        assertEquals(1, g.getSize());
    }

    @Test
    public void falseVertexExists() {
        assertFalse(g.vertexExists(1));
    }

    @Test
    public void trueVertexExists() {
        g.addVertex(1);
        assertTrue(g.vertexExists(1));
    }

    @Test
    public void falseHasEdge() {
        g.addVertex(0);
        g.addVertex(1);
        assertFalse(g.hasEdge(0, 1));
    }

    @Test
    public void trueHasEdge() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        assertTrue(g.hasEdge(0, 1));
    }

    @Test
    public void emptyGetNeighbors() {
        g.addVertex(0);
        assertEquals(new HashSet<Integer>(), g.neighbors(0));
    }

    @Test
    public void nonEmptyGetNeighbors() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        HashSet<Integer> n = new HashSet<Integer>();
        n.add(1);
        assertEquals(n, g.neighbors(0));
    }

    @Test
    public void emptyInNeighbors() {
        g.addVertex(0);
        assertEquals(new HashSet<Integer>(), g.inNeighbors(0));
    }

    @Test
    public void nonEmptyInNeighbors() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        HashSet<Integer> n = new HashSet<Integer>();
        n.add(0);
        assertEquals(n, g.inNeighbors(1));
    }

    @Test
    public void emptyOutNeighbors() {
        g.addVertex(0);
        assertEquals(new HashSet<Integer>(), g.outNeighbors(0));
    }

    @Test
    public void nonEmptyOutNeighbors() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        HashSet<Integer> n = new HashSet<Integer>();
        n.add(1);
        assertEquals(n, g.outNeighbors(0));
        assertEquals(g.outNeighbors(0), g.neighbors(0));
    }

    @Test
    public void emptyVertexSet() {
        assertEquals(new HashSet<Integer>(), g.vertexSet());
    }

    @Test
    public void nonEmptyVertexSet() {
        g.addVertex(0);
        HashSet<Integer> vs = new HashSet<Integer>();
        vs.add(0);
        assertEquals(vs, g.vertexSet());
    }

    @Test
    public void addTwiceVertexSet() {
        g.addVertex(0);
        g.addVertex(0);
        HashSet<Integer> vs = new HashSet<Integer>();
        vs.add(0);
        assertEquals(vs, g.vertexSet());
    }

    @Test
    public void getWeightNoEdge() {
        g.addVertex(0);
        g.addVertex(1);
        assertEquals(Optional.empty(), g.getWeight(0, 1));
    }

    @Test
    public void getExistingWeight() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        assertEquals(Optional.of(0.0), g.getWeight(0, 1));
    }

}
