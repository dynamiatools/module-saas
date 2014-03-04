/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.listeners;

import com.dynamia.modules.saas.AccountAware;
import com.dynamia.modules.saas.AccountContext;
import com.dynamia.modules.saas.domain.Account;
import com.dynamia.tools.commons.BeanUtils;
import com.dynamia.tools.domain.query.QueryParameters;
import com.dynamia.tools.domain.util.CrudServiceListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario
 */
@Component
public class AccountAwareCrudServiceListener extends CrudServiceListenerAdapter<AccountAware> {

    @Autowired
    private AccountContext context;

    @Override
    public void beforeCreate(AccountAware entity) {
        Account account = context.getAccount();
        entity.setAccount(account);
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        Class paramsType = params.getType();
        if (paramsType != null) {
            Object obj = BeanUtils.newInstance(paramsType);
            if (obj instanceof AccountAware) {
                Account account = context.getAccount();
                if (account != null) {
                    params.add("account", account);
                }
            }
        }
    }

}
