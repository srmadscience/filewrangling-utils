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
    CSVFieldWranglerIFace[] fieldChanges = new CSVFieldWranglerIFace[0];

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

    public static void main(String[] args) {

        msg("Parameters:" + Arrays.toString(args));

        if (args.length != 2) {
            msg("Usage: inputfilename outputfilename");
            System.exit(1);
        }

        try {

            File inputFile = new File(args[0]);
            File outputFile = new File(args[1]);

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

            if (outputFile.exists()) {
                msg("Output file already exists");
                System.exit(5);
            }

            if (!outputFile.canWrite()) {
                msg("Output file  not writable");
                System.exit(6);
            }

            BaseFileWrangler theFileWrangler = new BaseFileWrangler(inputFile, outputFile);

            theFileWrangler.makeChangedCopy();

        } catch (Exception e) {
            msg(e.getMessage());
        }

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
            }

            reader.close();
            printer.flush();
            printer.close();

        } catch (Exception e) {
            msg(e.getMessage());
            System.exit(7);
        }
    }


}
