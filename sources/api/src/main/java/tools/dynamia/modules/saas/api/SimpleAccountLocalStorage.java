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
