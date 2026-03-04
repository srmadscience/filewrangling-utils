/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.WranglerRequestException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;
import ie.rolfe.filewrangling.model.WranglerRequest;

public class FieldAppend extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    String thingToAppend;

    public FieldAppend(String thingToAppend) {
        this.thingToAppend = thingToAppend;
    }


    public FieldAppend(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);
        this.thingToAppend = (String) wranglerRequest.get("thingToAppend");
    }

    @Override
    public String fixField(String field) {

        if (field == null || field.isEmpty()) {
            return processExtraWranglers(field);
        }

        return processExtraWranglers(field + thingToAppend );
    }


    @Override
    public String toString() {
        return "FieldPrepend{" +
                "thingToPrepend='" + thingToAppend + '\'' +
                ", fieldNames=" + fieldNames +
                ", theExtraWranglers=" + theExtraWranglers +
                '}';
    }

}
