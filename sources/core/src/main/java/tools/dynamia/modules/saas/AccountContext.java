/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;

import tools.dynamia.domain.services.CrudService;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;

/**
 *
 * @author mario
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
        try {
            account = AccountSessionHolder.get().getCurrent();
        } catch (Exception e) {
            logger.warn("Cannot get AccountSessionHolder: " + e.getMessage());
        }
        if (account == null) {
            for (AccountResolver resolver : resolvers) {
                account = resolver.resolve();
                if (account != null) {
                    break;
                }
            }
            try {
                AccountSessionHolder.get().setCurrent(account);
            } catch (Exception e) {
                logger.warn("Cannot get AccountSessionHolder: " + e.getMessage());
            }
        }

        if (account == null) {
            account = service.getDefaultAccount();
            if (account == null) {
                account = service.init();
            }

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
            service.fixAccountAwareEntities();
        } catch (Exception e) {
        }

    }

}
