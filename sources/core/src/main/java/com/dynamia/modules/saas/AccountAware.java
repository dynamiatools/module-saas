/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas;

import com.dynamia.modules.saas.domain.Account;

/**
 *
 * @author mario
 */
public interface AccountAware {

    public Account getAccount();

    public void setAccount(Account account);

}
