/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas;

import com.dynamia.modules.saas.domain.Account;
import com.dynamia.tools.domain.query.QueryParameters;
import com.dynamia.tools.domain.services.CrudService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

/**
 *
 * @author mario
 */
@Component
public class AccountContext {

    @Autowired
    private List<AccountResolver> resolvers;

    @Autowired
    private CrudService crudService;

    public Account getAccount() {
        Account account = null;
        for (AccountResolver resolver : resolvers) {
            account = resolver.resolve();
            if (account != null) {
                break;
            }
        }

        if (account == null) {
            account = getDefaultAccount();
        }

        return account;
    }

    private Account getDefaultAccount() {

        return crudService.findSingle(Account.class, new QueryParameters());

    }

}
