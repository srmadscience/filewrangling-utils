/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.impl;

import ie.rolfe.filewrangling.exceptions.SkipThisFieldException;
import ie.rolfe.filewrangling.iface.CSVFieldWranglerIFace;

public class FieldSkip extends AbstractFieldWrangler implements CSVFieldWranglerIFace {

    @Override
    public String fixField(String field) {
        throw new SkipThisFieldException(field);
    }
}
