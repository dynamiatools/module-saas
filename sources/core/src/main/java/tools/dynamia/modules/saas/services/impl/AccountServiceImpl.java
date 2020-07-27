package tools.dynamia.modules.saas.services.impl;

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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.domain.query.ApplicationParameters;
import tools.dynamia.domain.query.QueryCondition;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.domain.util.QueryBuilder;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.api.AccountInitializer;
import tools.dynamia.modules.saas.api.AccountStats;
import tools.dynamia.modules.saas.api.AccountStatsProvider;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.*;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mario Serrano Leones
 */
@Service
public class AccountServiceImpl implements AccountService, ApplicationListener<ContextRefreshedEvent> {

    private LoggingService logger = new SLF4JLoggingService(AccountService.class);

    @Autowired
    private CrudService crudService;

    @Autowired
    private EntityManagerFactoryInfo entityManagerFactoryInfo;


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
                QueryParameters.with("subdomain", QueryConditions.eq(subdomain))
                        .add("status", QueryConditions.isNotNull())
                        .add("remote", false));
    }

    @Override
    public Account getAccountByCustomDomain(String domain) {
        return crudService.findSingle(Account.class,
                QueryParameters.with("customDomain", QueryConditions.eq(domain)).add("status", QueryConditions.isNotNull())
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
        account.setAutoInit(false);
        account = crudService.save(account);

        logger.info("Default Account created Succcesfull: " + account);
        return account;
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
        if (!account.isRemote() && account.isAutoInit()) {
            AccountDTO accountDTO = account.toDTO();
            Collection<AccountInitializer> initializers = Containers.get().findObjects(AccountInitializer.class);
            initializers.stream().sorted(Comparator.comparingInt(AccountInitializer::getPriority)).forEach(initializer -> {
                try {
                    initializer.init(accountDTO);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStats(Account a) {
        final Account account = crudService.reload(a);
        account.getStats().size();
        Containers.get().findObjects(AccountStatsProvider.class).forEach(provider -> {
            List<AccountStats> stats = provider.getAccountStats(account.getId());
            if (stats != null && !stats.isEmpty()) {
                syncAccountStats(account, stats);
            }
        });
        crudService.save(account);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAllAccountsStats() {
        List<Account> accounts = crudService.find(Account.class, QueryParameters.with("remote", false).add("status", AccountStatus.ACTIVE));
        accounts.forEach(this::updateStats);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStats(Account a, List<AccountStats> stats) {
        final Account account = crudService.reload(a);
        syncAccountStats(account, stats);
        account.save();
    }

    private void syncAccountStats(Account account, List<AccountStats> stats) {
        stats.forEach(s -> {
            AccountStatsData statsData = account.findStats(s.getName());
            if (statsData == null) {
                statsData = new AccountStatsData();
                statsData.setAccount(account);
                account.getStats().add(statsData);
            }
            statsData.load(s);
        });
    }

    @Override
    public List<Account> findPayableAccounts() {
        return crudService.find(Account.class, QueryParameters.with("status", QueryConditions.in(AccountStatus.ACTIVE, AccountStatus.SUSPENDED))
                .add("type.price", QueryConditions.gt(BigDecimal.ZERO))
                .add("type.paymentRequired", true));
    }

    @Override
    public AccountPayment findLastPayment(Account account) {
        return crudService.findSingle(AccountPayment.class, QueryParameters.with("account", account)
                .add("finished", true)
                .orderBy("creationDate", false));
    }


    @Override
    public boolean isOverdue(Account account) {
        if (account.getBalance().longValue() < 0) {
            return true;
        } else if (account.getBalance().longValue() == 0) {
            return false;
        }
        return account.getExpirationDate() != null && account.getExpirationDate().before(new Date());
    }

    @Override
    public boolean shouldBeSuspended(Account account) {
        if (isOverdue(account)) {
            int allowedOverdueDays = account.getType().getAllowedOverdueDays();
            return DateTimeUtils.daysBetween(account.getLastChargeDate(), new Date()) >= allowedOverdueDays;

        }
        return false;
    }

    @Override
    @Transactional
    public boolean chargeAccount(Account account) {
        long payDay = account.getPaymentDay();
        if (account.isUseTempPaymentDay()) {
            payDay = DateTimeUtils.getCurrentDay();
        }

        if (account.getStatus() == AccountStatus.ACTIVE && account.getType().getPrice().longValue() > 0 && payDay == DateTimeUtils.getCurrentDay() && account.getBalance().longValue() >= 0) {

            int days = 20;
            if (account.getType().getPeriodicity() == AccountPeriodicity.YEARLY) {
                days = 359;
            }


            if (account.getLastChargeDate() == null || DateTimeUtils.daysBetween(account.getLastChargeDate(), new Date()) > days) {
                Date chargeDate = DateTimeUtils.createDate(account.getPaymentDay());
                AccountCharge charge = new AccountCharge(account);
                charge.setCreationDate(chargeDate);
                charge.setValue(getPaymentValue(account));
                account.setBalance(account.getBalance().subtract(charge.getValue()));
                account.setLastChargeDate(charge.getCreationDate());
                charge.save();
                return true;
            }
        }
        return false;
    }

    @Override
    public void computeExpirationDate(Account account) {
        AccountPeriodicity periodicity = account.getType().getPeriodicity();
        if (periodicity != AccountPeriodicity.UNLIMITED) {
            int incr = 1;
            Date startDate = account.getStartDate();
            if (account.getLastChargeDate() != null) {
                startDate = account.getLastChargeDate();
            }

            switch (periodicity) {
                case MONTHLY:
                    account.setExpirationDate(DateTimeUtils.addMonths(startDate, incr));
                    int expDay = DateTimeUtils.getDay(account.getExpirationDate());
                    if (expDay < account.getPaymentDay()) {
                        account.setExpirationDate(DateTimeUtils.addDays(account.getExpirationDate(), account.getPaymentDay() - expDay));
                    }
                    break;
                case YEARLY:
                    account.setExpirationDate(DateTimeUtils.addYears(startDate, incr));
                    break;
            }
        } else {
            account.setExpirationDate(null);
        }
    }

    @Override
    @Transactional
    public void checkPayment(AccountPayment payment) {
        Account account = crudService.reload(payment.getAccount());
        account.setLastPaymentDate(payment.getCreationDate());
        if (payment.isFinished()) {
            account.setBalance(account.getBalance().add(payment.getValue()));
            if (account.getBalance().longValue() >= 0) {
                account.setStatus(AccountStatus.ACTIVE);
                account.setGlobalMessage(null);
                account.setShowGlobalMessage(false);
            }
            account.save();
        }
    }

    @Override
    @Transactional
    public void computeBalance(Account account) {
        QueryBuilder queryCharges = QueryBuilder.select("sum(c.value)").from(AccountCharge.class, "c").where("c.account", QueryConditions.eq(account));
        QueryBuilder queryPayments = QueryBuilder.select("sum(p.value)").from(AccountPayment.class, "p").where("p.account", QueryConditions.eq(account)).and("p.finished = true");

        BigDecimal charges = crudService.executeProjection(BigDecimal.class, queryCharges.toString(), queryCharges.getQueryParameters());
        BigDecimal payments = crudService.executeProjection(BigDecimal.class, queryPayments.toString(), queryPayments.getQueryParameters());


        BigDecimal balance = BigDecimal.ZERO.add(payments).subtract(charges);
        account.setBalance(balance);
    }


    @Override
    public boolean isAboutToExpire(Account account) {

        if (account.getLastPaymentDate() != null && account.getExpirationDate() != null) {
            if (account.getLastPaymentDate().after(account.getExpirationDate())) {
                return false;
            } else if (account.getExpirationDate().after(account.getLastChargeDate())) {
                final int MAX_DAYS = Integer.parseInt(ApplicationParameters.get().getValue("ACCOUNTS_ABOUT_TO_EXPIRE_MAX_DAYS", "4"));
                long daysBetween = DateTimeUtils.daysBetween(new Date(), account.getExpirationDate());
                return daysBetween < MAX_DAYS;
            } else if (DateTimeUtils.daysBetween(account.getLastPaymentDate(), account.getExpirationDate()) < 30 && account.getBalance().longValue() == 0) {
                return false;
            }
        }
        return false;
    }

    @Override
    public BigDecimal getPaymentValue(Account account) {
        account = crudService.reload(account);
        BigDecimal value = account.getPaymentValue();
        if (account.getFixedPaymentValue() != null) {
            value = account.getFixedPaymentValue();
        }

        if (account.getDiscount() != null && (account.getDiscountExpire() == null || account.getDiscountExpire().after(new Date()))) {
            value = value.subtract(account.getDiscount());
        }

        if (value == null) {
            value = BigDecimal.ZERO;
        }

        BigDecimal additionalServicesTotal = account.getAdditionalServices().stream()
                .map(AccountAdditionalService::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        value = value.add(additionalServicesTotal);

        return value;
    }
}
