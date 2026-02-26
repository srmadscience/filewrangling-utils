/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.examples;

import ie.rolfe.filewrangling.BaseFileWrangler;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.impl.FieldFixDateFormat;
import ie.rolfe.filewrangling.impl.FieldKeep;
import ie.rolfe.filewrangling.impl.FieldNvl;
import ie.rolfe.filewrangling.impl.LineForceToLowerCase;

import java.io.File;
import java.util.Locale;

public class FAAOntimeHistory extends BaseFileWrangler {
    public FAAOntimeHistory(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        final String inputMask = "MM/dd/yyyy KK:mm:ss a";
        final String outputMask = "dd/MM/yyyy";

        this.addLineChange(new LineForceToLowerCase(1, 1));


        CSVFieldWranglerIFace dateField = new FieldFixDateFormat(inputMask, Locale.US, outputMask, Locale.UK);
        dateField.useForField("FL_DATE");

        CSVFieldWranglerIFace wantedFields = new FieldKeep();
        wantedFields.useForField("FL_DATE,OP_UNIQUE_CARRIER,TAIL_NUM,OP_CARRIER_FL_NUM,ORIGIN,ORIGIN_CITY_NAME,DEST,DEST_CITY_NAME,CRS_DEP_TIME,DEP_TIME,DEP_DELAY,CRS_ARR_TIME,CRS_ELAPSED_TIME,DISTANCE");

        CSVFieldWranglerIFace nvlField = new FieldNvl("UNKNOWN");
        nvlField.useForField("TAIL_NUM");

        CSVFieldWranglerIFace nvlZeroField = new FieldKeep();
        nvlZeroField.useForField("CRS_DEP_TIME,DEP_TIME,DEP_DELAY,CRS_ARR_TIME,CRS_ELAPSED_TIME,DISTANCE");

        this.addField(wantedFields);
        this.addField(dateField);
        this.addField(nvlField);
        this.addField(nvlZeroField);

    }

    public static void main(String[] args) {

        File[] files = getFiles(args);

        FAAOntimeHistory theFileWrangler = new FAAOntimeHistory(files[0], files[1]);

        theFileWrangler.makeChangedCopy();

        System.exit(0);

    }


}
