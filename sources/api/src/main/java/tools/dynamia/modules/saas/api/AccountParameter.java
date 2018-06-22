/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import tools.dynamia.domain.jpa.JpaParameter;
import tools.dynamia.integration.Containers;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Mario Serrano Leones
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accountId", "param_name"})
})
public class AccountParameter extends JpaParameter implements AccountAware {

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
