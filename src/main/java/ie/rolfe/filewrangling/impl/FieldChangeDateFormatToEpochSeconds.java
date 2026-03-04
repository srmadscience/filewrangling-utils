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


public class FieldChangeDateFormatToEpochSeconds extends AbstractFieldWrangler implements CSVFieldWranglerIFace {
    SimpleDateFormat inputFormat;


    public FieldChangeDateFormatToEpochSeconds(String inputFormat, Locale inputLocale) {
        this.inputFormat = new SimpleDateFormat(inputFormat, inputLocale);

    }

    public FieldChangeDateFormatToEpochSeconds(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);

        String inputFormat = (String) wranglerRequest.get("inputFormat");
        Locale inputLocale = wranglerRequest.getLocale("inputLocale");

        this.inputFormat = new SimpleDateFormat(inputFormat, inputLocale);

    }

    @Override
    public String fixField(String field) throws FileWranglingException {

        if (field == null || field.isEmpty()) {
            return "";
        }

        Date date = null;

        try {
             date = inputFormat.parse(field);

        } catch (ParseException e) {
            throw new FileWranglingException(field + " '" + inputFormat , this.getClass().getSimpleName(), e.getMessage());
        }

        return processExtraWranglers(String.valueOf(date.getTime()));
    }

    @Override
    public String toString() {
        return "FieldFixDateFormat{" +
                "inputFormat=" + inputFormat +
                ", fieldNames=" + fieldNames +
                ", theExtraWranglers=" + theExtraWranglers +
                '}';
    }
}



