/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testexamples;

import ie.rolfe.filewrangling.examples.FAAAirport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FAACarrierHistoryWRTest {

    @org.junit.jupiter.api.Test
    void testCarrierHistory() {

        Properties p = System.getProperties();
        File inputFile = new File(p.getProperty("user.dir") + "/testdata/carrier_history_subset.txt");

        File outputFile = new File("/tmp/a.out");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        FAAAirport f = new FAAAirport(inputFile, outputFile);
        f.makeChangedCopy();

        String line1;
        String line2;
        String line1Answer = "code,description";
        String line2Answer = "\"02Q\",\"Titan Airways (2006 - )\"";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(outputFile));
            line1 = reader.readLine();
            line2 = reader.readLine();
            reader.close();
            outputFile.delete();
            assertEquals(line1Answer, line1);
            assertEquals(line2Answer, line2);


        } catch (Exception e) {
            fail();
        }


    }

}