/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineForgetRest;
import ie.rolfe.filewrangling.model.WranglerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineForgetRestTest {

    @Test
    void testNull() {
        LineForgetRest l = new LineForgetRest(1, 1, 3);

        String output = l.fixLine(1, null);
        Assertions.assertNull(output);
    }

    @Test
    void testNotNull() {
        LineForgetRest l = new LineForgetRest(1, 1, 3);
        String front = "A,B,C";
        String back = ",D,E,F";
        String output = l.fixLine(1, front + back);
        assertEquals(front, output);
    }

    @Test
    void testNotRightLine() {
        LineForgetRest l = new LineForgetRest(1, 1, 3);
        String front = "A,B,C";
        String back = ",D,E,F";
        String output = l.fixLine(2, front + back);
        assertEquals(front + back, output);
    }


    @Test
    void testNullWR() {


        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("lastColumn", 3);
        WranglerRequest w = new WranglerRequest("LineForgetRest", p);
        LineForgetRest l = new LineForgetRest(w);

        String output = l.fixLine(1, null);
        Assertions.assertNull(output);
    }

    @Test
    void testNotNullWR() {
        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("lastColumn", 3);
        WranglerRequest w = new WranglerRequest("LineForgetRest", p);
        LineForgetRest l = new LineForgetRest(w);
        String front = "A,B,C";
        String back = ",D,E,F";
        String output = l.fixLine(1, front + back);
        assertEquals(front, output);
    }

    @Test
    void testNotRightLineWR() {
        Properties p = new Properties();
        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("lastColumn", 3);
        WranglerRequest w = new WranglerRequest("LineForgetRest", p);
        LineForgetRest l = new LineForgetRest(w);
        String front = "A,B,C";
        String back = ",D,E,F";
        String output = l.fixLine(2, front + back);
        assertEquals(front + back, output);
    }
}