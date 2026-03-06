/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.SkipThisLineException;
import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.util.HashSet;

import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM;
import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM_SPLIT_REGEX;

public class LineSkipIfNull extends AbstractLineWrangler implements CSVLineWranglerIFace {

    HashSet<String> neededColumnNames = new HashSet<>();
    HashSet<Integer> neededColumnIds = new HashSet<>();

    public LineSkipIfNull(long startLine, long endLine, String neededColumnNames) {
        this.startLine = startLine;
        this.endLine = endLine;
        splitAndStoreColumnNames(neededColumnNames);
    }


    public LineSkipIfNull(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

        this.startLine = wranglerRequest.getInt("startLine");
        this.endLine = wranglerRequest.getInt("endLine");
        splitAndStoreColumnNames((String) wranglerRequest.get("columnNames"));

    }

    @Override
    public void setColumnNames(String[] columnNames) {
        super.setColumnNames(columnNames);

        for (int i = 0; i < columnNames.length; i++) {
            if (neededColumnNames.contains(columnNames[i])) {
                neededColumnIds.add(i);
            }
        }
    }


    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }


        String[] fields = line.split(DELIM_SPLIT_REGEX);

        if (fields.length == 0) {
            return line;
        }

        for (Integer aColId : neededColumnIds) {

            if (aColId >= fields.length) {
                throw new SkipThisLineException("Missing", lineNumber, line);
            } else if (fields[aColId] == null || fields[aColId].isEmpty()) {
                throw new SkipThisLineException(columnNames[aColId], lineNumber, line);
            }
        }
//            for (int i = 0; i < fields.length; i++) {
//                 if (neededColumnNames.contains(columnNames[i]) && (fields[i] == null || fields[i].isEmpty())) {
//                    throw new SkipThisLineException(columnNames[i], lineNumber, line);
//                }
//
//            }


        return processExtraWranglers(lineNumber, line);
    }


    public void splitAndStoreColumnNames(String fieldNameList) {

        if (fieldNameList.indexOf(DELIM) == -1) {
            neededColumnNames.add(fieldNameList.replace("\"", "").trim());
        } else {
            String[] fields = fieldNameList.split(DELIM_SPLIT_REGEX, -1);
            for (int i = 0; i < fields.length; i++) {
                neededColumnNames.add(fields[i].replace("\"", "").trim());
            }
        }

    }


}
