
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

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import tools.dynamia.domain.BaseEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;

/**
 *
 * @author Mario Serrano Leones
 */
@Entity
@Table(name = "saas_payments")
public class AccountPayment extends BaseEntity implements Transferable<AccountPaymentDTO> {

    @OneToOne
    @NotNull(message = "Select account")
    private Account account;
    @OneToOne
    private AccountType type;
    @Column(name = "realValue")
    private BigDecimal value;
    private BigDecimal paymentValue;
    private long users;
    private long activedUsers;
    @Column(length = 2000)
    private String description;
    @Column(length = 2000)
    private String paymentMethodDescription;
    private boolean finished;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        init();
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }

    public long getActivedUsers() {
        return activedUsers;
    }

    public void setActivedUsers(long activedUsers) {
        this.activedUsers = activedUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethodDescription() {
        return paymentMethodDescription;
    }

    public void setPaymentMethodDescription(String paymentMethodDescription) {
        this.paymentMethodDescription = paymentMethodDescription;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    private void init() {
        if (account != null) {
            paymentValue = account.getPaymentValue();
            activedUsers = account.getActivedUsers();
            users = account.getUsers();
            type = account.getType();
            
            notifyChange("paymentValue", BigDecimal.ZERO, paymentValue);
        }
    }

}
