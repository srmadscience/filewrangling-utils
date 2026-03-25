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

import java.util.Arrays;
import java.util.HashSet;

import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM;
import static ie.rolfe.filewrangling.BaseFileWrangler.splitCSV;
import static ie.rolfe.filewrangling.FileWrangler.ALL_LINES;

public class LineForgetColumnsInLine extends AbstractLineWrangler implements CSVLineWranglerIFace {

    HashSet<String> neededColumnNames = new HashSet<>();

    public LineForgetColumnsInLine(long startLine, long endLine, String columnNames) {
        this.startLine = startLine;
        this.endLine = endLine;
        splitAndStoreColumnNames(columnNames);
    }


    public LineForgetColumnsInLine(WranglerRequest wranglerRequest) {
        super(wranglerRequest);
        splitAndStoreColumnNames((String) wranglerRequest.get("columnNames"));
    }


    public void splitAndStoreColumnNames(String fieldNameList) {

        if (fieldNameList.indexOf(DELIM) == -1) {
            neededColumnNames.add(fieldNameList.replace("\"", "").trim().toLowerCase());
        } else {
            String[] fields = splitCSV(fieldNameList);
            for (int i = 0; i < fields.length; i++) {
                neededColumnNames.add(fields[i].replace("\"", "").trim().toLowerCase());
            }
        }

    }

    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && (endLine == ALL_LINES || lineNumber <= endLine)) {

            String[] fields = line.split(String.valueOf(BaseFileWrangler.DELIM));

            if (fields.length == 0) {
                return line;
            }

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < fields.length; i++) {
                if (neededColumnNames.contains(fields[i].toLowerCase())) {
                   // Do nothing...
                } else {
                    if (i > 0) {
                        newString.append(',');
                    }
                    newString.append(fields[i]);
                }

            }

            return processExtraWranglers(lineNumber, newString.toString());
        }
        return processExtraWranglers(lineNumber, line);
    }


    @Override
    public String toString() {
        return "LineForgetColumnsInLine{" +
                "neededColumnNames=" + neededColumnNames +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                ", columnNames=" + Arrays.toString(columnNames) +
                ", theExtraWranglers=" + theExtraWranglers +
                '}';
    }
}
