
package tools.dynamia.modules.saas.jpa;

/*-
 * #%L
 * DynamiaModules - SaaS JPA
 * %%
 * Copyright (C) 2016 - 2019 Dynamia Soluciones IT SAS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import tools.dynamia.domain.jpa.JpaParameter;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.api.AccountAware;
import tools.dynamia.modules.saas.api.AccountServiceAPI;

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
