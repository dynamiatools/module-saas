package tools.dynamia.modules.saas.listeners;

/*-
 * #%L
 * DynamiaModules - SaaS Core
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.integration.Containers;
import tools.dynamia.integration.sterotypes.Listener;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.modules.saas.services.impl.AccountServiceAPIImpl;

/**
 * @author Mario Serrano Leones
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
    public void afterUpdate(Account entity) {
        AccountServiceAPI accountServiceAPI = Containers.get().findObject(AccountServiceAPI.class);
        accountServiceAPI.clearCache(entity.getId(), entity.getSubdomain());
        accountServiceAPI.clearCache(entity.getId(), entity.getCustomDomain());
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        if (Account.class.equals(params.getType())) {
            if (params.get("status") == null && params.get("id") == null) {
                params.add("status", QueryConditions.in(AccountStatus.ACTIVE, AccountStatus.NEW, AccountStatus.SUSPENDED));
            }
        }
    }

}
