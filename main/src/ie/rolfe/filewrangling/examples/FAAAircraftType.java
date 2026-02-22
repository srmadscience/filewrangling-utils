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
import ie.rolfe.filewrangling.impl.FieldMapToZero;
import ie.rolfe.filewrangling.impl.LineReplace;

import java.io.File;

public class FAAAircraftType extends BaseFileWrangler {
    public FAAAircraftType(File inputFile, File outputFile) {
        super(inputFile, outputFile);

        this.addLineChange(new LineReplace("code,mfr,model,engines,seats"));

        CSVFieldWranglerIFace[] fields = new CSVFieldWranglerIFace[9];

        fields[0] = new FieldMapToZero();
        fields[1] = new FieldMapToZero();
        fields[2] = new FieldMapToZero();
        fields[7] = new FieldMapToZero();
        fields[8] = new FieldMapToZero();

        this.setFieldChanges(fields);
    }

}
