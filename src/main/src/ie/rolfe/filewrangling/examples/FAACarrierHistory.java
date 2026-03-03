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
import ie.rolfe.filewrangling.impl.FieldKeep;
import ie.rolfe.filewrangling.impl.LineForceToLowerCase;

import java.io.File;

public class FAACarrierHistory extends BaseFileWrangler {
    public FAACarrierHistory(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        this.addLineChange(new LineForceToLowerCase(1, 1));

        CSVFieldWranglerIFace[] fields = new CSVFieldWranglerIFace[2];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new FieldKeep();
        }

        this.setFieldChanges(fields);
    }

    public static void main(String[] args) {

        File[] files = getFiles(args);

        FAACarrierHistory theFileWrangler = new FAACarrierHistory(files[0], files[1]);

        theFileWrangler.makeChangedCopy();

        System.exit(0);

    }

}
