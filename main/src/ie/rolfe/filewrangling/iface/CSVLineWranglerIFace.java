/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.iface;

public interface CSVLineWranglerIFace {

    String fixLine(int lineNumber, String line);

    void addCSVLineWranglerIFace(CSVLineWranglerIFace theExtraWrangler);
}
