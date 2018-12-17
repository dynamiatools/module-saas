package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.util.Date;
import java.util.List;

public interface AccountServiceAPI {

    AccountStatus getAccountStatus(Long accountId);

    AccountDTO getAccount(Long accountId);

    Long getSystemAccountId();

    Long getCurrentAccountId();

    void updateAccountUsers(Long accountId, long users, long activedUsers);

    List<AccountPaymentDTO> getPayments(Long accountId);

    List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate);

    String getParameterValue(String name);

    String getParameterValue(String name, String defaultValue);

    void setParameter(String name, String value);

}
