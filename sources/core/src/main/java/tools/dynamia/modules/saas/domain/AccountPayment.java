/*
 * Copyright (C) 2021 Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia / South America
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.dynamia.modules.saas.domain;

import tools.dynamia.commons.BigDecimalUtils;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.domain.OrderBy;
import tools.dynamia.domain.Transferable;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.domain.jpa.BaseEntity;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.domain.enums.ResellerComissionStatus;
import tools.dynamia.modules.saas.services.AccountService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author Mario Serrano Leones
 */
@Entity
@Table(name = "saas_payments")
@OrderBy("creationDate")
public class AccountPayment extends BaseEntity implements Transferable<AccountPaymentDTO> {

    @OneToOne
    @NotNull(message = "Select account")
    private Account account;
    @OneToOne
    private AccountType type;
    @NotEmpty(message = "Entrer payment reference")
    private String reference;
    @Column(name = "realValue")
    private BigDecimal value;
    private BigDecimal paymentValue;
    private long users;
    private long activedUsers;
    @Column(length = 2000)
    private String description;
    @Column(length = 2000)
    private String paymentMethodDescription;
    private boolean finished = true;
    @OneToOne
    @NotNull
    private AccountPaymentMethod paymentMethod;
    private BigDecimal resellerComission;
    private double comissionRate;
    private ResellerComissionStatus comissionStatus;

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
            paymentValue = Containers.get().findObject(AccountService.class).getPaymentValue(account);
            activedUsers = account.getActivedUsers();
            users = account.getUsers();
            type = account.getType();
            value = paymentValue;
            if (account.getDiscount() != null && account.getDiscountExpire() != null && account.getDiscountExpire().after(new Date())) {
                description = "Discount: " + DecimalFormat.getCurrencyInstance().format(account.getDiscount()) + " - " + DateTimeUtils.formatDate(account.getDiscountExpire());
            }

            notifyChange("paymentValue", BigDecimal.ZERO, paymentValue);
            notifyChange("value", BigDecimal.ZERO, value);
        }
    }

    public AccountPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(AccountPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getResellerComission() {
        return resellerComission;
    }

    public void setResellerComission(BigDecimal resellerComission) {
        this.resellerComission = resellerComission;
    }

    public double getComissionRate() {
        return comissionRate;
    }

    public void setComissionRate(double comissionRate) {
        this.comissionRate = comissionRate;
    }

    public ResellerComissionStatus getComissionStatus() {
        return comissionStatus;
    }

    public void setComissionStatus(ResellerComissionStatus comissionStatus) {
        this.comissionStatus = comissionStatus;
    }


    public void computeComission() {
        if (paymentMethod != null && paymentMethod.isComissionable() && account != null && account.getReseller() != null) {
            comissionRate = account.getReseller().getComissionRate();
            comissionStatus = ResellerComissionStatus.PENDING;
            if (comissionRate > 0) {
                resellerComission = BigDecimalUtils.computePercent(value, comissionRate, false);
            } else {
                resellerComission = BigDecimal.ZERO;
            }
        }
    }
}
