package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ShortestPathImplTest {
    ShortestPathImpl<Integer> sp;
    DoubleWeightedDirectedGraphImpl<Integer> g;

    @Before
    public void setUp() {
        sp = new ShortestPathImpl<Integer>();
        g = new DoubleWeightedDirectedGraphImpl<Integer>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullGraph() {
        sp.getShortestPath(null, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSource() {
        sp.getShortestPath(g, null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTarget() {
        sp.getShortestPath(g, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void graphDoesNotContainSrc() {
        g.addVertex(1);
        sp.getShortestPath(g, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void graphDoesNotContainTgt() {
        g.addVertex(0);
        sp.getShortestPath(g, 0, 1);
    }

    @Test
    public void srcEqualsTgt() {
        g.addVertex(0);
        assertEquals(new LinkedList<Integer>(), sp.getShortestPath(g, 0, 0));
    }

    @Test
    public void noPath() {
        g.addVertex(0);
        g.addVertex(1);
        assertEquals(new LinkedList<Integer>(), sp.getShortestPath(g, 0, 1));
    }

    @Test
    public void adjacentVertices() {
        g.addVertex(0);
        g.addVertex(1);
        g.addEdge(0, 1, 0.0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(1);
        assertEquals(path, sp.getShortestPath(g, 0, 1));
    }

    @Test
    public void srcAndTgtTreeLeaves() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 2, 0.0);
        g.addEdge(2, 1, 2.0);
        g.addEdge(2, 3, 1.0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(1);
        assertEquals(path, sp.getShortestPath(g, 0, 1));
    }

    @Test
    public void cycleDiffWeights() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 2, 0.0);
        g.addEdge(0, 3, 1.0);
        g.addEdge(2, 1, 2.0);
        g.addEdge(3, 1, 3.0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(1);
        assertEquals(path, sp.getShortestPath(g, 0, 1));
    }

    @Test
    public void cycleSameWeights() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 2, 2.0);
        g.addEdge(0, 3, 1.0);
        g.addEdge(2, 1, 2.0);
        g.addEdge(3, 1, 3.0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(3);
        path.add(1);
        assertEquals(path, sp.getShortestPath(g, 0, 1));
    }

    @Test
    public void srcAndTgtPathWithMoreEdges() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(0, 2, 0.0);
        g.addEdge(2, 1, 3.0);
        g.addEdge(2, 3, 1.0);
        g.addEdge(3, 1, 1.0);
        LinkedList<Integer> path = new LinkedList<Integer>();
        path.add(0);
        path.add(2);
        path.add(3);
        path.add(1);
        assertEquals(path, sp.getShortestPath(g, 0, 1));
    }

}
