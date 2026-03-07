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

class FAAOntime2WRTest {

    @org.junit.jupiter.api.Test
    void testFAAOntimeHistory() {

        Properties p = System.getProperties();
        File inputFile = new File(p.getProperty("user.dir") + "/testdata/ontime_all.csv");
        File jsonFile = new File(p.getProperty("user.dir") + "/testdata/ontime_all.json");

        File outputFile = new File("/tmp/a.out");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        FileWrangler f = new FileWrangler(inputFile, outputFile, jsonFile);
        f.parseJsonFile();
        f.makeChangedCopy();

        String line1;
        String line2;
        String line1Answer = "year,quarter,month,dayofmonth,dayofweek,flightdate,marketing_airline_network,operated_or_branded_code_share_partners,dot_id_marketing_airline,iata_code_marketing_airline,flight_number_marketing_airline,originally_scheduled_code_share_airline,dot_id_originally_scheduled_code_share_airline,iata_code_originally_scheduled_code_share_airline,flight_num_originally_scheduled_code_share_airline,operating_airline ,dot_id_operating_airline,iata_code_operating_airline,tail_number,flight_number_operating_airline,originairportid,originairportseqid,origincitymarketid,origin,origincityname,originstate,originstatefips,originstatename,originwac,destairportid,destairportseqid,destcitymarketid,dest,destcityname,deststate,deststatefips,deststatename,destwac,crsdeptime,deptime,depdelay,depdelayminutes,depdel15,departuredelaygroups,deptimeblk,taxiout,wheelsoff,wheelson,taxiin,crsarrtime,arrtime,arrdelay,arrdelayminutes,arrdel15,arrivaldelaygroups,arrtimeblk,cancelled,cancellationcode,diverted,crselapsedtime,actualelapsedtime,airtime,flights,distance,distancegroup,carrierdelay,weatherdelay,nasdelay,securitydelay,lateaircraftdelay,firstdeptime,totaladdgtime,longestaddgtime,divairportlandings,divreacheddest,divactualelapsedtime,divarrdelay,divdistance,div1airport,div1airportid,div1airportseqid,div1wheelson,div1totalgtime,div1longestgtime,div1wheelsoff,div1tailnum,div2airport,div2airportid,div2airportseqid,div2wheelson,div2totalgtime,div2longestgtime,div2wheelsoff,div2tailnum,div3airport,div3airportid,div3airportseqid,div3wheelson,div3totalgtime,div3longestgtime,div3wheelsoff,div3tailnum,div4airport,div4airportid,div4airportseqid,div4wheelson,div4totalgtime,div4longestgtime,div4wheelsoff,div4tailnum,div5airport,div5airportid,div5airportseqid,div5wheelson,div5totalgtime,div5longestgtime,div5wheelsoff,div5tailnum,duplicate,x";
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