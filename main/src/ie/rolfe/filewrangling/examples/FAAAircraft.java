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
import ie.rolfe.filewrangling.impl.*;

import java.io.File;

public class FAAAircraft extends BaseFileWrangler {
    public FAAAircraft(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        this.addLineChange(new LineChangeOverrideColumnValue(1, 1, 0, "n_number"));
        this.addLineChange(new LineChangeOverrideColumnValue(1, 1, 1, "model"));
        this.addLineChange(new LineChangeOverrideColumnValue(1, 1, 2, "YEAR"));
        this.addLineChange(new LineForceToLowerCase(1, 1));
        this.addLineChange(new LineForgetRest(1, 1, 3));

        CSVFieldWranglerIFace[] fields = new CSVFieldWranglerIFace[34];

        fields[0] = new FieldPrepend("N");
        fields[2] = new FieldKeep();
        fields[4] = new FieldNvl();
        this.setFieldChanges(fields);
    }

    public static void main(String[] args) {

        File[] files = getFiles(args);

        FAAAircraft theFileWrangler = new FAAAircraft(files[0], files[1]);

        theFileWrangler.makeChangedCopy();

        System.exit(0);

    }
}
