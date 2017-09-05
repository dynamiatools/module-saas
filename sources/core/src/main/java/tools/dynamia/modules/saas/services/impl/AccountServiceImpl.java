/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tools.dynamia.commons.BeanUtils;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.modules.saas.api.AccountAware;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.AccountInitializer;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountType;
import tools.dynamia.modules.saas.services.AccountService;

/**
 * @author mario
 */
@Service
class AccountServiceImpl implements AccountService, ApplicationListener<ContextRefreshedEvent> {

    private LoggingService logger = new SLF4JLoggingService(AccountService.class);

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManagerFactoryInfo entityManagerFactoryInfo;

    @Autowired
    private List<AccountInitializer> initializers;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account init() {
        if (crudService.count(Account.class) == 0 && crudService.count(AccountType.class) == 0) {
            return createDefaults();
        } else {
            return null;
        }
    }

    @Override
    public Account getAccount(String subdomain) {
        return crudService.findSingle(Account.class,
                QueryParameters.with("subdomain", subdomain).add("status", QueryConditions.isNotNull()));
    }

    @Override
    public Account getAccountByCustomDomain(String domain) {
        return crudService.findSingle(Account.class,
                QueryParameters.with("customDomain", domain).add("status", QueryConditions.isNotNull())
                        .add("remote", false));
    }

    @Override
    public Account getAccount(HttpServletRequest request) {
        String host = request.getServerName();
        String subdomain = host.substring(0, host.indexOf("."));
        Account account = getAccount(subdomain);
        if (account == null) {
            account = getAccountByCustomDomain(host);
        }
        return account;
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
        logger.info("Creating default account type");
        AccountType type = new AccountType();
        type.setActive(true);
        type.setName("admin");
        type.setPublicType(false);
        type.setPeriodicity(AccountPeriodicity.UNLIMITED);
        type.setPrice(BigDecimal.ZERO);
        type.setDescription("Admin account type");
        type = crudService.save(type);

        logger.info("Creating default account ");
        Account account = new Account();
        account.setType(type);
        account.setTimeZone(TimeZone.getDefault().getID());
        account.setLocale(Locale.getDefault().toString());
        account.setDefaultAccount(true);
        account.setName("System");
        account.setSubdomain("admin");
        account.setEmail("admin@dynamiasoluciones.com");
        account.setStatus(AccountStatus.ACTIVE);
        account.setStatusDate(new Date());
        account.setIdentification(System.currentTimeMillis() + "");
        account = crudService.save(account);

        logger.info("Default Account created Succcesfull: " + account);
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
                        logger.info("Fixing Account Aware for " + className);
                        String update = "update " + className
                                + " a set a.accountId = :account where (a.accountId is null or a.accountId = 0)";
                        int count = crudService.execute(update, QueryParameters.with("account", account.getId()));
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

    @Override
    public void computeAccountPaymentValue(Account account) {
        AccountType type = account.getType();
        BigDecimal base = type.getPrice();
        BigDecimal userPrice = BigDecimal.ZERO;
        BigDecimal paymentValue = BigDecimal.ZERO;

        if (base == null) {
            base = BigDecimal.ZERO;
        }
        if (type.isAllowAdditionalUsers() && type.getAdditionalUserPrice() != null) {
            userPrice = type.getAdditionalUserPrice();
        }

        if (account.getActivedUsers() > type.getMaxUsers()) {
            long additionalUsers = account.getActivedUsers() - type.getMaxUsers();
            paymentValue = paymentValue.add(userPrice.multiply(new BigDecimal(additionalUsers)));
        }

        paymentValue = paymentValue.add(base);

        account.setPaymentValue(paymentValue);

    }

    @Override
    public void initAccount(Account account) {
        if (!account.isRemote()) {
            AccountInfo accountInfo = account.getInfo();
            initializers.stream().forEach((initializer) -> {
                try {
                    initializer.init(accountInfo);
                } catch (Exception e) {
                    logger.error("Error firing account initializer " + initializer.getClass(), e);
                }
            });
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        logger.info("Context Refreshed.. initializing Accounts");
        init();
    }
}
