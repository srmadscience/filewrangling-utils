/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling;

import com.google.gson.Gson;
import ie.rolfe.filewrangling.exceptions.FileWranglingException;
import ie.rolfe.filewrangling.exceptions.SkipThisFieldException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.iface.CSVLineWranglerIFace;
import ie.rolfe.filewrangling.impl.FieldSkip;
import ie.rolfe.filewrangling.model.FileMapping;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FileWrangler {

    public static final char DELIM = ',';
    public static final String DELIM_SPLIT_REGEX = DELIM + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final String PACKAGE_NAME = "ie.rolfe.filewrangling.impl.";
    private static final int IO_BUFFER_SIZE = 2048;
    File inputFile;
    File outputFile;
    File jsonFile;
    int startFieldLine = 2;
    ArrayList<CSVLineWranglerIFace> lineChanges = new ArrayList<>();
    ArrayList<CSVFieldWranglerIFace> rawFieldChanges = new ArrayList<>();
    CSVFieldWranglerIFace[] fieldChanges = new CSVFieldWranglerIFace[0];

    Gson gson = new Gson();


    int headerLine = 1;

    public FileWrangler(File inputFile, File outputFile, File jsonFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.jsonFile = jsonFile;
    }

    /**
     * Print a formatted message.
     *
     * @param message A message
     */
    public static void msg(String message) {

        SimpleDateFormat sdfDate;
        sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        System.out.println(strDate + ":" + message);

    }

    protected static File[] validateFiles(String[] args) {
        msg("Parameters:" + Arrays.toString(args));

        if (args.length != 3) {
            msg("Usage: inputfilename outputfilename jsonfile");
            System.exit(1);
        }

        File inputFile = null;
        File outputFile = null;
        File jsonFile = null;


        try {

            inputFile = new File(args[0]);
            jsonFile = new File(args[2]);

            File[] readableFiles = {inputFile, jsonFile};

            for (File readableFile : readableFiles) {

                if (!readableFile.exists()) {
                    msg("File " + readableFile.getAbsolutePath() + " does not exist");

                    System.exit(2);
                }

                if (!inputFile.isFile()) {
                    msg("File " + readableFile.getAbsolutePath() + " is not a file");
                    System.exit(3);
                }


                if (!inputFile.canRead()) {
                    msg("File " + readableFile.getAbsolutePath() + " is not readable");
                    System.exit(4);
                }
            }

            outputFile = new File(args[1]);
            if (outputFile.exists()) {
                msg("Output file already exists");
                System.exit(5);
            }


        } catch (Exception e) {
            msg(e.getMessage());
        }

        return new File[]{inputFile, outputFile, jsonFile};
    }

    public static void main(String[] args) {

        File[] files = validateFiles(args);

        FileWrangler theFileWrangler = new FileWrangler(files[0], files[1], files[2]);

        theFileWrangler.parseJsonFile();

        theFileWrangler.makeChangedCopy();

    }

    /**
     * Loads a file into a byte array. Note that this will not work with really big files.
     * Non-existant or zero length files are returned as a zero length array. This routine
     * has been tested with files up to 1MB in size.
     *
     * @param inFile the file you want copied
     * @return byte[] a byte array
     * @since JDBCWizard 4.0.2108
     */
    public static byte[] loadFileIntoByteArray(File inFile) throws FileWranglingException {
        byte[] buff;

        if (inFile == null || (!inFile.exists()) || inFile.length() == 0) {
            // Return zero length array.
            buff = new byte[0];
            return (buff);
        }

        try {
            // Note that a file can be Long.MAX_VALUE but that
            // our byte array can only be Integer.MAX_VALUE in size
            buff = new byte[(int) inFile.length()];
        } catch (Exception e) {
            throw new FileWranglingException("loadFileIntoByteArray: File " + inFile.getAbsolutePath()
                    + " is too big to be turned into a byte array. Size is "
                    + inFile.length());
        }

        try {
            int bytesRead;
            BufferedInputStream source = new BufferedInputStream(Files.newInputStream(inFile.toPath()), IO_BUFFER_SIZE);
            bytesRead = source.read(buff, 0, (int) inFile.length());
            source.close();

            if (bytesRead != inFile.length()) {
                throw new FileWranglingException("loadFileIntoByteArray: File " + inFile.getAbsolutePath()
                        + " could not be turned into a byte array of same size. File size is "
                        + inFile.length() + ". Only " + bytesRead + " bytes were retrieved");
            }

        } catch (IOException error) {
            throw new FileWranglingException("loadFileIntoByteArray: Error while reading: " + error.getMessage());
        }

        return (buff);
    }

    public void parseJsonFile() {


        String jsonFileContents = new String(loadFileIntoByteArray(jsonFile));

        try {
            FileMapping fm = gson.fromJson(jsonFileContents, FileMapping.class);

            for (int i = 0; i < fm.lineMappings.length; i++) {

                msg(i + " Create instance of " + fm.lineMappings[i].requestType + "...");

                Class<?> clazz = Class.forName(PACKAGE_NAME + fm.lineMappings[i].requestType);
                Constructor<?> constructor = clazz.getConstructor(WranglerRequest.class);
                Object instance = constructor.newInstance(fm.lineMappings[i]);
                addLineChange((CSVLineWranglerIFace) instance);
                msg(i + " " + instance);
            }

            for (int i = 0; i < fm.fieldMappings.length; i++) {

                msg(i + " Create instance of " + fm.fieldMappings[i].requestType + "...");

                Class<?> clazz = Class.forName(PACKAGE_NAME + fm.fieldMappings[i].requestType);
                Constructor<?> constructor = clazz.getConstructor(WranglerRequest.class);
                Object instance = constructor.newInstance(fm.fieldMappings[i]);
                addField((CSVFieldWranglerIFace) instance);
                msg(i + " " + instance);
            }

        } catch (Exception e) {
            throw new FileWranglingException(jsonFile + ":" + e.getMessage());
        }

    }

    public void addLineChange(CSVLineWranglerIFace newLineChange) {
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

        for (CSVLineWranglerIFace lineChange : lineChanges) {
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
        String[] fields = header.split(DELIM_SPLIT_REGEX, -1);

        if (fieldChanges == null || fieldChanges.length == 0) {
            fieldChanges = new CSVFieldWranglerIFace[fields.length];
        }

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < rawFieldChanges.size(); j++) {
                if (rawFieldChanges.get(j).isUsedForField(fields[i])) {
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
