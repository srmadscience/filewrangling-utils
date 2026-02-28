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

public class LineForgetRest extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int startLine;
    int endLine;
    int lastColumn;

    public LineForgetRest(int startLine, int endLine, int lastColumn) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.lastColumn = lastColumn;

    }

    public LineForgetRest(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

        this.startLine =  (Integer) wranglerRequest.get("startLine");
        this.endLine =  (Integer) wranglerRequest.get("endLine");
        this.lastColumn = (Integer) wranglerRequest.get("lastColumn");
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
            for (int i = 0; i < fields.length && i < lastColumn; i++) {
                if (i > 0) {
                    newString.append(',');
                }

                newString.append(fields[i]);


            }

            return processExtraWranglers(lineNumber, newString.toString());
        }
        return processExtraWranglers(lineNumber, line);
    }


}
