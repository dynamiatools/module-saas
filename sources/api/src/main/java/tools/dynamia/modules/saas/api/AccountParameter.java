/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import tools.dynamia.domain.jpa.JpaParameter;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.integration.Containers;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Mario Serrano Leones
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountId", "param_name"})
})
public class AccountParameter extends JpaParameter implements AccountAware {


    public static AccountParameter find(String param, Long accountId) {
        return DomainUtils.lookupCrudService().findSingle(AccountParameter.class, QueryParameters.with("accountId", accountId)
                .add("name", QueryConditions.eq(param)));
    }

    private Long accountId;

    public static AccountParameter create(String name, String value, Long accountId) {
        AccountParameter parameter = new AccountParameter();
        parameter.setName(name);
        parameter.setValue(value);
        parameter.setAccountId(accountId);
        return parameter;
    }

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
