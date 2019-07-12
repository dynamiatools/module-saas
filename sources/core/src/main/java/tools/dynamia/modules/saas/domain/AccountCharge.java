package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "saas_charges")
public class AccountCharge extends BaseEntity {

    @OneToOne
    @NotNull
    private Account account;
    @NotNull
    private BigDecimal value;
    private String comments;

    public AccountCharge() {
    }

    public AccountCharge(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
