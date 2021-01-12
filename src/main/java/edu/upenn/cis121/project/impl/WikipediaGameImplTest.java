package edu.upenn.cis121.project.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.engine.WikipediaGame.EdgeWeightType;
import edu.upenn.cis121.project.impl.WikipediaGameImpl.Edge;

public class WikipediaGameImplTest {
    WikipediaGameImpl wgi;
    Edge<Integer> e;

    @Before
    public void setUp() {
        wgi = new WikipediaGameImpl(new DoubleWeightedDirectedGraphImpl<PageVertex>());
        e = new Edge<Integer>(1, 0, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullGraph() {
        new WikipediaGameImpl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void solveWikiEdgeNull() {
        wgi.solveWikiGame(null, new PageVertex(""), new PageVertex(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void solveWikiSrcNull() {
        wgi.solveWikiGame(EdgeWeightType.DEGREE, null, new PageVertex(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void solveWikiTgtNull() {
        wgi.solveWikiGame(EdgeWeightType.DEGREE, new PageVertex(""), null);
    }

}
