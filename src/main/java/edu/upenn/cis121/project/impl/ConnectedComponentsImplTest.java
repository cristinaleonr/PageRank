package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.ConnectedComponents;
import edu.upenn.cis121.project.graph.SimpleGraph;
import edu.upenn.cis121.project.impl.ConnectedComponentsImpl.myIterator;

public class ConnectedComponentsImplTest {
    ConnectedComponents<Integer> cci;
    DoubleWeightedDirectedGraphImpl<Integer> g;
    Integer zero;
    Integer one;
    Integer two;
    Integer three;
    Integer four;
    Integer five;
    Integer six;
    Integer seven;
    Integer eight;
    Integer ten;
    Integer eleven;
    Integer twelve;
    Integer fourteen;

    @Before
    public void setUp() {
        cci = new ConnectedComponentsImpl<Integer>();
        g = new DoubleWeightedDirectedGraphImpl<Integer>();
        zero = 0;
        one = 1;
        two = 2;
        three = 3;
        four = 4;
        five = 5;
        six = 6;
        seven = 7;
        eight = 8;
        ten = 10;
        eleven = 11;
        twelve = 12;
        fourteen = 14;
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullGraphIterator() {
        cci.iterativeDepthFirstOrderIterator(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyNext() {
        myIterator<Integer> i = (myIterator<Integer>) cci
                .iterativeDepthFirstOrderIterator((SimpleGraph<Integer>) g);
        i.next();
    }

    @Test
    public void hasNextSingleElementIterator() {
        g.addVertex(0);
        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);
        assertTrue(i.hasNext());
    }

    @Test
    public void nextSingleElement() {
        g.addVertex(0);
        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);
        Integer zero = 0;
        assertEquals(zero, i.next());
    }

    @Test
    public void leavesHaveSmallerValuesThanParentsIterator() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);

        g.addEdge(7, 0, 0.0);
        g.addEdge(0, 3, 0.0);
        g.addEdge(0, 5, 0.0);
        g.addEdge(0, 4, 0.0);
        g.addEdge(3, 1, 0.0);
        g.addEdge(3, 2, 0.0);

        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);

        assertEquals(zero, i.next());
        assertEquals(three, i.next());
        assertEquals(one, i.next());
        assertEquals(two, i.next());
        assertEquals(four, i.next());
        assertEquals(five, i.next());
        assertEquals(seven, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void allPathsLeadToSmallestValueVertexIterator() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);
        g.addEdge(7, 0, 0.0);
        g.addEdge(3, 0, 0.0);
        g.addEdge(5, 0, 0.0);
        g.addEdge(4, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(2, 3, 0.0);

        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);
        assertEquals(zero, i.next());
        assertEquals(one, i.next());
        assertEquals(three, i.next());
        assertEquals(two, i.next());
        assertEquals(four, i.next());
        assertEquals(five, i.next());
        assertEquals(seven, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void Cycle() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 1, 0.0);
        g.addEdge(2, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(3, 2, 0.0);

        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);
        assertEquals(zero, i.next());
        assertEquals(one, i.next());
        assertEquals(three, i.next());
        assertEquals(two, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void complexDiagraphTwoCCsIterator() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(7);
        g.addVertex(8);
        g.addVertex(10);
        g.addVertex(11);
        g.addVertex(12);
        g.addVertex(14);

        g.addEdge(1, 4, 0.0);
        g.addEdge(3, 4, 0.0);
        g.addEdge(1, 2, 0.0);
        g.addEdge(2, 3, 0.0);

        g.addEdge(0, 10, 0.0);
        g.addEdge(0, 14, 0.0);
        g.addEdge(10, 7, 0.0);
        g.addEdge(10, 11, 0.0);
        g.addEdge(7, 8, 0.0);
        g.addEdge(8, 12, 0.0);
        g.addEdge(8, 7, 0.0);
        g.addEdge(11, 14, 0.0);
        g.addEdge(14, 0, 0.0);

        myIterator<Integer> i = (myIterator<Integer>) cci.iterativeDepthFirstOrderIterator(g);
        assertEquals(zero, i.next());
        assertEquals(ten, i.next());
        assertEquals(seven, i.next());
        assertEquals(eight, i.next());
        assertEquals(twelve, i.next());
        assertEquals(eleven, i.next());
        assertEquals(fourteen, i.next());
        assertEquals(one, i.next());
        assertEquals(two, i.next());
        assertEquals(three, i.next());
        assertEquals(four, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void leavesHaveSmallerValuesThanParentsPostOrder() {
        g.addVertex(0);
        g.addVertex(10);
        g.addVertex(11);
        g.addVertex(14);
        g.addVertex(12);
        g.addVertex(5);
        g.addVertex(8);
        g.addVertex(7);

        g.addEdge(0, 10, 0.0);
        g.addEdge(10, 11, 0.0);
        g.addEdge(11, 14, 0.0);
        g.addEdge(14, 0, 0.0);
        g.addEdge(11, 5, 0.0);
        g.addEdge(11, 12, 0.0);
        g.addEdge(8, 7, 0.0);
        g.addEdge(7, 8, 0.0);

        LinkedList<Integer> orderedVertices = new LinkedList<Integer>();
        orderedVertices.addAll(g.vertexSet());
        Collections.sort(orderedVertices);
        Iterator<Integer> i = new ConnectedComponentsImpl.postOrderIterator(g, orderedVertices);
        assertEquals(five, i.next());
        assertEquals(twelve, i.next());
        assertEquals(fourteen, i.next());
        assertEquals(eleven, i.next());
        assertEquals(ten, i.next());
        assertEquals(zero, i.next());
        assertEquals(eight, i.next());
        assertEquals(seven, i.next());
        assertFalse(i.hasNext());

        /*
         * Set<Set<Integer>> scc = cci.stronglyConnectedComponents(g);
         * Iterator<Set<Integer>> i = scc.iterator(); while (i.hasNext()) {
         * Set<Integer> set = i.next(); Iterator<Integer> i2 = set.iterator();
         * while (i2.hasNext()) { System.out.println(i2.next()); }
         * System.out.println(); }
         */
    }

    @Test
    public void allPathsLeadToSmallestValueVertexPostOrder() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);
        g.addEdge(7, 0, 0.0);
        g.addEdge(3, 0, 0.0);
        g.addEdge(5, 0, 0.0);
        g.addEdge(4, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(2, 3, 0.0);

        LinkedList<Integer> orderedVertices = new LinkedList<Integer>();
        orderedVertices.addAll(g.vertexSet());
        Collections.sort(orderedVertices);
        Iterator<Integer> i = new ConnectedComponentsImpl.postOrderIterator(g, orderedVertices);
        assertEquals(zero, i.next());
        assertEquals(three, i.next());
        assertEquals(one, i.next());
        assertEquals(two, i.next());
        assertEquals(four, i.next());
        assertEquals(five, i.next());
        assertEquals(seven, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void cyclePostOrder() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 1, 0.0);
        g.addEdge(2, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(3, 2, 0.0);

        LinkedList<Integer> orderedVertices = new LinkedList<Integer>();
        orderedVertices.addAll(g.vertexSet());
        Collections.sort(orderedVertices);
        Iterator<Integer> i = new ConnectedComponentsImpl.postOrderIterator(g, orderedVertices);
        assertEquals(two, i.next());
        assertEquals(three, i.next());
        assertEquals(one, i.next());
        assertEquals(zero, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void complexDiagraphTwoCCsPostOrder() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(7);
        g.addVertex(8);
        g.addVertex(10);
        g.addVertex(11);
        g.addVertex(12);
        g.addVertex(14);

        g.addEdge(1, 4, 0.0);
        g.addEdge(3, 4, 0.0);
        g.addEdge(1, 2, 0.0);
        g.addEdge(2, 3, 0.0);

        g.addEdge(0, 10, 0.0);
        g.addEdge(0, 14, 0.0);
        g.addEdge(10, 7, 0.0);
        g.addEdge(10, 11, 0.0);
        g.addEdge(7, 8, 0.0);
        g.addEdge(8, 12, 0.0);
        g.addEdge(8, 7, 0.0);
        g.addEdge(11, 14, 0.0);
        g.addEdge(14, 0, 0.0);

        LinkedList<Integer> orderedVertices = new LinkedList<Integer>();
        orderedVertices.addAll(g.vertexSet());
        Collections.sort(orderedVertices);
        Iterator<Integer> i = new ConnectedComponentsImpl.postOrderIterator(g, orderedVertices);
        assertEquals(twelve, i.next());
        assertEquals(eight, i.next());
        assertEquals(seven, i.next());
        assertEquals(eleven, i.next());
        assertEquals(ten, i.next());
        assertEquals(fourteen, i.next());
        assertEquals(zero, i.next());
        assertEquals(three, i.next());
        assertEquals(two, i.next());
        assertEquals(four, i.next());
        assertEquals(one, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void noEdgesWCCs() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);

        Set<Set<Integer>> wcc = cci.weaklyConnectedComponents(g);
        Iterator<Set<Integer>> i = wcc.iterator();
        Set<Integer> wcc1 = new HashSet<Integer>();
        wcc1.add(zero);
        Set<Integer> wcc2 = new HashSet<Integer>();
        wcc2.add(one);
        Set<Integer> wcc3 = new HashSet<Integer>();
        wcc3.add(two);
        Set<Integer> wcc4 = new HashSet<Integer>();
        wcc4.add(three);
        Set<Integer> wcc5 = new HashSet<Integer>();
        wcc5.add(four);
        Set<Integer> wcc6 = new HashSet<Integer>();
        wcc6.add(five);
        Set<Integer> wcc7 = new HashSet<Integer>();
        wcc7.add(seven);

        assertEquals(wcc1, i.next());
        assertEquals(wcc2, i.next());
        assertEquals(wcc3, i.next());
        assertEquals(wcc4, i.next());
        assertEquals(wcc5, i.next());
        assertEquals(wcc6, i.next());
        assertEquals(wcc7, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void disconnectedDirectedBecomesConnectedUndirectedWCCs() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);

        g.addEdge(7, 0, 0.0);
        g.addEdge(0, 3, 0.0);
        g.addEdge(0, 5, 0.0);
        g.addEdge(0, 4, 0.0);
        g.addEdge(3, 1, 0.0);
        g.addEdge(3, 2, 0.0);

        Set<Set<Integer>> wcc = cci.weaklyConnectedComponents(g);
        Iterator<Set<Integer>> i = wcc.iterator();
        Set<Integer> wcc1 = new HashSet<Integer>();
        wcc1.add(zero);
        wcc1.add(one);
        wcc1.add(two);
        wcc1.add(three);
        wcc1.add(four);
        wcc1.add(five);
        wcc1.add(seven);

        assertEquals(wcc1, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void cycleWCCs() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 1, 0.0);
        g.addEdge(2, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(3, 2, 0.0);

        Set<Set<Integer>> wcc = cci.weaklyConnectedComponents(g);
        Iterator<Set<Integer>> i = wcc.iterator();
        Set<Integer> wcc1 = new HashSet<Integer>();
        wcc1.add(zero);
        wcc1.add(one);
        wcc1.add(two);
        wcc1.add(three);
        assertEquals(wcc1, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void complexDiagraphWCCs() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(7);
        g.addVertex(8);
        g.addVertex(10);
        g.addVertex(11);
        g.addVertex(12);
        g.addVertex(14);

        g.addEdge(1, 4, 0.0);
        g.addEdge(3, 4, 0.0);
        g.addEdge(1, 2, 0.0);
        g.addEdge(2, 3, 0.0);

        g.addEdge(0, 10, 0.0);
        g.addEdge(0, 14, 0.0);
        g.addEdge(10, 7, 0.0);
        g.addEdge(10, 11, 0.0);
        g.addEdge(7, 8, 0.0);
        g.addEdge(8, 12, 0.0);
        g.addEdge(8, 7, 0.0);
        g.addEdge(11, 14, 0.0);
        g.addEdge(14, 0, 0.0);

        Set<Set<Integer>> wcc = cci.weaklyConnectedComponents(g);
        Iterator<Set<Integer>> i = wcc.iterator();
        Set<Integer> wcc1 = new HashSet<Integer>();
        Set<Integer> wcc2 = new HashSet<Integer>();
        wcc1.add(one);
        wcc1.add(two);
        wcc1.add(three);
        wcc1.add(four);
        wcc2.add(zero);
        wcc2.add(seven);
        wcc2.add(eight);
        wcc2.add(ten);
        wcc2.add(eleven);
        wcc2.add(twelve);
        wcc2.add(fourteen);

        assertEquals(wcc1, i.next());
        assertEquals(wcc2, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void noEdgesSCCs() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);

        Set<Set<Integer>> scc = cci.stronglyConnectedComponents(g);
        Iterator<Set<Integer>> i = scc.iterator();
        Set<Integer> scc1 = new HashSet<Integer>();
        scc1.add(zero);
        Set<Integer> scc2 = new HashSet<Integer>();
        scc2.add(one);
        Set<Integer> scc3 = new HashSet<Integer>();
        scc3.add(two);
        Set<Integer> scc4 = new HashSet<Integer>();
        scc4.add(three);
        Set<Integer> scc5 = new HashSet<Integer>();
        scc5.add(four);
        Set<Integer> scc6 = new HashSet<Integer>();
        scc6.add(five);
        Set<Integer> scc7 = new HashSet<Integer>();
        scc7.add(seven);

        assertEquals(scc1, i.next());
        assertEquals(scc2, i.next());
        assertEquals(scc3, i.next());
        assertEquals(scc4, i.next());
        assertEquals(scc5, i.next());
        assertEquals(scc6, i.next());
        assertEquals(scc7, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void disconnectedDirectedSCCs() {
        g.addVertex(0);
        g.addVertex(7);
        g.addVertex(3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(5);
        g.addVertex(4);

        g.addEdge(7, 0, 0.0);
        g.addEdge(0, 3, 0.0);
        g.addEdge(0, 5, 0.0);
        g.addEdge(0, 4, 0.0);
        g.addEdge(3, 1, 0.0);
        g.addEdge(3, 2, 0.0);

        Set<Set<Integer>> scc = cci.stronglyConnectedComponents(g);
        Iterator<Set<Integer>> i = scc.iterator();
        Set<Integer> scc1 = new HashSet<Integer>();
        scc1.add(zero);
        Set<Integer> scc2 = new HashSet<Integer>();
        scc2.add(one);
        Set<Integer> scc3 = new HashSet<Integer>();
        scc3.add(two);
        Set<Integer> scc4 = new HashSet<Integer>();
        scc4.add(three);
        Set<Integer> scc5 = new HashSet<Integer>();
        scc5.add(four);
        Set<Integer> scc6 = new HashSet<Integer>();
        scc6.add(five);
        Set<Integer> scc7 = new HashSet<Integer>();
        scc7.add(seven);

        assertEquals(scc1, i.next());
        assertEquals(scc2, i.next());
        assertEquals(scc3, i.next());
        assertEquals(scc4, i.next());
        assertEquals(scc5, i.next());
        assertEquals(scc6, i.next());
        assertEquals(scc7, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void cycleSCCs() {
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(0, 1, 0.0);
        g.addEdge(2, 0, 0.0);
        g.addEdge(1, 3, 0.0);
        g.addEdge(3, 2, 0.0);

        Set<Set<Integer>> scc = cci.stronglyConnectedComponents(g);
        Iterator<Set<Integer>> i = scc.iterator();
        Set<Integer> scc1 = new HashSet<Integer>();
        scc1.add(zero);
        scc1.add(one);
        scc1.add(two);
        scc1.add(three);
        assertEquals(scc1, i.next());
        assertFalse(i.hasNext());
    }

}