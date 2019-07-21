
package tools.dynamia.modules.saas.services;

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

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.dynamia.modules.saas.api.AccountStats;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */
public interface AccountService {

    Account getAccount(String subdomain);

    Account getAccount(HttpServletRequest request);

    Account getAccountByCustomDomain(String domain);

    void setDefaultAccount(Account account);

    Account getDefaultAccount();

    Account init();


    void computeAccountPaymentValue(Account account);

    void initAccount(Account entity);


    void updateStats(Account a);


    void updateAllAccountsStats();


    void updateStats(Account a, List<AccountStats> stats);

    List<Account> findPayableAccounts();

    AccountPayment findLastPayment(Account account);

    boolean isOverdue(Account account);

    boolean shouldBeSuspended(Account account);


    boolean chargeAccount(Account account);

    void computeExpirationDate(Account account);

    void checkPayment(AccountPayment payment);


    void computeBalance(Account account);

    boolean isAboutToExpire(Account account);

    BigDecimal getPaymentValue(Account account);
}
