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
