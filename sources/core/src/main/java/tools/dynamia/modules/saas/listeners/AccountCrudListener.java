/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.listeners;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.integration.sterotypes.Listener;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.AccountInitializer;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;

/**
 *
 * @author mario
 */
@Listener
public class AccountCrudListener extends CrudServiceListenerAdapter<Account> {

    @Autowired
    private List<AccountInitializer> initializers;

    private final LoggingService LOGGER = new SLF4JLoggingService(AccountCrudListener.class);

    @Override
    public void afterCreate(Account entity) {
        fireInitializers(entity);
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        if (Account.class.equals(params.getType())) {
            if (params.get("status") == null && params.get("id") == null) {
                params.add("status", QueryConditions.in(AccountStatus.ACTIVE, AccountStatus.NEW));
            }
        }
    }

    private void fireInitializers(Account entity) {
        AccountInfo accountInfo = entity.getInfo();
        initializers.stream().forEach((initializer) -> {
            try {
                initializer.init(accountInfo);
            } catch (Exception e) {
                LOGGER.error("Error firing account initializer " + initializer.getClass(), e);
            }
        });
    }

}
