package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldKeep;
import ie.rolfe.filewrangling.impl.FieldPrepend;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
class FieldKeepTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        FieldKeep fieldToTest = new FieldKeep();

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        final String payload = "1976T";

        FieldKeep fieldToTest = new FieldKeep();

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals(input, output);
    }


    @org.junit.jupiter.api.Test
    void fixFieldNullWR() {


        WranglerRequest w = new WranglerRequest("FieldKeep");

        FieldKeep fieldToTest = new FieldKeep(w);

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMullWR() {

        WranglerRequest w = new WranglerRequest("FieldKeep");

        final String payload = "1976T";

        FieldKeep fieldToTest = new FieldKeep();

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals(input, output);
    }

}