/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tools.dynamia.domain.query.Parameter;
import tools.dynamia.integration.Containers;

/**
 *
 * @author mario
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accountId", "param_name"})
})
public class AccountParameter extends Parameter implements AccountAware {

    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String identifier() {
        if (accountId != null) {
            return "Account" + accountId;
        } else {
            AccountServiceAPI service = Containers.get().findObject(AccountServiceAPI.class);
            return "Account" + service.getCurrentAccountId();
        }
    }

}
