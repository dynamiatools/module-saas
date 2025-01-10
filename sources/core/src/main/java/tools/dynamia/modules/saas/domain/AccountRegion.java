package tools.dynamia.modules.saas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import tools.dynamia.domain.jpa.SimpleEntity;

import java.util.Locale;
import java.util.TimeZone;

@Entity
@Table(name = "saas_regions")
public class AccountRegion extends SimpleEntity {

    @NotNull
    private String name;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountProfile defaultProfile;
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountType defaultType;
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountPaymentProvider paymentProvider;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account mainAccount;
    private String currency;
    private String timeZone = TimeZone.getDefault().getDisplayName();
    private String locale = Locale.getDefault().getDisplayName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AccountProfile getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(AccountProfile defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public AccountType getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(AccountType defaultType) {
        this.defaultType = defaultType;
    }

    public AccountPaymentProvider getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(AccountPaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Account getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(Account mainAccount) {
        this.mainAccount = mainAccount;
    }

    @Override
    public String toString() {
        return name;
    }

}
