/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tools.dynamia.commons.BeanUtils;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.modules.saas.AccountAware;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountType;
import tools.dynamia.modules.saas.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.enums.AccountStatus;
import tools.dynamia.modules.saas.services.AccountService;

/**
 *
 * @author mario
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManagerFactoryInfo entityManagerFactoryInfo;

    private LoggingService logger = new SLF4JLoggingService(AccountService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account init() {
        if (crudService.count(Account.class) == 0
                && crudService.count(AccountType.class) == 0) {
            return createDefaults();
        } else {
            return null;
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

    private Account createDefaults() {

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
        account = crudService.save(account);
        return account;
    }

    @Transactional
    @Override
    public void fixAccountAwareEntities() {
        logger.info("Fixing AccountAware entities");
        Account account = crudService.findSingle(Account.class, new QueryParameters());

        if (account != null) {
            logger.info("Setting account " + account + " to all AccountAware entities with null account ");
            List<String> entityClasses = entityManagerFactoryInfo.getPersistenceUnitInfo().getManagedClassNames();
            for (String className : entityClasses) {
                try {
                    Object entity = BeanUtils.newInstance(className);
                    if (entity instanceof AccountAware) {
                        logger.info("Fixing " + className);
                        String update = "update " + className + " a set a.account = :account where a.account is null";
                        int count = crudService.execute(update, QueryParameters.with("account", account));
                        if (count > 0) {
                            logger.info(" " + count + " " + className + " entities fixed with account " + account);
                        } else {
                            logger.info(" Nothing to fix");
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

}
