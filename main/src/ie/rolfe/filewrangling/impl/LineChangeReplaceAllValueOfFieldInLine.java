/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.BaseFileWrangler;
import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

public class LineChangeReplaceAllValueOfFieldInLine extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int startLine;
    int endLine;
    String columnValue;
    String newValue;

    public LineChangeReplaceAllValueOfFieldInLine(int startLine, int endLine, String columnValue, String newValue) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.columnValue = columnValue;
        this.newValue = newValue;
    }

    public LineChangeReplaceAllValueOfFieldInLine(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

        this.startLine =  (Integer) wranglerRequest.get("startLine");
        this.endLine =  (Integer) wranglerRequest.get("endLine");
        this.columnValue = (String) wranglerRequest.get("columnValue");
        this.newValue = (String) wranglerRequest.get("newValue");
    }


    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && lineNumber <= endLine) {

            String[] fields = line.split(String.valueOf(BaseFileWrangler.DELIM));

            if (fields.length == 0) {
                return line;
            }

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    newString.append(',');
                }
                if (fields[i].equals(columnValue)) {
                    newString.append(newValue);
                } else {
                    newString.append(fields[i]);
                }

            }

            return processExtraWranglers(lineNumber, newString.toString());
        }
        return processExtraWranglers(lineNumber, line);
    }


}
