package tools.dynamia.modules.saas.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.util.Date;

public class RemoteAccountServiceAPI extends CrudServiceListenerAdapter<AccountAware> implements AccountServiceAPI {

    @Autowired
    private Environment env;

    private static final String ACCOUNT_ID = "accountId";

    private String serverUrl;
    private String accountUuid;
    private AccountInfo accountInfo;
    private Date lastSync;
    private final int hours = 1;
    private Long defaultID;

    public RemoteAccountServiceAPI(String serverUrl, String accountUuid) {
        this.serverUrl = serverUrl;
        this.accountUuid = accountUuid;
    }

    public RemoteAccountServiceAPI(String serverUrl, String accountUuid, Long defaultID) {
        this.serverUrl = serverUrl;
        this.accountUuid = accountUuid;
        this.defaultID = defaultID;
    }

    @Override
    public AccountStatus getAccountStatus(Long accountId) {
        checkAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getStatus();
        } else {
            return AccountStatus.CANCELED;
        }
    }

    @Override
    public AccountInfo getAccountInfo(Long accountId) {
        checkAccountInfo();

        if (defaultID != null) {
            accountInfo.setId(defaultID);
        }

        return accountInfo;

    }

    @Override
    public Long getSystemAccountId() {

        if (defaultID != null) {
            return defaultID;
        }

        checkAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getId();
        } else {
            return 1L;
        }

    }

    @Override
    public Long getCurrentAccountId() {
        if (defaultID != null) {
            return defaultID;
        }

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

        if (accountInfo != null && accountInfo.getStatus() == AccountStatus.CANCELED) {
            accountInfo = null;
        }

        if (accountInfo == null) {
            syncAccountInfo();
        }


    }

    private void syncAccountInfo() {
        String url = String.format("%s/api/saas/account/%s", serverUrl, accountUuid);

        RestTemplate rest = new RestTemplate();

        String info = getLocalInfo();
        if(info!=null){
            url = url+"?info="+info;
        }

        accountInfo = rest.getForObject(url, AccountInfo.class);
        lastSync = new Date();

        if (defaultID != null) {
            accountInfo.setId(defaultID);
        }


    }


    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity != null && defaultID != null) {
            entity.setAccountId(defaultID);
            return;
        }


        checkAccountInfo();

        if (entity != null && accountInfo != null) {
            if (defaultID != null) {
                entity.setAccountId(defaultID);
            } else {
                entity.setAccountId(accountInfo.getId());
            }
        }
    }


    @Override
    public void beforeUpdate(AccountAware entity) {
        if (entity != null && defaultID != null) {
            entity.setAccountId(defaultID);
            return;
        }


        checkAccountInfo();
        if (entity != null && entity.getAccountId() == null && accountInfo != null) {
            if (defaultID != null) {
                entity.setAccountId(defaultID);
            } else {
                entity.setAccountId(accountInfo.getId());
            }
        }
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        if (!params.containsKey(ACCOUNT_ID) || params.get(ACCOUNT_ID) == null) {
            Class paramsType = params.getType();
            if (paramsType != null) {
                Object obj = BeanUtils.newInstance(paramsType);
                if (obj instanceof AccountAware) {
                    if (defaultID != null) {
                        params.add(ACCOUNT_ID, defaultID);
                    } else if (accountInfo != null) {
                        params.add(ACCOUNT_ID, accountInfo.getId());
                    }

                }
            }
        }
    }

    private String getLocalInfo() {
        StringBuilder info = new StringBuilder();
        info.append("User Dir:").append(System.getProperty("user.dir")).append(",");
        info.append("OS:").append(System.getProperty("os.name")).append(",");
        info.append("Port:").append(env.getProperty("server.port")).append(",");
        info.append("Datasource:").append(env.getProperty("spring.datasource.url"));
        return info.toString();
    }
}
