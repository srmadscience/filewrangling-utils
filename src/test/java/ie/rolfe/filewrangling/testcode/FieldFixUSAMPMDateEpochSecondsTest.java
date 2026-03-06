/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.impl.FieldChangeDateFormatToEpochSeconds;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.Locale;
import java.util.Properties;

import static ie.rolfe.filewrangling.BaseFileWrangler.msg;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldFixUSAMPMDateEpochSecondsTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        final String inputMask = "MM/dd/yy";
        final String outputMask = "dd/MM/yy";

        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(inputMask, Locale.US);

        String input = null;
        String output = fieldToTest.fixField(input);

        assertEquals("", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        final String inputMask = "MM/dd/yy";
        final String simpleTestUS = "12/30/26";
        final String epochMSString = "1798588800000";

        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(inputMask, Locale.US);

        String output = fieldToTest.fixField(simpleTestUS);

        assertEquals(epochMSString, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNullRW() {

        final String inputMask = "MM/dd/yy";

        Properties p = new Properties();
        p.put("inputFormat", inputMask);
        p.put("inputLocale", Locale.US);
        WranglerRequest w = new WranglerRequest("FieldChangeDateFormatToEpochSeconds", p);


        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(w);

        String input = null;
        String output = fieldToTest.fixField(input);

        assertEquals("", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMullRW() {

        final String inputMask = "MM/dd/yy";
        final String simpleTestUS = "12/30/26";
        final String simpleTestUK = "1798588800000";


        Properties p = new Properties();
        p.put("inputFormat", inputMask);
        p.put("inputLocale", Locale.US);

        WranglerRequest w = new WranglerRequest("FieldChangeDateFormatToEpochSeconds", p);

        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(w);

        String input = simpleTestUS;
        String output = fieldToTest.fixField(input);

        assertEquals(simpleTestUK, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldUSFormat() {

        final String inputMask = "MM/dd/yyyy KK:mm:ss a";
        final String simpleTestUS = "2/1/2025 12:00:00 AM";
        final String simpleTestUK = "1738411200000";

        Properties p = new Properties();
        p.put("inputFormat", inputMask);
        p.put("inputLocale", Locale.US);

        WranglerRequest w = new WranglerRequest("FieldChangeDateFormatToEpochSeconds", p);
        w.setFieldNames("A_FIELD, ANOTHER_FIELD");

        Gson g = new Gson();
        msg(g.toJson(w));
        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(w);

        String input = simpleTestUS;
        String output = fieldToTest.fixField(input);

        assertEquals(simpleTestUK, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldUSFormatToMS() {

        final String inputMask = "MM/dd/yyyy KK:mm:ss a";
        final String simpleTestUS = "2/1/2025 12:00:00 AM";
        final String simpleTestUK = "1738411200000";

        Properties p = new Properties();
        p.put("inputFormat", inputMask);
        p.put("inputLocale", Locale.US);

        WranglerRequest w = new WranglerRequest("FieldChangeDateFormatToEpochSeconds", p);
        w.setFieldNames("A_FIELD, ANOTHER_FIELD");

        Gson g = new Gson();
        msg(g.toJson(w));
        FieldChangeDateFormatToEpochSeconds fieldToTest = new FieldChangeDateFormatToEpochSeconds(w);

        String input = simpleTestUS;
        String output = fieldToTest.fixField(input);

        assertEquals(simpleTestUK, output);
    }


}
