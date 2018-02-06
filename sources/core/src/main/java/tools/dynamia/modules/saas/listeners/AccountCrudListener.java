/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.integration.sterotypes.Listener;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;

/**
 *
 * @author mario
 */
@Listener
public class AccountCrudListener extends CrudServiceListenerAdapter<Account> {

	@Autowired
	private AccountService service;

	@Override
	public void afterCreate(Account entity) {
		service.initAccount(entity);
	}

	@Override
	public void beforeQuery(QueryParameters params) {
		if (Account.class.equals(params.getType())) {
			if (params.get("status") == null && params.get("id") == null) {
				params.add("status", QueryConditions.in(AccountStatus.ACTIVE, AccountStatus.NEW));
			}
		}
	}

}
