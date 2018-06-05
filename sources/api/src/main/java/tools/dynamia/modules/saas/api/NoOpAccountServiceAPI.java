package tools.dynamia.modules.saas.api;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

public class NoOpAccountServiceAPI extends CrudServiceListenerAdapter<AccountAware> implements AccountServiceAPI {

    private static final Long ACCOUNT_ID = 1L;
    private static final AccountDTO CURRENT_ACCOUNT = new AccountDTO(ACCOUNT_ID, "", "1", "account@api.com",
            AccountStatus.ACTIVE, AccountPeriodicity.UNLIMITED, "NoOp", new Date(), "", null, null, null, null, null,
            "GMT-5", 10000, true, 1, null, "UUID", false, null);

    @Override
    public AccountStatus getAccountStatus(Long accountId) {
        return AccountStatus.ACTIVE;
    }

    @Override
    public AccountDTO getAccount(Long accountId) {
        return CURRENT_ACCOUNT;
    }

    @Override
    public Long getSystemAccountId() {
        return ACCOUNT_ID;
    }

    @Override
    public Long getCurrentAccountId() {
        return ACCOUNT_ID;
    }

    @Override
    public void updateAccountUsers(Long accountId, long users, long activedUsers) {

    }

    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity != null) {
            entity.setAccountId(ACCOUNT_ID);
        }
    }

    @Override
    public void afterUpdate(AccountAware entity) {
        if (entity != null && entity.getAccountId() == null) {
            entity.setAccountId(ACCOUNT_ID);
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
}
