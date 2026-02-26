/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;

import java.util.ArrayList;

public abstract class AbstractFieldWrangler implements CSVFieldWranglerIFace {

    ArrayList<String> fieldNames = new ArrayList<String>();
    ArrayList<CSVFieldWranglerIFace> theExtraWranglers = new ArrayList<CSVFieldWranglerIFace>();

    @Override
    public void addCSVFieldWranglerIFace(CSVFieldWranglerIFace anExtraWrangler) {
        theExtraWranglers.add(anExtraWrangler);
    }

    public String processExtraWranglers(String field) {

        String newField = field;

        for (CSVFieldWranglerIFace theExtraWrangler : theExtraWranglers) {
            newField = theExtraWrangler.fixField(newField);
        }

        return newField;
    }


    public void useForField(String fieldName) {
        fieldNames.add(fieldName);
    }

    public boolean isUsableForField(String fieldName) {
        return fieldNames.contains(fieldName);
    }

}



