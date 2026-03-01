/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.model;

import java.util.Arrays;

public class FileMapping {
    public WranglerRequest[] lineMappings = new WranglerRequest[0];
    public WranglerRequest[] fieldMappings = new WranglerRequest[0];

    @Override
    public String toString() {
        return "FileMapping{" +
                "lineMappings=" + Arrays.toString(lineMappings) +
                ", fieldMappings=" + Arrays.toString(fieldMappings) +
                '}';
    }
}
