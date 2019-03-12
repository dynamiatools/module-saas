package tools.dynamia.modules.saas.api;

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
