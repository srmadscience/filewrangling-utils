/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineForceToLowerCase;
import ie.rolfe.filewrangling.impl.LineRemoveLastChar;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LineRemoveLastCharTest {

    @org.junit.jupiter.api.Test
    void fixLineMatches() {

        LineRemoveLastChar thingToTest = new LineRemoveLastChar(1, 1);
        final String testInputLine = "ABC";
        final String testOutput = "AB";

        String output = thingToTest.fixLine(1, testInputLine);

        assertEquals(testOutput, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineNoMatches() {

        LineRemoveLastChar thingToTest = new LineRemoveLastChar(1, 1);
        final String testInputLine = "ABC";

        String output = thingToTest.fixLine(42, testInputLine);

        assertEquals(testInputLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNull() {

        LineRemoveLastChar thingToTest = new LineRemoveLastChar(1, 1);
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatchesAndToLowerCase() {

        LineRemoveLastChar thingToTest = new LineRemoveLastChar(1, 1);
        LineForceToLowerCase thingToTest2 = new LineForceToLowerCase(1, 1);
        thingToTest.addCSVLineWranglerIFace(thingToTest2);

        final String testInputLine = "ABC";
        final String testOutput = "ab";

        String output = thingToTest.fixLine(1, testInputLine);

        assertEquals(testOutput, output);
    }
    @org.junit.jupiter.api.Test
    void fixLineMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineRemoveLastChar", p);
        LineRemoveLastChar thingToTest = new LineRemoveLastChar(w);

        final String testInputLine = "ABC";
        final String testOutput = "AB";

        String output = thingToTest.fixLine(1, testInputLine);

        assertEquals(testOutput, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineNoMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineRemoveLastChar", p);
        LineRemoveLastChar thingToTest = new LineRemoveLastChar(w);      final String testInputLine = "ABC";

        String output = thingToTest.fixLine(42, testInputLine);

        assertEquals(testInputLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNullWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineRemoveLastChar", p);
        LineRemoveLastChar thingToTest = new LineRemoveLastChar(w);      String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatchesAndToLowerCaseWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineRemoveLastChar", p);
        LineRemoveLastChar thingToTest = new LineRemoveLastChar(w);

        LineForceToLowerCase thingToTest2 = new LineForceToLowerCase(1, 1);
        thingToTest.addCSVLineWranglerIFace(thingToTest2);

        final String testInputLine = "ABC";
        final String testOutput = "ab";

        String output = thingToTest.fixLine(1, testInputLine);

        assertEquals(testOutput, output);
    }
}
