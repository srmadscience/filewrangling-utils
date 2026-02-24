/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineChangeOverrideColumnValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LineChangeColumnValueTest {

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


}