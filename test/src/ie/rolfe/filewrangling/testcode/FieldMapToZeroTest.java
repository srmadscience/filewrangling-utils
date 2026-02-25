/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldNvl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldMapToZeroTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        FieldNvl fieldMapToZero = new FieldNvl();

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals("0", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNullZ() {
        String prepend = "Z";
        FieldNvl fieldMapToZero = new FieldNvl(prepend);

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals(prepend, output);
    }


    @org.junit.jupiter.api.Test
    void fixFieldNotNull() {

        FieldNvl fieldMapToZero = new FieldNvl();

        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotNullZ() {

        String prepend = "Z";

        FieldNvl fieldMapToZero = new FieldNvl(prepend);


        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }


    @org.junit.jupiter.api.Test
    void fixFieldNotNullNotZero() {

        FieldNvl fieldMapToZero = new FieldNvl("X");

        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }


}