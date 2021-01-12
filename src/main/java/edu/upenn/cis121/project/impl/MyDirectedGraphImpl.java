package edu.upenn.cis121.project.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.ForeignVertexException;

public class MyDirectedGraphImpl implements DirectedGraph<PageVertex> {
    Set<PageVertex> vertices = new HashSet<PageVertex>();

    void checkNull(PageVertex v) {
        if (v == null) {
            throw new IllegalArgumentException();
        }
    }

    void exists(PageVertex v) {
        if (!vertices.contains(v)) {
            throw new ForeignVertexException();
        }
    }

    @Override
    public Set<PageVertex> inNeighbors(PageVertex vertex) {
        checkNull(vertex);
        exists(vertex);
        Set<PageVertex> vertices = vertexSet();
        Set<PageVertex> in = new HashSet<PageVertex>();
        for (String s : vertex.getIn()) {
            for (PageVertex v : vertices) {
                if (v.getId().equals(s)) {
                    in.add(v);
                }
            }
        }
        return in;
    }

    @Override
    public Set<PageVertex> outNeighbors(PageVertex vertex) {
        checkNull(vertex);
        exists(vertex);
        Set<PageVertex> vertices = vertexSet();
        Set<PageVertex> out = new HashSet<PageVertex>();
        for (String s : vertex.getOut()) {
            for (PageVertex v : vertices) {
                if (v.getId().equals(s)) {
                    out.add(v);
                }
            }
        }
        return out;
    }

    @Override
    public Set<PageVertex> vertexSet() {
        return Collections.unmodifiableSet(vertices);
    }

    void addVertex(PageVertex vertex) {
        vertices.add(vertex);
    }

}
