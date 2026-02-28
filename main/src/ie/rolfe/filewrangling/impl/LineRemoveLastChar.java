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

public class LineRemoveLastChar extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int startLine;
    int endLine;

    public LineRemoveLastChar(int startLine, int endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public LineRemoveLastChar(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

        this.startLine =  (Integer) wranglerRequest.get("startLine");
        this.endLine =  (Integer) wranglerRequest.get("endLine");
    }

    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && lineNumber <= endLine) {
            return processExtraWranglers(lineNumber, line.substring(0, line.length() - 1));
        }
        return processExtraWranglers(lineNumber, line);
    }


}
