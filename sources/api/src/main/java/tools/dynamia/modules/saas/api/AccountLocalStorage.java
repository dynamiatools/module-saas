package tools.dynamia.modules.saas.api;

/*-
 * #%L
 * DynamiaModules - SaaS API
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.integration.Containers;

import java.util.Date;

public interface AccountLocalStorage {

    static AccountLocalStorage load() {
        return Containers.get().findObject(AccountLocalStorage.class);
    }

    Object get(String key);

    Entry getEntry(String key);

    void add(String key, Object value);

    void addEntry(Entry entry);

    void remove(String key);

    void clear();

    class Entry {
        private String key;
        private Object value;
        private long timestamp;
        private String message;
        private Date date;

        public Entry(String key, Object value) {
            this(key, value, null);
        }

        public Entry(String key, Object value, String message) {
            this.key = key;
            this.value = value;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
            this.date = DateTimeUtils.createDate(timestamp);
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public Date getDate() {
            return date;
        }
    }
}
