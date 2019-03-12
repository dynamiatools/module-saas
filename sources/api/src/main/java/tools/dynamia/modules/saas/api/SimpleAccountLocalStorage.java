package tools.dynamia.modules.saas.api;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.integration.sterotypes.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class SimpleAccountLocalStorage implements AccountLocalStorage {

    private final static long TIMEOUT = 60; //minutes

    @Autowired
    private AccountServiceAPI accountServiceAPI;
    private Map<Long, Map<String, Entry>> storage = new ConcurrentHashMap<>();

    @Override
    public Object get(String key) {
        Entry entry = getEntry(key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Entry getEntry(String key) {
        Entry entry = getCurrentAccountMap().get(key);
        if (isExpired(entry)) {
            remove(entry.getKey());
            entry = null;
        }
        return entry;
    }

    private boolean isExpired(Entry entry) {
        return entry != null && DateTimeUtils.minutesBetween(entry.getDate(), new Date()) >= TIMEOUT;
    }

    @Override
    public void add(String key, Object value) {
        addEntry(new Entry(key, value));
    }

    @Override
    public void addEntry(Entry entry) {
        getCurrentAccountMap().put(entry.getKey(), entry);
    }

    @Override
    public void remove(String key) {
        getCurrentAccountMap().remove(key);
    }

    @Override
    public void clear() {
        getCurrentAccountMap().clear();
    }

    private Map<String, Entry> getCurrentAccountMap() {
        Long accountId = accountServiceAPI.getCurrentAccountId();
        Map<String, Entry> accountMap = storage.get(accountId);
        if (accountMap == null) {
            accountMap = new ConcurrentHashMap<>();
            storage.put(accountId, accountMap);
        }
        return accountMap;
    }
}
