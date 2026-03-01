/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.model;

import ie.rolfe.filewrangling.exceptions.WranglerRequestException;

import java.util.Properties;

public class WranglerRequest {
    public String requestType = null;
    public Properties props = new Properties();
    public String[] fieldNames = null;

    public WranglerRequest(String requestType) {
        this.requestType = requestType;
    }

    public WranglerRequest(String requestType, Properties props) {
        this.requestType = requestType;
        this.props = props;
    }

    public Object get(String key) throws WranglerRequestException {
        Object value = props.get(key);

        if (value == null) {
            throw new WranglerRequestException("key " + key + " not found");
        }

        return value;
    }

    public String getString(String key) throws WranglerRequestException {
        return get(key).toString();
    }

    public int getInt(String key) throws WranglerRequestException {
        Object theValue = get(key);

        if (theValue instanceof Integer) {
            return (Integer) theValue;
        }

        return Integer.parseInt(theValue.toString());

    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public void setFieldName(String fieldName) {
        this.fieldNames = new String[]{fieldName};
    }

}
