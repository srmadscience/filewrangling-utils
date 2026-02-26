package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldPrepend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
class FieldPrependTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        FieldPrepend fieldToTest = new FieldPrepend("N");

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        final String StringToPrepend = "N";
        final String payload = "1976T";

        FieldPrepend fieldToTest = new FieldPrepend(StringToPrepend);

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals(StringToPrepend + payload, output);
    }

}