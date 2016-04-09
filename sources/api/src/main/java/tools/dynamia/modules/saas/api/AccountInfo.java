package tools.dynamia.modules.saas.api;

import java.io.Serializable;
import java.util.Date;

import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

public class AccountInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5463114651455694713L;
    private Long id;
    private String name;
    private String email;
    private AccountStatus status;
    private AccountPeriodicity periodicity;
    private String type;
    private Date creationDate;
    private String subdomain;
    private String domain;
    private String statusDescription;
    private String skin;
    private String logoURL;
    private String locale;
    private String timeZone;
    private int maxUsers;

    public AccountInfo(Long id, String name,
            String email, AccountStatus status,
            AccountPeriodicity periodicity, String type,
            Date creationDate, String subdomain, String domain,
            String statusDescription, String skin, String logoURL, String locale, String timeZone, int maxUsers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.periodicity = periodicity;
        this.type = type;
        this.creationDate = creationDate;
        this.subdomain = subdomain;
        this.domain = domain;
        this.statusDescription = statusDescription;
        this.skin = skin;
        this.logoURL = logoURL;

        this.locale = locale;
        this.timeZone = timeZone;
        this.maxUsers = maxUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getSkin() {
        return skin;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Long getId() {
        return id;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public AccountPeriodicity getPeriodicity() {
        return periodicity;
    }

    public String getType() {
        return type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

}