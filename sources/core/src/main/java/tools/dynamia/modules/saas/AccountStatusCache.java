package tools.dynamia.modules.saas;

import tools.dynamia.commons.SimpleCache;
import tools.dynamia.modules.saas.domain.Account;

public class AccountStatusCache {

    private static SimpleCache<Long, Boolean> statusCache = new SimpleCache<>();

    public static boolean isStatusChanged(Account account) {
        if (account.getId() == null) {
            return false;
        }
        return Boolean.TRUE.equals(statusCache.remove(account.getId()));
    }

    public static void statusChanged(Account account) {
        if (account.getId() != null) {
            statusCache.add(account.getId(), true);
        }
    }
}
