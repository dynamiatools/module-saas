/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import tools.dynamia.domain.BaseEntity;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "saas_payments")
public class AccountPayment extends BaseEntity {

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
