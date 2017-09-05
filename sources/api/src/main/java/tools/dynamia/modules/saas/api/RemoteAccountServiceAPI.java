package tools.dynamia.modules.saas.api;

import org.springframework.web.client.RestTemplate;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.util.Date;

public class RemoteAccountServiceAPI extends CrudServiceListenerAdapter<AccountAware> implements AccountServiceAPI {

    private String serverUrl;
    private String accountUuid;
    private AccountInfo accountInfo;
    private Date lastSync;
    private final int hours = 2;

    public RemoteAccountServiceAPI(String serverUrl, String accountUuid) {
        this.serverUrl = serverUrl;
        this.accountUuid = accountUuid;
    }

    @Override
    public AccountStatus getAccountStatus(Long accountId) {
        checkAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getStatus();
        } else {
            return AccountStatus.ACTIVE;
        }
    }

    @Override
    public AccountInfo getAccountInfo(Long accountId) {
        checkAccountInfo();
        return accountInfo;

    }

    @Override
    public Long getSystemAccountId() {
        checkAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getId();
        } else {
            return 1L;
        }

    }

    @Override
    public Long getCurrentAccountId() {
        checkAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getId();
        } else {
            return 1L;
        }
    }

    @Override
    public void updateAccountUsers(Long accountId, long users, long activedUsers) {

    }

    private void checkAccountInfo() {
        if (lastSync != null && DateTimeUtils.hoursBetween(lastSync, new Date()) >= hours) {
            accountInfo = null;
        }

        if (accountInfo == null) {
            syncAccountInfo();
        }


    }

    private void syncAccountInfo() {
        String url = String.format("%s/api/saas/account/%s", serverUrl, accountUuid);

        RestTemplate rest = new RestTemplate();

        accountInfo = rest.getForObject(url, AccountInfo.class);
        lastSync = new Date();


    }


    @Override
    public void beforeCreate(AccountAware entity) {
        checkAccountInfo();

        if (entity != null && accountInfo != null) {
            entity.setAccountId(accountInfo.getId());
        }
    }


    @Override
    public void afterUpdate(AccountAware entity) {
        checkAccountInfo();
        if (entity != null && entity.getAccountId() == null && accountInfo != null) {
            entity.setAccountId(accountInfo.getId());
        }
    }

}
