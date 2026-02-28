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

public class FieldKeep extends AbstractFieldWrangler implements CSVFieldWranglerIFace {


    public FieldKeep() {
        super();

    }

    public FieldKeep(WranglerRequest wranglerRequest) throws WranglerRequestException {
        super(wranglerRequest);
    }

    @Override
    public String fixField(String field) {
        return field;
    }
}
