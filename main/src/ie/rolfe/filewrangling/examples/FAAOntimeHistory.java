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
import ie.rolfe.filewrangling.impl.LineForceToLowerCase;

import java.io.File;
import java.util.Locale;

public class FAAOntimeHistory extends BaseFileWrangler {
    public FAAOntimeHistory(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        final String inputMask = "MM/dd/yyyy KK:mm:ss a";
        final String outputMask = "dd/MM/yyyy";

        this.addLineChange(new LineForceToLowerCase(1, 1));

        CSVFieldWranglerIFace[] fields = new CSVFieldWranglerIFace[16];
        fields[0] = new FieldFixDateFormat(inputMask, Locale.US, outputMask, Locale.UK);
        for (int i = 1; i < fields.length; i++) {
            fields[i] = new FieldKeep();
        }

        this.setFieldChanges(fields);
    }

}
