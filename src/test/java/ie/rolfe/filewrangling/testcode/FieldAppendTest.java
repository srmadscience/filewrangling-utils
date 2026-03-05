package ie.rolfe.filewrangling.testcode;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.impl.FieldAppend;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Properties;

import static ie.rolfe.filewrangling.BaseFileWrangler.msg;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
class FieldAppendTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        FieldAppend fieldToTest = new FieldAppend("N");

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        final String StringToAppend = "N";
        final String payload = "1976T";

        FieldAppend fieldToTest = new FieldAppend( StringToAppend);

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals(payload +  StringToAppend , output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNullWR() {

        Properties p = new Properties();
        p.put("thingToAppend", "N");
        WranglerRequest w = new WranglerRequest("FieldAppend", p);

        FieldAppend fieldToTest = new FieldAppend(w);
        Gson g = new Gson();
        msg(g.toJson(w));

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMullWR() {

        final String StringToAppend = "N";
        final String payload = "1976T";

        Properties p = new Properties();
        p.put("thingToAppend",  StringToAppend);
        WranglerRequest w = new WranglerRequest("FieldAppend", p);

        FieldAppend fieldToTest = new FieldAppend(w);

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals( payload + StringToAppend, output);
    }


}