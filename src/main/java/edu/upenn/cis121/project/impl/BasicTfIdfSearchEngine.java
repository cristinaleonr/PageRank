package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.engine.QueryContext;
import edu.upenn.cis121.project.engine.SearchEngine;
import edu.upenn.cis121.project.engine.SearchResult;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author davix
 */
public class BasicTfIdfSearchEngine implements SearchEngine {

    @Override
    public void loadXmlDump(File file) throws IOException, XMLStreamException {

    }

    @Override
    public Iterable<? extends SearchResult> search(String text, int maxNumResults) {

        Iterable<SearchResult> results = Collections.emptyList();
        return results;
    }

    static class SearchResultImpl implements SearchResult {
        HashMap<String, Double> subValueMap;
        URL url;
        String title;

        @Override
        public double getOverallValue() {
            return subValueMap.get("tf-idf");
        }

        @Override
        public Collection<? extends QueryContext> getQueryContexts() {
            return null;
        }

        @Override
        public Map<String, Double> getSubValueMap() {
            return subValueMap;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public URL getURL() {
            return url;
        }

        public void setTitle(String t) {
            title = t;
        }

        public void setURL(URL u) {
            url = u;
        }

    }

}
