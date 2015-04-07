package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.enums.AccountStatus;

public interface AccountServiceAPI {

	public AccountStatus getAccountStatus(Long accountId);

	public AccountInfo getAccountInfo(Long accountId);

	public Long getCurrentAccountId();

}
