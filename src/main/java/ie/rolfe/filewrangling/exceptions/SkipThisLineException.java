/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.exceptions;

public class SkipThisLineException extends RuntimeException {
    String columnName;int lineNumber; String line;

    public SkipThisLineException(String columnName, int lineNumber, String line) {
        this.columnName = columnName;
        this.lineNumber = lineNumber;
        this.line = line;
    }
}
