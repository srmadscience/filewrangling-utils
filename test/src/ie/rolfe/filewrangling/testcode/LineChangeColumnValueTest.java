/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineChangeOverrideColumnValue;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LineChangeColumnValueTest {

    public static final String TEST_VALUE = "D";

    @org.junit.jupiter.api.Test
    void fixLineNoMatches() {

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(1, 1, 1, "D");
        final String testLine = "A,B,C";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatches() {

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(1, 1, 1, "D");
        final String testLine = "A,B,C";
        final String expectedLine = "A,D,C";

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(expectedLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNull() {

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(1, 1, 1, "D");
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixLineNoMatchesWR() {


        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("fieldNumberStartingAtZero", 1);
        p.put("newValue", TEST_VALUE);
        WranglerRequest w = new WranglerRequest("LineChangeOverrideColumnValue", p);

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(w);

        final String testLine = "A,B,C";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatchesWR() {


        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("fieldNumberStartingAtZero", 1);
        p.put("newValue", TEST_VALUE);
        WranglerRequest w = new WranglerRequest("LineChangeOverrideColumnValue", p);

        final String testLine = "A,B,C";
        final String expectedLine = "A," + TEST_VALUE + ",C";

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(w);

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(expectedLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNullWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("fieldNumberStartingAtZero", 1);
        p.put("newValue", TEST_VALUE);
        WranglerRequest w = new WranglerRequest("LineChangeOverrideColumnValue", p);

        LineChangeOverrideColumnValue thingToTest =
                new LineChangeOverrideColumnValue(w);
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

}