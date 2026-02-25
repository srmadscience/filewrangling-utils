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
import ie.rolfe.filewrangling.impl.FieldNvl;
import ie.rolfe.filewrangling.impl.LineReplace;

import java.io.File;

public class FAAAircraftType extends BaseFileWrangler {
    public FAAAircraftType(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        this.addLineChange(new LineReplace("code,mfr,model,engines,seats"));

        CSVFieldWranglerIFace[] fields = new CSVFieldWranglerIFace[9];

        fields[0] = new FieldNvl();
        fields[1] = new FieldNvl();
        fields[2] = new FieldNvl();
        fields[7] = new FieldNvl();
        fields[8] = new FieldNvl();

        this.setFieldChanges(fields);
    }

    public static void main(String[] args) {

        File[] files = getFiles(args);

        FAAAircraftType theFileWrangler = new FAAAircraftType(files[0], files[1]);

        theFileWrangler.makeChangedCopy();

        System.exit(0);

    }
}
