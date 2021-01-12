package edu.upenn.cis121.project.impl;

import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiParseException;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiText;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * For testing purposes, this class must provide a public default no-argument
 * constructor. This means that you do not need to write any constructor at all;
 * if you do write other constructors, be sure you have one that is public and
 * takes no arguments.
 *
 * @author davix
 */
public class WikiXmlDumpParserImpl implements WikiXmlDumpParser {
    HashMap<String, PageVertex> titleToObject;

    @Override
    public DirectedGraph<PageVertex> parseXmlDump(File file)
            throws IOException, XMLStreamException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        MyDirectedGraphImpl graph = new MyDirectedGraphImpl();
        titleToObject = new HashMap<String, PageVertex>();

        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {

            try {
                XMLEventReader r = xmlif.createXMLEventReader(br);
                PageVertex newVertex = null;
                while (r.hasNext()) {
                    try {
                        XMLEvent event = r.nextEvent();

                        if (event.isStartElement()) {
                            if (event.asStartElement().getName().getLocalPart().equals("title")) {
                                String title = r.getElementText();
                                newVertex = new PageVertex(title);
                                if (!titleToObject.containsKey(title)) {
                                    graph.addVertex(newVertex);
                                    titleToObject.put(title, newVertex);
                                }
                            }

                            else if (event.asStartElement().getName().getLocalPart()
                                    .equals("format")) {
                                String type = r.getElementText();
                                if (type.equals("text/plain") && newVertex != null) {
                                    newVertex.type = type;
                                } else if (type.equals("text/x-wiki") && newVertex != null) {
                                    newVertex.hasOutlinks = true;
                                    newVertex.type = type;
                                }
                            } else if (event.asStartElement().getName().getLocalPart()
                                    .equals("text")) {
                                String text = r.getElementText();
                                if (newVertex != null) {
                                    if (newVertex.type.equals("text/plain")
                                            || newVertex.type.equals("text/x-wiki")) {
                                        newVertex.text = text;
                                    }
                                    if (newVertex.hasOutlinks) {
                                        try {
                                            MediaWikiText wikiText = MediaWikiUtils
                                                    .parseWikiText(text);
                                            for (String link : wikiText.getOutlinks()) {
                                                PageVertex v = new PageVertex(link);
                                                if (!graph.vertexSet().contains(v)) {
                                                    titleToObject.put(link, v);
                                                    graph.addVertex(v);
                                                } else {
                                                    v = titleToObject.get(link);
                                                }
                                                newVertex.addOutLink(link);
                                                v.addInLink(newVertex.getId());
                                            }
                                        } catch (MediaWikiParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

}
