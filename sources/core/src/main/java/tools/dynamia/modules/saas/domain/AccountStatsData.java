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

import tools.dynamia.commons.BeanUtils;
import tools.dynamia.domain.Descriptor;
import tools.dynamia.domain.OrderBy;
import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.modules.saas.api.AccountStats;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "saas_stats")
@OrderBy("module")
@Descriptor(fields = {"module", "name","quantity", "value",  "description", "creationDate", "lastUpdate"})
public class AccountStatsData extends SimpleEntity {

    @ManyToOne
    private Account account;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    private String name;
    @Column(length = 1000)
    private String value;
    @Column(length = 1000)
    private String description;
    private long quantity;
    private String module;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public void load(AccountStats stats) {
        this.lastUpdate = new Date();
        BeanUtils.setupBean(this, stats);
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
