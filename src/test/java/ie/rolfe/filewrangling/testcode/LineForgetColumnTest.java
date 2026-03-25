/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.impl.LineForgetColumnsInLine;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static ie.rolfe.filewrangling.BaseFileWrangler.msg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LineForgetColumnTest {

    private static final String TEST_VALUE_D = "D";
    private static final String TEST_VALUE_B = "B";

    @org.junit.jupiter.api.Test
    void fixLineNoMatches() {

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(1, 1,TEST_VALUE_D );
        final String testLine = "A,B,C";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatches() {

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(1, 1, TEST_VALUE_B);
        final String testLine = "A,B,C";
        final String expectedLine = "A,C";

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(expectedLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNull() {

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(1, 1, TEST_VALUE_B);
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNoMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("columnNames", TEST_VALUE_D);
        WranglerRequest w = new WranglerRequest("LineForgetColumnsInLine", p);

        Gson g = new Gson();
        msg(g.toJson(w));

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(w);
        final String testLine = "A,B,C";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatchesWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("columnNames", TEST_VALUE_B);
        WranglerRequest w = new WranglerRequest("LineForgetColumnsInLine", p);

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(w);
        final String testLine = "A,B,C";
        final String expectedLine = "A,C";

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(expectedLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNullWR() {

        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("columnNames", TEST_VALUE_B);
        WranglerRequest w = new WranglerRequest("LineForgetColumnsInLine", p);

        LineForgetColumnsInLine thingToTest =
                new LineForgetColumnsInLine(w);
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }

}