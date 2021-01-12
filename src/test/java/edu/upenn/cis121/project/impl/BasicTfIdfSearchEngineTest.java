package edu.upenn.cis121.project.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author davix
 */
public class BasicTfIdfSearchEngineTest {

    @Before
    public void setUp() throws Exception {
        // TODO
    }

    @Test
    public void testLoadXmlDump() throws Exception {
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(BasicTfIdfSearchEngineTest.class.getResource("/TODO.xml").toURI()))) {
            // TODO
            fail("unimplemented");
        }
    }

    @Test
    public void testSearch() throws Exception {
        // TODO
        fail("unimplemented");
    }
}
