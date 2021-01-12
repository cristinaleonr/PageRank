package edu.upenn.cis121.project.impl;

import java.util.HashSet;
import java.util.Set;

import edu.upenn.cis121.project.data.AbstractIdentifiable;

public class PageVertex extends AbstractIdentifiable {
    private Set<String> outLinks;
    private Set<String> inLinks;
    String text;
    boolean hasOutlinks;
    String type;

    protected PageVertex(String id) {
        super(id);
        outLinks = new HashSet<String>();
        inLinks = new HashSet<String>();
        hasOutlinks = false;
        type = null;
    }

    Set<String> getIn() {
        return inLinks;
    }

    void addInLink(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException();
        }
        inLinks.add(vertex);
    }

    Set<String> getOut() {
        return outLinks;
    }

    void addOutLink(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException();
        }
        outLinks.add(vertex);
    }

}
