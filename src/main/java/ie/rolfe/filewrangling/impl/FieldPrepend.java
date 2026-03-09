/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.FileWrangler;
import ie.rolfe.filewrangling.exceptions.WranglerRequestException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;


public class FieldPrepend extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    String thingToPrepend;
    boolean onlyIfMissing = false;

    public FieldPrepend(String thingToPrepend) {
        this.thingToPrepend = thingToPrepend;
    }


    public FieldPrepend(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);
        this.thingToPrepend = wranglerRequest.getString("thingToPrepend");
        if (wranglerRequest.contains("onlyIfMissing")) {
            onlyIfMissing = wranglerRequest.getBool("onlyIfMissing");
        }
    }

    public FieldPrepend(String thingToPrepend, boolean onlyIfMissing) {
        this.thingToPrepend = thingToPrepend;
        this.onlyIfMissing = onlyIfMissing;
    }

    @Override
    public String fixField(String field) {

        if (field == null || field.isEmpty() || field.equals(FileWrangler.QUOTE + FileWrangler.QUOTE)) {
            return processExtraWranglers(field);
        }

        String quoteString = "";
        String outField = new  String(field);

        if (field.length() >= 2
                && field.startsWith(String.valueOf(FileWrangler.QUOTE))
                && field.endsWith(String.valueOf(FileWrangler.QUOTE)))
        {
            quoteString = String.valueOf(FileWrangler.QUOTE);
            outField = outField.substring(1, outField.length()-1);
        }

        if (onlyIfMissing && outField.startsWith(thingToPrepend)) {
            return processExtraWranglers(quoteString  + outField + quoteString);
        }

        return processExtraWranglers(quoteString + thingToPrepend + outField + quoteString);
    }


    @Override
    public String toString() {
        return "FieldPrepend{" +
                "thingToPrepend='" + thingToPrepend + '\'' +
                ", onlyIfMissing=" + onlyIfMissing +
                ", fieldNames=" + fieldNames +
                ", theExtraWranglers=" + theExtraWranglers +
                ", originalWranglerRequest=" + originalWranglerRequest +
                '}';
    }
}
