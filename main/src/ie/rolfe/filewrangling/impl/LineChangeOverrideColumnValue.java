/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;

public class LineChangeOverrideColumnValue extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int startLine;
    int endLine;
    int fieldNumberStartingAtZero;
    String newValue;

    public LineChangeOverrideColumnValue(int startLine, int endLine, int fieldNumberStartingAtZero, String newValue) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.fieldNumberStartingAtZero = fieldNumberStartingAtZero;
        this.newValue = newValue;
    }


    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && lineNumber <= endLine) {

            String[] fields = line.split(",");

            if (fields.length == 0) {
                return line;
            }

            if (fieldNumberStartingAtZero < 0 || fieldNumberStartingAtZero >= fields.length) {
                return line;
            }

            fields[fieldNumberStartingAtZero] = newValue;

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
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
