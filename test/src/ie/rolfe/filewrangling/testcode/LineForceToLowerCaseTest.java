/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineForceToLowerCase;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LineForceToLowerCaseTest {

    @org.junit.jupiter.api.Test
    void fixLineNoMatches() {

        LineForceToLowerCase thingToTest = new LineForceToLowerCase(1, 1);
        final String testLine = "ABC";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatches() {

        LineForceToLowerCase thingToTest = new LineForceToLowerCase(1, 1);
        final String testLine = "ABC";

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(testLine.toLowerCase(), output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNull() {

        LineForceToLowerCase thingToTest = new LineForceToLowerCase(1, 1);

        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixLineNoMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineForceToLowerCase", p);
        final String testLine = "ABC";
        LineForceToLowerCase thingToTest = new LineForceToLowerCase(w);

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineForceToLowerCase", p);
        final String testLine = "ABC";
        LineForceToLowerCase thingToTest = new LineForceToLowerCase(w);

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(testLine.toLowerCase(), output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNullWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        WranglerRequest w = new WranglerRequest("LineForceToLowerCase", p);
        LineForceToLowerCase thingToTest = new LineForceToLowerCase(w);

        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }


}