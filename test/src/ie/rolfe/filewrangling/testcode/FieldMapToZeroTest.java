/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldNvl;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Locale;
import java.util.Properties;

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
        String mapValue = "Z";
        FieldNvl fieldMapToZero = new FieldNvl(mapValue);

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals(mapValue, output);
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

    @org.junit.jupiter.api.Test
    void fixFieldNullWR() {

        WranglerRequest w = new WranglerRequest("FieldNvl");

        FieldNvl fieldMapToZero = new FieldNvl(w);

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals("0", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNullZWR() {
        String mapValue = "Z";
        Properties p = new Properties();
        p.put("nvlValue", mapValue);

        WranglerRequest w = new WranglerRequest("FieldNvl",p);


        FieldNvl fieldMapToZero = new FieldNvl(mapValue);

        String input = null;
        String output = fieldMapToZero.fixField(input);

        assertEquals(mapValue, output);
    }


    @org.junit.jupiter.api.Test
    void fixFieldNotNullWR() {

        Properties p = new Properties();


        WranglerRequest w = new WranglerRequest("FieldNvl");

        FieldNvl fieldMapToZero = new FieldNvl(w);

        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotNullZWR() {

        String prepend = "Z";

        Properties p = new Properties();
        p.put("nvlValue", prepend);

        WranglerRequest w = new WranglerRequest("FieldNvl",p);

        FieldNvl fieldMapToZero = new FieldNvl(w);


        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }


    @org.junit.jupiter.api.Test
    void fixFieldNotNullNotZeroWR() {
        Properties p = new Properties();
        p.put("nvlValue", "X");

        WranglerRequest w = new WranglerRequest("FieldNvl",p);
        FieldNvl fieldMapToZero = new FieldNvl(w);

        String input = "42";
        String output = fieldMapToZero.fixField(input);

        assertEquals(input, output);
    }


}