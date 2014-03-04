/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.domain;

import com.dynamia.modules.saas.AccountAware;
import com.dynamia.tools.domain.SimpleEntity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mario
 */
@MappedSuperclass
public abstract class SimpleEntitySaaS extends SimpleEntity implements AccountAware {

    @OneToOne
    @NotNull
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
