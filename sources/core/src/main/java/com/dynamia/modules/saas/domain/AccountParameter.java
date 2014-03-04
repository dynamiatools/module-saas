/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.domain;

import com.dynamia.modules.saas.AccountAware;
import com.dynamia.tools.domain.query.Parameter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author mario
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"account_id", "param_name"})
})
public class AccountParameter extends Parameter implements AccountAware {

    @OneToOne
    private Account account;

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

}
