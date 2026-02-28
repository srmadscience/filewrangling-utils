/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.WranglerRequestException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.ArrayList;

import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM_SPLIT_REGEX;
import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM;

public abstract class AbstractFieldWrangler implements CSVFieldWranglerIFace {

    ArrayList<String> fieldNames = new ArrayList<String>();
    ArrayList<CSVFieldWranglerIFace> theExtraWranglers = new ArrayList<CSVFieldWranglerIFace>();

    public AbstractFieldWrangler() {}

    public AbstractFieldWrangler(WranglerRequest wranglerRequest)
    {
        if (! wranglerRequest.requestType.equals(this.getClass().getSimpleName())) {
            throw new WranglerRequestException(wranglerRequest.requestType + " can't be used for " + this.getClass().getSimpleName());
        }
    }


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


    public void useForFields(String fieldNameList) {

        if (fieldNameList.indexOf(DELIM) == -1) {
            fieldNames.add(fieldNameList);
        } else {
            String[] fields = fieldNameList.split(DELIM_SPLIT_REGEX, -1);
            for (int i = 1; i < fields.length; i++) {
                fieldNames.add(fields[i].trim());
            }
        }

    }

    public boolean isUsedForField(String fieldName) {
        return fieldNames.contains(fieldName);
    }

}



