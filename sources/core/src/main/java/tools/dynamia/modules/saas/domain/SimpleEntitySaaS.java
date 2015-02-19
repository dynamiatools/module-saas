/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.modules.saas.AccountAware;

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
