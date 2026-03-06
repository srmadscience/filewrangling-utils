/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.testexamples;

import ie.rolfe.filewrangling.FileWrangler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DMAirportTestWR {

    @org.junit.jupiter.api.Test
    void testDMAirpprt() {

        Properties p = System.getProperties();
        File inputFile = new File(p.getProperty("user.dir") + "/testdata/airports.csv");
        File jsonFile = new File(p.getProperty("user.dir") + "/testdata/airports.json");

        File outputFile = new File("/tmp/a.out");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        FileWrangler f = new FileWrangler(inputFile, outputFile, jsonFile);
        f.parseJsonFile();
        f.makeChangedCopy();

        String line1;
        String line2;
        String line1Answer = "\"id\",\"ident\",\"type\",\"name\",\"latitude_deg\",\"longitude_deg\",\"elevation_ft\",\"continent\",\"iso_country\",\"iso_region\",\"municipality\",\"scheduled_service\",\"icao_code\",\"iata_code\",\"gps_code\",\"local_code\",\"home_link\",\"wikipedia_link\",\"keywords\"";
        String line2Answer = "4650,\"03N\",\"small_airport\",\"Utirik Airport\",11.222219,169.851429,4,\"OC\",\"MH\",\"MH-UTI\",\"Utirik Island\",\"yes\",,\"UTK\",\"03N\",\"03N\",,\"https://en.wikipedia.org/wiki/Utirik_Airport\",";

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