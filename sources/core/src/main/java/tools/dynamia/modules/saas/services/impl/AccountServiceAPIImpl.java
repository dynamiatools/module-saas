package tools.dynamia.modules.saas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.integration.sterotypes.Service;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;

@Service
public class AccountServiceAPIImpl implements AccountServiceAPI {

	@Autowired
	private CrudService crudService;

	@Autowired
	private LoggingService logger = new SLF4JLoggingService(AccountServiceAPI.class);

	@Override
	public AccountStatus getAccountStatus(Long accountId) {
		try {
			Account account = crudService.find(Account.class, accountId);
			return account.getStatus();
		} catch (Exception e) {
			logger.error("Error getting account status, returning null", e);
			return null;
		}
	}

	@Override
	public AccountInfo getAccountInfo(Long accountId) {
		try {
			Account account = crudService.find(Account.class, accountId);
			return account.getInfo();
		} catch (Exception e) {
			logger.error("Error getting account info, returning null", e);
			return null;
		}
	}

	@Override
	public Long getCurrentAccountId() {
		try {
			return AccountContext.getCurrent().getAccount().getId();
		} catch (Exception e) {
			logger.error("Error getting current account id, returning null", e);
			return null;
		}
	}

}
