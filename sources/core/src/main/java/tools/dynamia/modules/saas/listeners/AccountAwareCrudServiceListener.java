/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.listeners;

import org.springframework.stereotype.Component;

import tools.dynamia.commons.BeanUtils;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.AccountAware;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.domain.Account;

/**
 *
 * @author mario
 */
@Component
public class AccountAwareCrudServiceListener extends CrudServiceListenerAdapter<AccountAware> {

    
    
    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity.getAccount() == null) {
            entity.setAccount(AccountContext.getCurrent().getAccount());
        }
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        if (!params.containsKey("account")) {
            Class paramsType = params.getType();
            if (paramsType != null) {
                Object obj = BeanUtils.newInstance(paramsType);
                if (obj instanceof AccountAware) {
                    Account account = AccountContext.getCurrent().getAccount();
                    if (account != null) {
                        params.add("account", account);
                    }
                }
            }
        }
    }

}