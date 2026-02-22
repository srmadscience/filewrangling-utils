/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.FieldFixDateFormat;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldFixUSAMPMDateTest {

    @org.junit.jupiter.api.Test
    void fixFieldNull() {

        final String inputMask = "MM/dd/yy";
        final String outputMask = "dd/MM/yy";

        FieldFixDateFormat fieldToTest = new FieldFixDateFormat(inputMask, Locale.US, outputMask, Locale.UK);

        String input = null;
        String output = fieldToTest.fixField(input);

        assertEquals("", output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldNotMull() {

        final String inputMask = "MM/dd/yy";
        final String outputMask = "dd/MM/yy";
        final String simpleTestUS = "12/30/26";
        final String simpleTestUK = "30/12/26";

        FieldFixDateFormat fieldToTest = new FieldFixDateFormat(inputMask, Locale.US, outputMask, Locale.UK);

        String input = simpleTestUS;
        String output = fieldToTest.fixField(input);

        assertEquals(simpleTestUK, output);
    }

    @org.junit.jupiter.api.Test
    void fixFieldUSFormat() {

        final String inputMask = "MM/dd/yyyy KK:mm:ss a";
        final String outputMask = "dd/MM/yy";
        final String simpleTestUS = "2/1/2025 12:00:00 AM";
        final String simpleTestUK = "01/02/25";

        FieldFixDateFormat fieldToTest = new FieldFixDateFormat(inputMask, Locale.US, outputMask, Locale.UK);

        String input = simpleTestUS;
        String output = fieldToTest.fixField(input);

        assertEquals(simpleTestUK, output);
    }

}
