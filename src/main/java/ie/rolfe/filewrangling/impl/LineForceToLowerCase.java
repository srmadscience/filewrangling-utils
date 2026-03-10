/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import static ie.rolfe.filewrangling.FileWrangler.ALL_LINES;

public class LineForceToLowerCase extends AbstractLineWrangler implements CSVLineWranglerIFace {


    public LineForceToLowerCase(long startLine, long endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public LineForceToLowerCase(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

    }


    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && (endLine == ALL_LINES || lineNumber <= endLine)) {
            return processExtraWranglers(lineNumber, line.toLowerCase());
        }
        return processExtraWranglers(lineNumber, line);
    }

    @Override
    public String toString() {
        return "LineForceToLowerCase{" +
                "startLine=" + startLine +
                ", endLine=" + endLine +
                ", theExtraWranglers=" + theExtraWranglers +
                '}';
    }

}
