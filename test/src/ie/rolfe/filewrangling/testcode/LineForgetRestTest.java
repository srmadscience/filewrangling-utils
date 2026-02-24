/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testcode;

import ie.rolfe.filewrangling.impl.LineForgetRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}