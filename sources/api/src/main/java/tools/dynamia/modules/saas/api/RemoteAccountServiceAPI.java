package tools.dynamia.modules.saas.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.commons.Messages;
import tools.dynamia.commons.StringUtils;
import tools.dynamia.domain.query.ApplicationParameters;
import tools.dynamia.domain.query.Parameter;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.io.IOUtils;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RemoteAccountServiceAPI extends CrudServiceListenerAdapter<AccountAware> implements AccountServiceAPI {

    @Autowired
    private Environment env;

    @Autowired
    private List<AccountStatsProvider> statsProviders;

    private static final String ACCOUNT_ID = "accountId";

    private String serverUrl;
    private String accountUuid;
    private AccountDTO accountDTO;
    private Date lastSync;
    private final int hours = 1;
    private Long defaultID;
    private int connectionFailCount = 0;
    private static SystemInfo systemInfo = new SystemInfo();

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
        if (accountDTO != null) {
            return accountDTO.getStatus();
        } else {
            return AccountStatus.CANCELED;
        }
    }

    @Override
    public AccountDTO getAccount(Long accountId) {
        checkAccountInfo();

        if (defaultID != null) {
            accountDTO.setId(defaultID);
        }

        return accountDTO;

    }

    @Override
    public Long getSystemAccountId() {

        if (defaultID != null) {
            return defaultID;
        }

        checkAccountInfo();
        if (accountDTO != null) {
            return accountDTO.getId();
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
        if (accountDTO != null) {
            return accountDTO.getId();
        } else {
            return 1L;
        }
    }

    @Override
    public void updateAccountUsers(Long accountId, long users, long activedUsers) {

    }

    private void checkAccountInfo() {
        if (lastSync != null && DateTimeUtils.hoursBetween(lastSync, new Date()) >= hours) {
            accountDTO = null;
        }

        if (accountDTO != null && accountDTO.getStatus() == AccountStatus.CANCELED) {
            accountDTO = null;
        }

        if (accountDTO == null) {
            syncAccountInfo();
        }
    }

    public String getHardwareUUID() {
        String processorID = systemInfo.getHardware().getProcessor().getProcessorID();
        String serialHDD = "";
        HWDiskStore[] diskStores = systemInfo.getHardware().getDiskStores();
        if (diskStores != null && diskStores.length > 1) {
            serialHDD = diskStores[0].getSerial();
        }
        NetworkIF[] networkIFs = systemInfo.getHardware().getNetworkIFs();
        String macaddr = "";
        if (networkIFs != null && networkIFs.length > 1) {
            macaddr = networkIFs[0].getMacaddr();
        }
        return StringUtils.hash(processorID + serialHDD + macaddr, "md5");
    }

    private void syncAccountInfo() {
        String url = getAdminURL();

        RestTemplate rest = new RestTemplate();

        String info = getLocalInfo();
        if (info != null) {
            url = url + "?info=" + info + "&uuid=" + getHardwareUUID();
        }

        try {
            accountDTO = rest.getForObject(url, AccountDTO.class);
            lastSync = new Date();

            if (defaultID != null) {
                accountDTO.setId(defaultID);
            }
            connectionFailCount = 0;
        } catch (RestClientException e) {
            connectionFailCount++;
            accountDTO = tempAccountInfo();

            if (connectionFailCount >= 10) {
                throw new RemoteAccountNotAuthenticatedException(Messages.get(RemoteAccountServiceAPI.class, "remoteConnectionFail"));
            }

            if (connectionFailCount > 5) {
                accountDTO.setStatus(AccountStatus.CANCELED);
                accountDTO.setStatusDescription(Messages.get(RemoteAccountServiceAPI.class, "remoteConnectionFail"));
                System.err.println(accountDTO.getStatusDescription());
            }

            lastSync = new Date();
        }


    }

    private String getAdminURL() {
        return String.format("%s/api/saas/account/%s", serverUrl, accountUuid);
    }

    private AccountDTO tempAccountInfo() {
        AccountDTO temp = new AccountDTO();
        temp.setId(defaultID);
        temp.setName("Temporary Account");
        temp.setCreationDate(new Date());
        temp.setAllowAdditionalUsers(false);
        temp.setRemote(true);
        temp.setIdentification("0000");
        temp.setStatus(AccountStatus.ACTIVE);
        return temp;
    }


    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity != null && defaultID != null) {
            entity.setAccountId(defaultID);
            return;
        }


        checkAccountInfo();

        if (entity != null && accountDTO != null) {
            if (defaultID != null) {
                entity.setAccountId(defaultID);
            } else {
                entity.setAccountId(accountDTO.getId());
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
        if (entity != null && entity.getAccountId() == null && accountDTO != null) {
            if (defaultID != null) {
                entity.setAccountId(defaultID);
            } else {
                entity.setAccountId(accountDTO.getId());
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
                    } else if (accountDTO != null) {
                        params.add(ACCOUNT_ID, accountDTO.getId());
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

    @Scheduled(cron = "0 0 6 * * *")
    public void sendStats() {
        if (accountDTO != null) {
            AccountStatsList stats = new AccountStatsList();

            statsProviders.forEach(provider -> {
                stats.add(provider.getAccountStats(accountDTO.getId()));
            });

            String url = getAdminURL() + "/stats";

            RestTemplate rest = new RestTemplate();
            String response = rest.postForObject(url, stats, String.class);
            System.out.println("Stats Response: " + response);
        }
    }

    @Override
    public List<AccountPaymentDTO> getPayments(Long accountId) {
        return Collections.emptyList();
    }

    @Override
    public List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate) {
        return Collections.emptyList();
    }

    @Override
    public Parameter getParameter(String name) {
        return ApplicationParameters.get().getParameter(AccountParameter.class, name);
    }

    @Override
    public String getParameterValue(String name) {
        return ApplicationParameters.get().getValue(AccountParameter.class, name);
    }

    @Override
    public String getParameterValue(String name, String defaultValue) {
        return ApplicationParameters.get().getValue(AccountParameter.class, name, defaultValue);
    }

    @Override
    public void setParameter(String name, String value) {
        ApplicationParameters.get().setParameter(AccountParameter.class, name, value);
    }
}
