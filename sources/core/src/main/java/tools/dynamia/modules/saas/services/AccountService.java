/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.AccountStats;
import tools.dynamia.modules.saas.domain.Account;

import java.util.List;

/**
 *
 * @author mario
 */
public interface AccountService {

    Account getAccount(String subdomain);

    Account getAccount(HttpServletRequest request);

    Account getAccountByCustomDomain(String domain);

    void setDefaultAccount(Account account);

    Account getDefaultAccount();

    Account init();

    void fixAccountAwareEntities();

    void computeAccountPaymentValue(Account account);

	void initAccount(Account entity);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateStats(Account a);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateAllAccountsStats();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateStats(Account a, List<AccountStats> stats);
}
