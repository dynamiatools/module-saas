/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.services.impl;

import com.dynamia.modules.saas.domain.Account;
import com.dynamia.modules.saas.domain.AccountType;
import com.dynamia.modules.saas.enums.AccountPeriodicity;
import com.dynamia.modules.saas.enums.AccountStatus;
import com.dynamia.modules.saas.services.AccountService;
import com.dynamia.tools.domain.services.CrudService;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mario
 */

public class AccountServiceImpl implements AccountService {

    @Autowired
    private CrudService crudService;

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void init() {
        if (crudService.count(Account.class) == 0
                && crudService.count(AccountType.class) == 0) {
            createDefaults();
        }
    }

    @Override
    public Account getAccount(String subdomain) {
        return crudService.findSingle(Account.class, "subdomaing", subdomain);
    }

    @Override
    public Account getAccount(HttpServletRequest request) {
        String host = request.getRemoteHost();
        String subdomain = host.substring(0, host.indexOf("."));
        return getAccount(subdomain);
    }

    private void createDefaults() {
        AccountType type = new AccountType();
        type.setActive(true);
        type.setName("admin");
        type.setPublicType(false);
        type.setPeriodicity(AccountPeriodicity.UNLIMITED);
        type.setPrice(BigDecimal.ZERO);
        type.setDescription("Admin account type");
        type = crudService.save(type);
        
        Account account = new Account();
        account.setType(type);
        account.setName("administrator");
        account.setSubdomain("admin");
        account.setEmail("admin@dynamiasoluciones.com");
        account.setStatus(AccountStatus.ACTIVE);
        account.setStatusDate(new Date());
        crudService.save(account);
        

    }

}
