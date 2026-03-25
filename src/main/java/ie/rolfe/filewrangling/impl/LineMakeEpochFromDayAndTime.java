/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.BaseFileWrangler;
import ie.rolfe.filewrangling.exceptions.BadInputDateException;
import ie.rolfe.filewrangling.exceptions.MissingColumnThisLineException;
import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static ie.rolfe.filewrangling.BaseFileWrangler.splitCSV;
import static ie.rolfe.filewrangling.FileWrangler.ALL_LINES;
import static ie.rolfe.filewrangling.FileWrangler.msg;

public class LineMakeEpochFromDayAndTime extends AbstractLineWrangler implements CSVLineWranglerIFace {

    int epochColumn = -1;
    int dateColumn= -1;
    int timeColumn= -1;
    String epochColumnName;
    String dateColumnName;
    String timeColumnName;
    SimpleDateFormat dateFormat;



    public LineMakeEpochFromDayAndTime(long startLine, long endLine, String epochColumnName, String dateColumnName, String timeColumnName, String inputFormat, Locale inputLocale) throws MissingColumnThisLineException {
        this.startLine = startLine;
        this.endLine = endLine;
        this.epochColumnName = epochColumnName;
        this.dateColumnName = dateColumnName;
        this.timeColumnName = timeColumnName;
        dateFormat = new SimpleDateFormat(inputFormat, inputLocale);
    }


    public LineMakeEpochFromDayAndTime(WranglerRequest wranglerRequest) {
        super(wranglerRequest);

        this.epochColumnName = wranglerRequest.getString("epochColumn");
        this.dateColumnName = wranglerRequest.getString("dateColumn");
        this.timeColumnName = wranglerRequest.getString("timeColumn");
        dateFormat = new SimpleDateFormat(wranglerRequest.getString("inputFormat"), wranglerRequest.getLocale("inputLocale"));

    }

    @Override
    public void setColumnNames(String[] columnNames) {
        super.setColumnNames(columnNames);
        this.epochColumn = getIdOfColumn(epochColumnName);
        this.dateColumn = getIdOfColumn(dateColumnName);
        this.timeColumn = getIdOfColumn(timeColumnName);
    }


    @Override
    public String fixLine(int lineNumber, String line) {

        if (line == null || line.isEmpty()) {
            return line;
        }

        if (lineNumber >= startLine && (endLine == ALL_LINES || lineNumber <= endLine)) {
            String[] fields = splitCSV(line);
            try {

                if (fields.length == 0) {
                    return line;
                }

                String newDateAsString = fields[dateColumn].replace("\"","")
                        + " " + fields[timeColumn].replace("\"","");
                Date newDate = dateFormat.parse(newDateAsString);

                fields[epochColumn] = String.valueOf(newDate.getTime());

                StringBuilder newString = new StringBuilder();
                for (int i = 0; i < fields.length; i++) {

                    if (i > 0) {
                        newString.append(',');
                    }
                    newString.append(fields[i]);
                }

                return processExtraWranglers(lineNumber, newString.toString());

            } catch (ParseException e) {
                throw new BadInputDateException(fields[dateColumn] + " " + fields[timeColumn], e.getMessage());
            }

        }
        return processExtraWranglers(lineNumber, line);
    }

    @Override
    public String toString() {
        return "LineMakeEpochFromDayAndTime{" +
                "epochColumn=" + epochColumn +
                ", dateColumn=" + dateColumn +
                ", timeColumn=" + timeColumn +
                ", epochColumnName='" + epochColumnName + '\'' +
                ", dateColumnName='" + dateColumnName + '\'' +
                ", timeColumnName='" + timeColumnName + '\'' +
                ", dateFormat=" + dateFormat +
                ", startLine=" + startLine +
                ", endLine=" + endLine +
                ", columnNames=" + Arrays.toString(columnNames) +
                ", theExtraWranglers=" + theExtraWranglers +
                '}';
    }

}
