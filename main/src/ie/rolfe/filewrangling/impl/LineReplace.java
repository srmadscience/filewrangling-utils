/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;

public class LineReplace extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int startLine;
    int endLine;
    String newValue;

    public LineReplace(int startLine, int endLine, String newValue) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.newValue = newValue;
    }

    public LineReplace(String newValue) {
        this.startLine = 1;
        this.endLine = 1;
        this.newValue = newValue;
    }

    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && lineNumber <= endLine) {
            return processExtraWranglers(lineNumber, newValue);
        }
        return processExtraWranglers(lineNumber, line);
    }


}
