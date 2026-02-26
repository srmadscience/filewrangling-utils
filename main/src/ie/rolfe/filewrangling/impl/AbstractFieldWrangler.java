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

import static ie.rolfe.filewrangling.BaseFileWrangler.COMMA_SPLIT_REGEX;
import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM;

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

        if (fieldName.indexOf(DELIM) == -1) {
            fieldNames.add(fieldName);
        } else {
            String[] fields = fieldName.split(COMMA_SPLIT_REGEX, -1);
            for (int i = 1; i < fields.length; i++) {
                fieldNames.add(fields[i].trim());
            }
        }

    }

    public boolean isUsableForField(String fieldName) {
        return fieldNames.contains(fieldName);
    }

}



