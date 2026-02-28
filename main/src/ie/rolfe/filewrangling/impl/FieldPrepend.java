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

public class FieldPrepend extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    String thingToPrepend;

    public FieldPrepend(String thingToPrepend) {
        this.thingToPrepend = thingToPrepend;
    }

    public FieldPrepend(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);
        this.thingToPrepend = (String) wranglerRequest.get("thingToPrepend");
    }

    @Override
    public String fixField(String field) {

        if (field == null || field.isEmpty()) {
            return processExtraWranglers(field);
        }

        return processExtraWranglers(thingToPrepend + field);
    }
}
