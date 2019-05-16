
package tools.dynamia.modules.saas;

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
import org.springframework.stereotype.Component;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */
@Component("accountContext")
public class AccountContext {

    private final LoggingService logger = new SLF4JLoggingService(AccountContext.class);

    @Autowired
    private List<AccountResolver> resolvers;

    @Autowired
    private AccountService service;


    public static AccountContext getCurrent() {
        return Containers.get().findObject(AccountContext.class);
    }

    public Account getAccount() {
        Account account = null;


        if(HttpUtils.isInWebScope()){
            account = AccountSessionHolder.get().getCurrent();
        }

        if (account == null) {
            for (AccountResolver resolver : resolvers) {
                account = resolver.resolve();
                if (account != null) {
                    break;
                }
            }
        }

        if (account != null && HttpUtils.isInWebScope()) {
            AccountSessionHolder.get().setCurrent(account);
        }
        return account;
    }

    public boolean isAdminAccount() {
        Account account = getAccount();
        if (account != null) {
            return account.getType().getName().equalsIgnoreCase("admin");
        }
        return false;
    }

    @PostConstruct
    private void fixEntities() {
        try {

            Account defaultAccount = service.getDefaultAccount();
            if (defaultAccount == null) {
                defaultAccount = service.init();
            }

            service.fixAccountAwareEntities();
        } catch (Exception e) {
        }

    }

}
