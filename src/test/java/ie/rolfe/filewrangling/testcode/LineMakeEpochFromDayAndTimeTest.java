package ie.rolfe.filewrangling.testcode;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.impl.LineMakeEpochFromDayAndTime;
import ie.rolfe.filewrangling.model.WranglerRequest;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Properties;

import static ie.rolfe.filewrangling.FileWrangler.msg;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LineMakeEpochFromDayAndTimeTest {

    @Test
    void fixLineNotNull() {

        final String inputMask = "MM/dd/yy HHmm";
        final String simpleDateUS = "12/30/26";
        final String simpleTimeUS = "1200";
        final String epochMSString = "1798632000000";

        String[] testCols = {
                "dt",
                "tm",
                "out"
        };

        String[] testLines = {
                simpleDateUS,
                simpleTimeUS,
                "0"
        };

        Properties p = new Properties();

        p.put("startLine", 1);
        p.put("endLine", 1);
        p.put("epochColumn", "out");
        p.put("dateColumn", "dt");
        p.put("timeColumn", "tm");
        p.put("inputFormat", inputMask);
        p.put("inputLocale", Locale.UK);


        WranglerRequest wr = new WranglerRequest("LineMakeEpochFromDayAndTime", p);

        Gson g = new Gson();

        String wrAsString = g.toJson(wr);
        msg(wrAsString);

        LineMakeEpochFromDayAndTime t = new LineMakeEpochFromDayAndTime(wr);
        t.setColumnNames(testCols);

        String testLine = testLines[0] + "," + testLines[1] + "," + testLines[2];
        String expectedLine = testLines[0] + "," + testLines[1] + "," + epochMSString;

        String result = t.fixLine(1, testLine);


        assertEquals(result, expectedLine);

    }
}