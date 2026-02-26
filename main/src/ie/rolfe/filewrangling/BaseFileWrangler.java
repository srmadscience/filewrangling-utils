/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling;

import ie.rolfe.filewrangling.exceptions.SkipThisFieldException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.impl.AbstractLineWrangler;
import ie.rolfe.filewrangling.impl.FieldSkip;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class BaseFileWrangler {

    public static final char DELIM = ',';
    File inputFile = null;
    File outputFile = null;
    int startFieldLine = 2;

    ArrayList<AbstractLineWrangler> lineChanges = new ArrayList<AbstractLineWrangler>();
    ArrayList<CSVFieldWranglerIFace> rawFieldChanges = new ArrayList<CSVFieldWranglerIFace>();
    CSVFieldWranglerIFace[] fieldChanges = new CSVFieldWranglerIFace[0];


    int headerLine = 1;

    public BaseFileWrangler(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Print a formatted message.
     *
     * @param message
     */
    public static void msg(String message) {

        SimpleDateFormat sdfDate;
        sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        System.out.println(strDate + ":" + message);

    }

    protected static File[] getFiles(String[] args) {
        msg("Parameters:" + Arrays.toString(args));

        if (args.length != 2) {
            msg("Usage: inputfilename outputfilename");
            System.exit(1);
        }

        File inputFile = null;
        File outputFile = null;


        try {

            inputFile = new File(args[0]);

            if (!inputFile.exists()) {
                msg("Input file does not exist");
                System.exit(2);
            }

            if (!inputFile.isFile()) {
                msg("Input file is not a file");
                System.exit(3);
            }


            if (!inputFile.canRead()) {
                msg("Input file is not readable");
                System.exit(4);
            }

            outputFile = new File(args[1]);
            if (outputFile.exists()) {
                msg("Output file already exists");
                System.exit(5);
            }


        } catch (Exception e) {
            msg(e.getMessage());
        }

        return new File[]{inputFile, outputFile};
    }

    public static void main(String[] args) {

        File[] files = getFiles(args);

        BaseFileWrangler theFileWrangler = new BaseFileWrangler(files[0], files[1]);

        theFileWrangler.makeChangedCopy();

    }

    public void addLineChange(AbstractLineWrangler newLineChange) {
        lineChanges.add(newLineChange);
    }

    public void setFieldChanges(CSVFieldWranglerIFace[] fieldChanges) {
        this.fieldChanges = fieldChanges;

        for (int i = 0; i < fieldChanges.length; i++) {
            if (fieldChanges[i] == null) {
                fieldChanges[i] = new FieldSkip();
            }
        }
    }

    public String processLine(int lineNumber, String line) {

        if (line == null) {
            return line;
        }

        if (lineNumber == headerLine) {

            mapFieldsToPositions(line);

        }

        String newLine = line;

        for (AbstractLineWrangler lineChange : lineChanges) {
            newLine = lineChange.fixLine(lineNumber, newLine);
        }

        if (newLine.indexOf(DELIM) == 0) {
            return newLine;
        }

        if (lineNumber < startFieldLine) {
            return newLine;
        }

        // See https://www.baeldung.com/java-split-string-commas
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < fields.length && i < fieldChanges.length; i++) {
            try {
                fields[i] = fieldChanges[i].fixField(fields[i]);
                if (i > 0) {
                    sb.append(DELIM);
                }
                sb.append(fields[i].trim());
            } catch (SkipThisFieldException e) {
                //
            }
        }

        return sb.toString();

    }

    public void addField(CSVFieldWranglerIFace newField) {
        rawFieldChanges.add(newField);
    }

    private void mapFieldsToPositions(String header) {
        String[] fields = header.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if (fieldChanges == null) {
            fieldChanges = new CSVFieldWranglerIFace[fields.length];
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < rawFieldChanges.size(); j++) {
                if (rawFieldChanges.get(j).isUsableForField(fields[i])) {
                    if (fieldChanges[i] == null) {
                        fieldChanges[i] = rawFieldChanges.get(j);
                    } else {
                        fieldChanges[i].addCSVFieldWranglerIFace(rawFieldChanges.get(j));
                    }

                }
            }
        }

        setFieldChanges(fieldChanges);
    }

    public int getStartFieldLine() {
        return startFieldLine;
    }

    public void setStartFieldLine(int startFieldLine) {
        this.startFieldLine = startFieldLine;
    }

    public void makeChangedCopy() {

        int lineNumber = 0;

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(inputFile));
            FileWriter fw = new FileWriter(outputFile);
            PrintWriter printer = new PrintWriter(fw);


            String line = reader.readLine();

            while (line != null) {
                lineNumber++;
                printer.println(processLine(lineNumber, line));
                line = reader.readLine();

                if (lineNumber % 1000 == 0) {
                    msg("Processing line " + lineNumber);
                }
            }

            reader.close();
            printer.flush();
            printer.close();

        } catch (Exception e) {
            msg(e.getMessage());
            System.exit(7);
        }
    }

    public int getHeaderLine() {
        return headerLine;
    }

    public void setHeaderLine(int headerLine) {
        this.headerLine = headerLine;
    }

}
