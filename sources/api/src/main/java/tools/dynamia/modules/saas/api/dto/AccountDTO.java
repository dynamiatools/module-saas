package tools.dynamia.modules.saas.api.dto;

import java.io.Serializable;
import java.util.Date;

import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

public class AccountDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5463114651455694713L;
    private Long id;
    private String name;
    private String identification;
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
    private boolean allowAdditionalUsers;
    private int paymentDay;
    private Date lastPaymentDate;
    private String uuid;
    private boolean remote;
    private String adminUsername;
    private String instanceUuid;
    private Boolean requiredInstanceUuid = Boolean.FALSE;
    private String address;
    private String phoneNumber;
    private String mobileNumber;
    private String city;
    private String country;
    private String region;
    private String globalMessage;
    private boolean showGlobalMessage;



    public AccountDTO(Long id, String name, String identification, String email, AccountStatus status,
                      AccountPeriodicity periodicity, String type, Date creationDate, String subdomain, String domain,
                      String statusDescription, String skin, String logoURL, String locale, String timeZone, int maxUsers,
                      boolean allowAdditionalUsers, int paymentDay, Date lastPaymentDate, String uuid, boolean remote, String adminUsername,
                      String instanceUuid, Boolean requiredInstanceUuid) {
        this.id = id;
        this.name = name;
        this.identification = identification;
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
        this.allowAdditionalUsers = allowAdditionalUsers;
        this.paymentDay = paymentDay;
        this.lastPaymentDate = lastPaymentDate;
        this.uuid = uuid;
        this.remote = remote;
        this.adminUsername = adminUsername;
        this.instanceUuid = instanceUuid;
        this.requiredInstanceUuid = requiredInstanceUuid;
    }

    public AccountDTO() {
    }

    public Boolean getRequiredInstanceUuid() {
        if (requiredInstanceUuid == null) {
            requiredInstanceUuid = false;
        }
        return requiredInstanceUuid;
    }

    public void setRequiredInstanceUuid(Boolean requiredInstanceUuid) {
        this.requiredInstanceUuid = requiredInstanceUuid;
    }

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setPeriodicity(AccountPeriodicity periodicity) {
        this.periodicity = periodicity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setAllowAdditionalUsers(boolean allowAdditionalUsers) {
        this.allowAdditionalUsers = allowAdditionalUsers;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isRemote() {
        return remote;
    }


    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public boolean isAllowAdditionalUsers() {
        return allowAdditionalUsers;
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

    public String getAdminUsername() {
        return adminUsername;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, type);
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGlobalMessage() {
        return globalMessage;
    }

    public void setGlobalMessage(String globalMessage) {
        this.globalMessage = globalMessage;
    }

    public boolean isShowGlobalMessage() {
        return showGlobalMessage;
    }

    public void setShowGlobalMessage(boolean showGlobalMessage) {
        this.showGlobalMessage = showGlobalMessage;
    }
}
