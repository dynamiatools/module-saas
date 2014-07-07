/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.services;

import com.dynamia.modules.saas.domain.Account;
import javax.servlet.http.HttpServletRequest;

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

}
