package edu.upenn.cis121.project.impl;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.graph.ForeignVertexException;

public class DoubleWeightedDirectedGraphImpl<V> implements DoubleWeightedDirectedGraph<V> {
    HashMap<V, HashMap<V, Double>> adjacencyList = new HashMap<V, HashMap<V, Double>>();

    boolean addVertex(V vertex) {
        if (adjacencyList.keySet().contains(vertex)) {
            return false;
        }
        adjacencyList.put(vertex, new HashMap<V, Double>());
        return true;
    }

    int getSize() {
        return adjacencyList.size();
    }

    boolean vertexExists(V vertex) {
        return adjacencyList.containsKey(vertex);
    }

    boolean hasEdge(V src, V tgt) {
        if (src == null || tgt == null) {
            throw new IllegalArgumentException();
        }
        if (!(vertexExists(src)) || !(vertexExists(tgt))) {
            throw new ForeignVertexException();
        }
        Set<V> neighbors = adjacencyList.get(src).keySet();
        return neighbors.contains(tgt);
    }

    boolean addEdge(V src, V tgt, double weight) {
        if (src == null || tgt == null) {
            throw new IllegalArgumentException();
        }
        if (!(vertexExists(src)) || !(vertexExists(tgt))) {
            throw new ForeignVertexException();
        }
        if (hasEdge(src, tgt)) {
            return false;
        } else {
            HashMap<V, Double> map = new HashMap<V, Double>();
            map.put(tgt, weight);
            adjacencyList.get(src).putAll(map);
            return true;
        }
    }

    @Override
    public Set<V> neighbors(V vertex) {
        return outNeighbors(vertex);
    }

    @Override
    public Set<V> inNeighbors(V vertex) {
        Set<V> in = new TreeSet<V>();
        if (!(vertexExists(vertex))) {
            throw new IllegalArgumentException();
        }
        for (Entry<V, HashMap<V, Double>> edgeMap : adjacencyList.entrySet()) {
            HashMap<V, Double> edges = edgeMap.getValue();
            if (edges.containsKey(vertex)) {
                in.add(edgeMap.getKey());
            }
        }
        return in;
    }

    @Override
    public Set<V> outNeighbors(V vertex) {
        if (!(vertexExists(vertex))) {
            throw new IllegalArgumentException();
        }
        return adjacencyList.get(vertex).keySet();
    }

    @Override
    public Set<V> vertexSet() {
        return adjacencyList.keySet();
    }

    @Override
    public Optional<Double> getWeight(V src, V tgt) {
        if (src == null || tgt == null) {
            throw new IllegalArgumentException();
        }
        if (!(vertexExists(src)) || !(vertexExists(tgt))) {
            throw new ForeignVertexException();
        }

        if (hasEdge(src, tgt)) {
            HashMap<V, Double> neighbors = adjacencyList.get(src);
            Optional<Double> optional = Optional.of(neighbors.get(tgt));
            return optional;
        }

        return Optional.empty();
    }

}
