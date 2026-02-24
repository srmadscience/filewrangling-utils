/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testexamples;

import ie.rolfe.filewrangling.examples.FAAOntimeHistory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FAAOntimeTest {

    @org.junit.jupiter.api.Test
    void testFAAOntimeHistory() {

        Properties p = System.getProperties();
        File inputFile = new File(p.getProperty("user.dir") + "/testdata/ontime_subset.csv");

        File outputFile = new File("/tmp/a.out");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        FAAOntimeHistory f = new FAAOntimeHistory(inputFile, outputFile);
        f.makeChangedCopy();

        String line1;
        String line2;
        String line1Answer = "fl_date,op_unique_carrier,tail_num,op_carrier_fl_num,origin,origin_city_name,dest,dest_city_name,crs_dep_time,dep_time,dep_delay,crs_arr_time,crs_elapsed_time,distance";
        String line2Answer = "01/07/2021,9E,N131EV,4979,ATL,\"Atlanta, GA\",DAY,\"Dayton, OH\",1632,1632,0.00,1809,97.00,432.00";

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