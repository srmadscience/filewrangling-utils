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

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FieldNvl extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    private static final String ZERO_AS_STRING = "0";

    String nvlValue;

    public FieldNvl() {
        this.nvlValue = ZERO_AS_STRING;
    }

    public FieldNvl(String nvlValue) {
        this.nvlValue = nvlValue;
    }

    public FieldNvl(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);

        try {
            this.nvlValue = (String) wranglerRequest.get("nvlValue");
        } catch (WranglerRequestException e) {
            this.nvlValue = ZERO_AS_STRING;
        }
    }

    @Override
    public String fixField(String field) {

        if (field == null || field.trim().isEmpty()) {
            return processExtraWranglers(nvlValue);
        }
        return processExtraWranglers(field);
    }
}
