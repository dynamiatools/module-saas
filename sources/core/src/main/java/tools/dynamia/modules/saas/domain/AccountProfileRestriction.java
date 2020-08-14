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

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.modules.saas.domain.enums.AccessControl;

@Entity
@Table(name = "saas_profiles_restrictions")
public class AccountProfileRestriction extends SimpleEntity {

    @ManyToOne
    private AccountProfile profile;

    @NotEmpty
    private String name;
    private String type;
    @Column(length = 1000)
    private String value;
    private AccessControl accessControl = AccessControl.ALLOWED;

    public AccountProfileRestriction() {
        // TODO Auto-generated constructor stub
    }

    public AccountProfileRestriction(String name, String type, String value) {
        super();
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccountProfile getProfile() {
        return profile;
    }

    public void setProfile(AccountProfile profile) {
        this.profile = profile;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s (%s %s)", name, type,getAccessControl());
    }

    public AccessControl getAccessControl() {
        if (accessControl == null) {
            accessControl = AccessControl.ALLOWED;
        }
        return accessControl;
    }

    public void setAccessControl(AccessControl access) {
        this.accessControl = access;
    }
}
