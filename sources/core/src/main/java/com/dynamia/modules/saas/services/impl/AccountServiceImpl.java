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
import com.dynamia.tools.domain.services.impl.JpaCrudService;
import com.dynamia.tools.integration.scheduling.Task;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author mario
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CrudService crudService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void init() {
        if (crudService.count(Account.class) == 0
                && crudService.count(AccountType.class) == 0) {
            createDefaults();
        }
    }

    @Override
    public Account getAccount(String subdomain) {
        return crudService.findSingle(Account.class, "subdomain", subdomain);
    }

    @Override
    public Account getAccountByCustomDomain(String domain) {
        return crudService.findSingle(Account.class, "customDomain", domain);
    }

    @Override
    public Account getAccount(HttpServletRequest request) {
        String host = request.getServerName();
        String subdomain = host.substring(0, host.indexOf("."));
        return getAccount(subdomain);
    }

    @Override
    public void setDefaultAccount(Account account) {
        Account defaultAccount = getDefaultAccount();
        if (defaultAccount != null) {
            defaultAccount.setDefaultAccount(false);
            crudService.update(defaultAccount);
        }

        account.setDefaultAccount(true);
        crudService.update(account);
    }

    @Override
    public Account getDefaultAccount() {
        return crudService.findSingle(Account.class, "defaultAccount", true);
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
        account.setDefaultAccount(true);
        account.setName("System");
        account.setSubdomain("admin");
        account.setEmail("admin@dynamiasoluciones.com");
        account.setStatus(AccountStatus.ACTIVE);
        account.setStatusDate(new Date());
        crudService.save(account);
    }

}
