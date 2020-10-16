
package tools.dynamia.modules.saas.domain;

/*-
 * #%L
 * DynamiaModules - SaaS Core
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tools.dynamia.domain.jpa.SimpleEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.modules.saas.api.dto.AccountTypeRestrictionDTO;

/**
 * @author Mario Serrano Leones
 */
@Entity
@Table(name = "saas_restrictions")
public class AccountTypeRestriction extends SimpleEntity implements Transferable<AccountTypeRestrictionDTO> {

    @ManyToOne
    private AccountType type;
    @Column(name = "rest_name")
    private String name;
    @Column(name = "rest_key")
    private String key;
    @Column(name = "rest_values", length = 5000)
    private String values;
    private boolean active;
    private String description;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
