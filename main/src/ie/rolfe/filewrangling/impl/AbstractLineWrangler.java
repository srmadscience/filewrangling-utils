/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;

import java.util.ArrayList;

public abstract class AbstractLineWrangler implements CSVLineWranglerIFace {

    ArrayList<CSVLineWranglerIFace> theExtraWranglers = new ArrayList<CSVLineWranglerIFace>();

    @Override
    public void addCSVLineWranglerIFace(CSVLineWranglerIFace theExtraWrangler) {
        theExtraWranglers.add(theExtraWrangler);
    }

    public String processExtraWranglers(int lineNumber, String field) {

        String newField = field;

        for (CSVLineWranglerIFace theExtraWrangler : theExtraWranglers) {
            newField = theExtraWrangler.fixLine(lineNumber, newField);
        }

        return newField;
    }

}
