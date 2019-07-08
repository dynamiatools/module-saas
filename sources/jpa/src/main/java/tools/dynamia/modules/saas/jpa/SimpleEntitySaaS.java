
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

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.modules.saas.api.AccountAware;

/**
 *
 * @author Mario Serrano Leones
 */
@MappedSuperclass
public abstract class SimpleEntitySaaS extends SimpleEntity implements AccountAware {

    @NotNull
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}