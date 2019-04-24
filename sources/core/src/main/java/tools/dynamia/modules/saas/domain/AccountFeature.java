package tools.dynamia.modules.saas.domain;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.contraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "saas_accounts_features")
public class AccountFeature extends SimpleEntity {

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
