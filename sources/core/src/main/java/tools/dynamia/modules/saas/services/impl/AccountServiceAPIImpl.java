package tools.dynamia.modules.saas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.integration.sterotypes.Service;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;

import java.util.Date;
import java.util.List;

@Service("accountServiceAPI")
public class AccountServiceAPIImpl implements AccountServiceAPI {

	@Autowired
	private CrudService crudService;

	@Autowired
	private AccountService service;

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
	public AccountDTO getAccount(Long accountId) {
		try {
			Account account = crudService.findSingle(Account.class,
					QueryParameters.with("id", accountId).add("status", QueryConditions.isNotNull()));
			if (account != null) {
				return account.toDTO();
			} else {
				logger.error("No account found with id " + accountId);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error getting account info, returning null", e);
			return null;
		}
	}

	@Override
	public Long getSystemAccountId() {
		try {
			Account account = crudService.findSingle(Account.class, "name", QueryConditions.eq("System"));
			if (account != null) {
				return account.getId();
			} else {
				logger.warn("No system account found");
			}
		} catch (Exception e) {
			logger.error("Error getting system account id, returning null", e);
		}
		return null;
	}

	@Override
	public Long getCurrentAccountId() {
		try {
			Account account = null;
			AccountContext accountContext = AccountContext.getCurrent();
			if (accountContext != null) {
				account = accountContext.getAccount();
			} else {
				logger.warn("No account context found");
			}

			if (account != null) {
				return account.getId();
			} else {
				logger.warn("No current account found");
			}
		} catch (Exception e) {
			logger.error("Error getting current account id, returning null", e);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateAccountUsers(Long accountId, long users, long activedUsers) {
		Account account = crudService.find(Account.class, accountId);
		if (account != null) {
			account.setActivedUsers(activedUsers);
			account.setUsers(users);
			account.getIdentification();
			service.computeAccountPaymentValue(account);
			crudService.update(account);
		}
	}

	@Override
	public List<AccountPaymentDTO> getPayments(Long accountId) {
		return null;
	}

	@Override
	public List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate) {
		return null;
	}
}
