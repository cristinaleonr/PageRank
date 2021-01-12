package edu.upenn.cis121.project.impl;

import java.util.HashSet;
import java.util.Set;

import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.engine.WikipediaGame;

/**
 * Implementation of a solver for the Wikipedia game.
 * <p>
 * You are free to implement this class however you like. We will be testing
 * your code through the {@link WikipediaGameFactoryImpl} class.
 */
class WikipediaGameImpl<V extends AbstractIdentifiable> implements WikipediaGame<V> {
    DoubleWeightedDirectedGraphImpl<V> graph;
    HashSet<Edge<V>> unweighted;
    HashSet<Edge<V>> degree;

    public WikipediaGameImpl(DirectedGraph<V> g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }

        graph = new DoubleWeightedDirectedGraphImpl<V>();
        Set<V> vertices = g.vertexSet();
        unweighted = new HashSet<Edge<V>>();
        degree = new HashSet<Edge<V>>();

        for (V u : vertices) {
            graph.addVertex(u);
            Set<V> neighbors = graph.neighbors(u);
            for (V v : neighbors) {
                Edge<V> uw = new Edge<V>(u, v, 1.0);
                unweighted.add(uw);
                Edge<V> deg = new Edge<V>(u, v, (double) graph.inDegree(v));
                degree.add(deg);
            }
        }
    }

    @Override
    public Iterable<V> solveWikiGame(EdgeWeightType e, V src, V tgt) {
        if (e == null || src == null || tgt == null) {
            throw new IllegalArgumentException();
        }

        if (e == EdgeWeightType.DEGREE) {
            for (Edge<V> edge : degree) {
                graph.addEdge(edge.src, edge.tgt, edge.weight);
            }
        } else if (e == EdgeWeightType.UNWEIGHTED) {
            for (Edge<V> edge : unweighted) {
                graph.addEdge(edge.src, edge.tgt, edge.weight);
            }
        }

        ShortestPathImpl<V> path = new ShortestPathImpl<V>();
        return path.getShortestPath(graph, src, tgt);
    }

    public static class Edge<V> {
        V src;
        V tgt;
        Double weight;

        public Edge(V src, V tgt, Double weight) {
            this.src = src;
            this.tgt = tgt;
            this.weight = weight;
        }
    }
}