/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.FileWranglingException;
import ie.rolfe.filewrangling.exceptions.WranglerRequestException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FieldFixDateFormat extends AbstractFieldWrangler implements CSVFieldWranglerIFace {
    SimpleDateFormat inputFormat;
    SimpleDateFormat outputFormat;

    public FieldFixDateFormat(String inputFormat, Locale inputLocale, String outputFormat, Locale outputLocale) {
        this.inputFormat = new SimpleDateFormat(inputFormat, inputLocale);
        this.outputFormat = new SimpleDateFormat(outputFormat, outputLocale);
    }

    public FieldFixDateFormat(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);

        String inputFormat = (String) wranglerRequest.get("inputFormat");
        Locale inputLocale =  (Locale)  wranglerRequest.get("inputLocale");
        String outputFormat = (String) wranglerRequest.get("outputFormat");
        Locale outputLocale =  (Locale)  wranglerRequest.get("outputLocale");

        this.inputFormat = new SimpleDateFormat(inputFormat, inputLocale);
        this.outputFormat = new SimpleDateFormat(outputFormat, outputLocale);

    }

    @Override
    public String fixField(String field) throws FileWranglingException {

        if (field == null || field.isEmpty()) {
            return "";
        }

        String newField = null;

        try {
            Date date = inputFormat.parse(field);
            newField = outputFormat.format(date);

        } catch (ParseException e) {
            throw new FileWranglingException(field + " '" + inputFormat + "' '" + outputFormat + "'", this.getClass().getSimpleName(), e.getMessage());
        }

        return processExtraWranglers(newField);
    }

}

