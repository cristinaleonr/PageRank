package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.ConnectedComponents;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;
import edu.upenn.cis121.project.graph.UndirectedGraph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * For testing purposes, this class must provide a public default no-argument
 * constructor. This means that you do not need to write any constructor at all;
 * if you do write other constructors, be sure you have one that is public and
 * takes no arguments.
 *
 * @author davix
 */
public class ConnectedComponentsImpl<V extends Comparable<V>> implements ConnectedComponents<V> {

    @Override
    public Iterator<V> iterativeDepthFirstOrderIterator(SimpleGraph<V> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        LinkedList<V> orderedVertices = new LinkedList<V>();
        orderedVertices.addAll(graph.vertexSet());
        Collections.sort(orderedVertices);

        return new myIterator<V>(graph, orderedVertices);
    }

    static class myIterator<V extends Comparable<V>> implements Iterator<V> {
        HashSet<V> visited = new HashSet<V>();
        Stack<V> dfs = new Stack<V>();
        V currSmallest;
        SimpleGraph<V> g;
        LinkedList<V> order;

        public myIterator(SimpleGraph<V> graph, LinkedList<V> order) {
            g = graph;
            this.order = order;
        }

        @Override
        public boolean hasNext() {
            return !(visited.size() == g.order());
        }

        public V nextInComponent() {
            if (!hasNextInComponent()) {
                throw new NoSuchElementException();
            }

            while (!dfs.isEmpty()) {
                V vertex = dfs.pop();

                TreeSet<V> orderedNeighbors = new TreeSet<V>();
                orderedNeighbors.addAll(g.neighbors(vertex));
                orderedNeighbors = (TreeSet<V>) orderedNeighbors.descendingSet();

                for (V v : orderedNeighbors) {
                    if (!visited.contains(v) && !dfs.contains(v)) {
                        dfs.push(v);
                    }
                }

                if (!visited.contains(vertex)) {
                    visited.add(vertex);

                    return vertex;
                }
            }
            return null;
        }

        public boolean hasNextInComponent() {
            return !dfs.isEmpty();
        }

        @Override
        public V next() {
            if (hasNextInComponent()) {
                V nextInComponent = nextInComponent();
                return nextInComponent;
            }

            if (hasNext()) {
                V smallestNonVisited = getSmallestNonVisited();
                visited.add(smallestNonVisited);
                currSmallest = smallestNonVisited;

                for (V neighbor : g.neighbors(smallestNonVisited)) {

                    if (!visited.contains(neighbor)) {
                        dfs.push(smallestNonVisited);
                        break;
                    }
                }

                return smallestNonVisited;
            } else {
                throw new NoSuchElementException();
            }
        }

        public V getSmallestNonVisited() {
            Iterator<V> orderedVertices = order.iterator();

            V ref = orderedVertices.next();
            while (visited.contains(ref)) {
                ref = orderedVertices.next();
            }
            return ref;
        }

    }

    static class postOrderIterator<V extends Comparable<V>> implements Iterator<V> {
        HashSet<V> visited = new HashSet<V>();
        Stack<V> dfs = new Stack<V>();
        V currSmallest;
        SimpleGraph<V> g;
        LinkedList<V> order;

        public postOrderIterator(DirectedGraph<V> graph, LinkedList<V> order) {
            g = graph;
            this.order = order;
        }

        @Override
        public boolean hasNext() {
            return !(visited.size() == g.order());
        }

        public V nextInComponent() {
            if (!hasNextInComponent()) {
                throw new NoSuchElementException();
            }

            while (!dfs.isEmpty()) {
                V vertex = dfs.peek();

                TreeSet<V> orderedNeighbors = new TreeSet<V>();
                orderedNeighbors.addAll(g.neighbors(vertex));
                orderedNeighbors = (TreeSet<V>) orderedNeighbors.descendingSet();

                boolean unvisitedChildren = false;
                for (V v : orderedNeighbors) {
                    if (!visited.contains(v) && !dfs.contains(v)) {
                        unvisitedChildren = true;
                        dfs.push(v);
                    }
                }

                if (!unvisitedChildren) {
                    visited.add(vertex);
                    dfs.pop();
                    return vertex;
                }

            }
            return null;
        }

        public boolean hasNextInComponent() {
            return !dfs.isEmpty();
        }

        @Override
        public V next() {

            if (hasNextInComponent()) {
                V nextInComponent = nextInComponent();
                return nextInComponent;
            }

            if (hasNext()) {
                V smallestNonVisited = getSmallestNonVisited();
                currSmallest = smallestNonVisited;

                dfs.push(smallestNonVisited);
                return nextInComponent();
            } else {
                throw new NoSuchElementException();
            }
        }

        public V getSmallestNonVisited() {
            Iterator<V> orderedVertices = order.iterator();

            V ref = orderedVertices.next();
            while (visited.contains(ref)) {
                ref = orderedVertices.next();
            }
            return ref;
        }
    }

    @Override
    public Set<Set<V>> weaklyConnectedComponents(SimpleGraph<V> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        if (graph instanceof DirectedGraph<?>) {
            graph = new DirectedAsUndirectedGraphWrapper<V>((DirectedGraph<V>) graph);
        }

        Set<Set<V>> weaklyCCs = new HashSet<Set<V>>();
        myIterator<V> iterator = (myIterator<V>) iterativeDepthFirstOrderIterator(graph);

        while (iterator.hasNext()) {
            Set<V> component = new HashSet<V>();
            V next = iterator.next();
            component.add(next);
            while (iterator.hasNextInComponent()) {
                next = iterator.nextInComponent();
                component.add(next);
            }
            weaklyCCs.add(component);
        }
        return weaklyCCs;
    }

    @Override
    public Set<Set<V>> stronglyConnectedComponents(DirectedGraph<V> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        Set<Set<V>> stronglyCCs = new HashSet<Set<V>>();

        // sort vertices
        LinkedList<V> orderedVertices = new LinkedList<V>();
        orderedVertices.addAll(graph.vertexSet());
        Collections.sort(orderedVertices);

        // get post order
        postOrderIterator<V> iterator = new postOrderIterator<V>(graph, orderedVertices);

        LinkedList<V> reversedPostOrder = new LinkedList<V>();

        // get reversed post order
        while (iterator.hasNext()) {
            reversedPostOrder.addFirst(iterator.next());
            ;
        }

        // get reversed graph
        ReversedGraph<V> reversedG = new ReversedGraph<V>(graph);
        myIterator<V> reversedIterator = new myIterator<V>(reversedG, reversedPostOrder);

        while (reversedIterator.hasNext()) {
            Set<V> component = new HashSet<V>();
            component.add(reversedIterator.next());
            while (reversedIterator.hasNextInComponent()) {
                component.add(reversedIterator.next());
            }

            stronglyCCs.add(component);
        }
        return stronglyCCs;
    }

    private static class DirectedAsUndirectedGraphWrapper<V> implements UndirectedGraph<V> {
        DirectedGraph<V> graph;
        Set<V> vertexSet;

        public DirectedAsUndirectedGraphWrapper(DirectedGraph<V> graph) {
            this.graph = graph;
            vertexSet = graph.vertexSet();
        }

        @Override
        public Set<V> vertexSet() {
            return vertexSet;
        }

        @Override
        public Set<V> neighbors(V arg0) {
            Set<V> neighbors = new HashSet<V>();
            neighbors.addAll(graph.neighbors(arg0));
            for (V vertex : vertexSet) {
                if (graph.hasDirectedEdge(vertex, arg0)) {
                    neighbors.add(vertex);
                }
            }
            return neighbors;
        }
    }

    private static class ReversedGraph<V> implements DirectedGraph<V> {
        DirectedGraph<V> graph;
        Set<V> vertexSet;

        public ReversedGraph(DirectedGraph<V> graph) {
            this.graph = graph;
            vertexSet = graph.vertexSet();
        }

        @Override
        public Set<V> neighbors(V arg0) {
            return outNeighbors(arg0);
        }

        @Override
        public Set<V> vertexSet() {
            return vertexSet;
        }

        @Override
        public Set<V> inNeighbors(V arg0) {
            return graph.neighbors(arg0);
        }

        @Override
        public Set<V> outNeighbors(V arg0) {
            Set<V> neighbors = new HashSet<V>();
            for (V vertex : vertexSet) {
                if (graph.hasDirectedEdge(vertex, arg0)) {
                    neighbors.add(vertex);
                }
            }
            return neighbors;
        }
    }

}
