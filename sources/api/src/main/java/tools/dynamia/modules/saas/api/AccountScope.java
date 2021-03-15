/*
 * Copyright (C) 2021 Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia / South America
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.dynamia.modules.saas.api;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.api.AccountServiceAPI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountScope implements Scope {

    private Map<Long, Map<String, Object>> accountObjects = new ConcurrentHashMap<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object object = null;
        Long accountId = getAccountId();
        if (accountId != null) {
            object = getAccountObjects(accountId).get(name);

            if (object == null) {
                object = objectFactory.getObject();
                getAccountObjects(accountId).put(name, object);
            }
        }
        return object;
    }


    @Override
    public Object remove(String name) {
        Long accountId = getAccountId();
        if (accountId != null) {
            return getAccountObjects(accountId).remove(name);
        }
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        Long accountId = getAccountId();
        if (accountId != null) {
            return "account" + accountId;
        }
        return null;
    }


    private Map<String, Object> getAccountObjects(Long accountId) {
        Map<String, Object> objectMap = accountObjects.get(accountId);
        if (objectMap == null) {
            objectMap = new ConcurrentHashMap<>();
            accountObjects.put(accountId, objectMap);
        }
        return objectMap;
    }

    private Long getAccountId() {
        AccountServiceAPI accountServiceAPI = Containers.get().findObject(AccountServiceAPI.class);
        if (accountServiceAPI != null) {
            return accountServiceAPI.getCurrentAccountId();
        }
        return null;
    }
}
