/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldMapToZero;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldMapToZeroTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        FieldMapToZero fieldMapToZero = new FieldMapToZero();

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals("0", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        FieldMapToZero fieldMapToZero = new FieldMapToZero();

        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }

}