package tools.dynamia.modules.saas.api;

/*-
 * #%L
 * DynamiaModules - SaaS API
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

import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.io.Serializable;

public class AccountStats implements Serializable {

    private Long accountId;
    private String name;
    private String value;
    private String description;
    private long quantity;
    private String module;

    public AccountStats() {

    }

    public AccountStats(Long accountId, String name, String value) {
        this.accountId = accountId;
        this.name = name;
        this.value = value;
    }

    public AccountStats(Long accountId, String name, long quantity) {
        this.accountId = accountId;
        this.name = name;
        this.quantity = quantity;
        this.value = String.valueOf(quantity);
    }

    public AccountStats(Long accountId, String name, String value, String description, long quantity) {
        this.accountId = accountId;
        this.name = name;
        this.value = value;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public AccountStats module(String module) {
        this.module = module;
        return this;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "AccountStats{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", module='" + module + '\'' +
                '}';
    }
}
