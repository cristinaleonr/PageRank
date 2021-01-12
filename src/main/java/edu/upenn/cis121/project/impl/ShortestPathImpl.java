package edu.upenn.cis121.project.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import edu.upenn.cis121.project.data.BinaryMinHeap;
import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.graph.ShortestPath;

/**
 * @param <V>
 *            {@inheritDoc}
 *
 * @author eyeung, 16sp
 */
public class ShortestPathImpl<V> implements ShortestPath<V> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<V> getShortestPath(DoubleWeightedDirectedGraph<V> G, V src, V tgt) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        if (src == null || !G.vertexSet().contains(src)) {
            throw new IllegalArgumentException();
        }
        if (tgt == null || !G.vertexSet().contains(tgt)) {
            throw new IllegalArgumentException();
        }
        LinkedList<V> list = new LinkedList<V>();

        Set<V> vertices = G.vertexSet();
        // mapping from child to parent
        HashMap<V, V> parent = new HashMap<V, V>();
        // mapping form vertex to distance from src
        HashMap<V, Double> distance = new HashMap<V, Double>();
        BinaryMinHeap<V, Double> pq = new BinaryMinHeapImpl<V, Double>();

        for (V v : vertices) {
            if (!v.equals(src)) {
                pq.add(v, Double.POSITIVE_INFINITY);
                distance.put(v, Double.POSITIVE_INFINITY);
            }
            parent.put(v, v);
        }
        pq.add(src, 0.0);
        distance.put(src, 0.0);

        while (!pq.isEmpty()) {
            V min = pq.extractMin();
            for (V neighbor : G.neighbors(min)) {
                double distFromMin = distance.get(min) + G.getWeight(min, neighbor).get();
                if (distFromMin < distance.get(neighbor)) {
                    pq.decreaseKey(neighbor, distFromMin);

                    distance.remove(neighbor);
                    distance.put(neighbor, distFromMin);

                    parent.remove(neighbor);
                    parent.put(neighbor, min);
                }
            }
        }

        if (parent.get(tgt).equals(tgt)) {
            return list;
        }

        V child = tgt;
        list.add(tgt);
        while (!child.equals(src)) {
            V p = parent.get(child);
            list.add(p);
            child = p;
        }
        LinkedList<V> reversed = new LinkedList<V>();
        while (!list.isEmpty()) {
            reversed.add(list.removeLast());
        }

        return reversed;
    }
}
