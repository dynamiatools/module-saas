package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.enums.AccountStatus;

public interface AccountServiceAPI {

    AccountStatus getAccountStatus(Long accountId);

    AccountInfo getAccountInfo(Long accountId);

    Long getSystemAccountId();

    Long getCurrentAccountId();

    void updateAccountUsers(Long accountId, long users, long activedUsers);

}
