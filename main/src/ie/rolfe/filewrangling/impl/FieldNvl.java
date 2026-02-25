/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;

public class FieldNvl extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    String nvlValue;

    public FieldNvl() {
        this.nvlValue = "0";
    }

    public FieldNvl(String nvlValue) {
        this.nvlValue = nvlValue;
    }

    @Override
    public String fixField(String field) {

        if (field == null || field.trim().isEmpty()) {
            return processExtraWranglers(nvlValue);
        }
        return processExtraWranglers(field);
    }
}
