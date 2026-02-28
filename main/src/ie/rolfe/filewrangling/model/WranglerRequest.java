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
}
