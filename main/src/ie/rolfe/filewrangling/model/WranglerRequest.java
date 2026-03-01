/*
 * Copyright (C) 2026 David Rolfe
 *
 * Use of this source code is governed by an MIT
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package ie.rolfe.filewrangling.model;

import ie.rolfe.filewrangling.exceptions.WranglerRequestException;

import java.util.ArrayList;
import java.util.Properties;

import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM;
import static ie.rolfe.filewrangling.BaseFileWrangler.DELIM_SPLIT_REGEX;

public class WranglerRequest {
    public String requestType;
    public Properties props = new Properties();
    public ArrayList<String> fieldNames = new ArrayList<>();

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
        return fieldNames.toArray(new String[fieldNames.size()]);
    }


    public void setFieldNames(String fieldNameList) {

        if (fieldNameList.indexOf(DELIM) == -1) {
            fieldNames.add(fieldNameList);
        } else {
            String[] fields = fieldNameList.split(DELIM_SPLIT_REGEX, -1);
            for (int i = 1; i < fields.length; i++) {
                fieldNames.add(fields[i].trim());
            }
        }

    }

    @Override
    public String toString() {
        return "WranglerRequest{" +
                "requestType='" + requestType + '\'' +
                ", props=" + props +
                ", fieldNames=" + fieldNames +
                '}';
    }
}
