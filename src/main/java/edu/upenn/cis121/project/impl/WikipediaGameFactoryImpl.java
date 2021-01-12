package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.engine.WikipediaGame;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.engine.WikipediaGameFactory;

/**
 *
 * @author davix
 */
public class WikipediaGameFactoryImpl implements WikipediaGameFactory {
    @Override
    public <V extends AbstractIdentifiable> WikipediaGame<V> create(DirectedGraph<V> g) {
        return new WikipediaGameImpl<>(g);
    }
}
