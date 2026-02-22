/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineChangeReplaceAllValueOfFieldInLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LineChangeFieldValueTest {

    @org.junit.jupiter.api.Test
    void fixLineNoMatches() {

        LineChangeReplaceAllValueOfFieldInLine thingToTest =
                new LineChangeReplaceAllValueOfFieldInLine(1, 1, "B", "D");
        final String testLine = "A,B,C";

        String output = thingToTest.fixLine(42, testLine);

        assertEquals(testLine, output);
    }

    @org.junit.jupiter.api.Test
    void fixLineMatches() {

        LineChangeReplaceAllValueOfFieldInLine thingToTest =
                new LineChangeReplaceAllValueOfFieldInLine(1, 1, "B", "D");
        final String testLine = "A,B,C";
        final String expectedLine = "A,D,C";

        String output = thingToTest.fixLine(1, testLine);

        assertEquals(expectedLine, output);
    }


    @org.junit.jupiter.api.Test
    void fixLineNull() {

        LineChangeReplaceAllValueOfFieldInLine thingToTest =
                new LineChangeReplaceAllValueOfFieldInLine(1, 1, "B", "D");
        final String testLine = "ABC";

        String input = null;
        String output = thingToTest.fixLine(1, null);

        assertNull(output);
    }


}