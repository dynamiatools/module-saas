
/*
 * Copyright (C) 2021 Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia / South America
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.dynamia.modules.saas.services;

import tools.dynamia.modules.saas.api.AccountStats;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountPayment;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */
public interface AccountService {

    Account getAccount(String subdomain);

    Account getAccount(HttpServletRequest request);

    Long getAccountId(String subdomain);

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

    Account getAccountById(Long accountId);

    Long getAccountIdByCustomDomain(String domain);

    Account getAccountByName(String name);
}
