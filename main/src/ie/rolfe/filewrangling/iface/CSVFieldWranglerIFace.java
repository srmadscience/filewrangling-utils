/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.iface;

import ie.rolfe.filewrangling.exceptions.FileWranglingException;
import ie.rolfe.filewrangling.exceptions.SkipThisFieldException;

public interface CSVFieldWranglerIFace {

    String fixField(String field) throws FileWranglingException, SkipThisFieldException;

    void addCSVFieldWranglerIFace(CSVFieldWranglerIFace theExtraWrangler);
}
