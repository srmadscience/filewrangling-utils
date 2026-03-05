package ie.rolfe.filewrangling.testcode;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.impl.FieldPrepend;
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

    @org.junit.jupiter.api.Test
    void fixFieldNullWR() {

        Properties p = new Properties();
        p.put("thingToPrepend", "N");
        WranglerRequest w = new WranglerRequest("FieldPrepend", p);

        FieldPrepend fieldToTest = new FieldPrepend(w);
        Gson g = new Gson();
        msg(g.toJson(w));

        String input = null;
        String output = fieldToTest.fixField(input);

        assertNull(output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMullWR() {

        final String StringToPrepend = "N";
        final String payload = "1976T";

        Properties p = new Properties();
        p.put("thingToPrepend", StringToPrepend);
        WranglerRequest w = new WranglerRequest("FieldPrepend", p);

        FieldPrepend fieldToTest = new FieldPrepend(w);

        String input = payload;
        String output = fieldToTest.fixField(input);

        assertEquals(StringToPrepend + payload, output);
    }


}