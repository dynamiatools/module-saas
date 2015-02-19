/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tools.dynamia.domain.query.Parameter;
import tools.dynamia.modules.saas.AccountAware;

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
