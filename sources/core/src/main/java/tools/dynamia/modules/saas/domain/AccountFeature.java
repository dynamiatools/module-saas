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

import tools.dynamia.domain.jpa.SimpleEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.modules.saas.api.dto.AccountFeatureDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "saas_accounts_features")
public class AccountFeature extends SimpleEntity implements Transferable<AccountFeatureDTO> {

    @ManyToOne
    private Account account;

    @NotEmpty
    private String name;
    private boolean enabled;
    private String providerId;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        boolean old = this.enabled;
        this.enabled = enabled;
        notifyChange("enabled", old, this.enabled);
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return name;
    }
}
