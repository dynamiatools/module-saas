/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.services;

import javax.servlet.http.HttpServletRequest;

import tools.dynamia.modules.saas.domain.Account;

/**
 *
 * @author mario
 */
public interface AccountService {

    Account getAccount(String subdomain);

    Account getAccount(HttpServletRequest request);

    Account getAccountByCustomDomain(String domain);

    public void setDefaultAccount(Account account);

    Account getDefaultAccount();

    Account init();

    void fixAccountAwareEntities();

    void computeAccountPaymentValue(Account account);

}
