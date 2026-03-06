/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.WranglerRequestException;
import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.ArrayList;

public abstract class AbstractLineWrangler implements CSVLineWranglerIFace {

    long startLine;
    long endLine;
    String[] columnNames = new String[0];


    ArrayList<CSVLineWranglerIFace> theExtraWranglers = new ArrayList<>();


    public AbstractLineWrangler() {
    }

    public AbstractLineWrangler(WranglerRequest wranglerRequest) {
        if (!wranglerRequest.requestType.equals(this.getClass().getSimpleName())) {
            throw new WranglerRequestException(wranglerRequest.requestType + " can't be used for " + this.getClass().getSimpleName());
        }
    }


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

    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public long getStartLine() {
        return startLine;
    }

    @Override
    public long getEndLine() {
        return endLine;
    }

    @Override
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
}

