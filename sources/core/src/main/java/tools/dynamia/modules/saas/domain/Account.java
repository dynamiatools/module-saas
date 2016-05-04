/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import tools.dynamia.commons.DateTimeUtils;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.contraints.Email;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.domain.util.ContactInfo;
import tools.dynamia.modules.entityfile.domain.EntityFile;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "saas_accounts")
public class Account extends SimpleEntity {

    @NotNull
    @NotEmpty(message = "ingrese nombre de cuenta")
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "Ingrese numero de identificacion")
    private String identification;

    @NotNull
    @Column(unique = true)
    private String subdomain;
    private String customDomain;
    @NotNull
    @Email(message = "ingreso direccion de correo valida ")
    @NotEmpty(message = "ingrese direccion de correo electronico")
    private String email;
    @OneToOne
    @NotNull
    private AccountType type;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expirationDate;
    private AccountStatus status;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date statusDate;
    private String statusDescription;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
    private boolean defaultAccount;
    private String skin;
    @OneToOne
    private EntityFile logo;
    private String locale;
    private String timeZone;
    @OneToOne
    private AccountProfile profile;
    private long users;
    private long activedUsers;
    @Min(value = 1, message = "Enter valid day")
    @Max(value = 31, message = "Enter valid day")
    private int paymentDay;
    private BigDecimal paymentValue;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastPaymentDate;
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String city;
    private String country;
    private String contact;

    public Account() {
        try {
            Locale current = Locale.getDefault();
            locale = current.toLanguageTag();

            timeZone = ZoneId.systemDefault().getId();

            paymentDay = DateTimeUtils.getCurrentDay();
        } catch (Exception e) {
        }

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdentification() {
        if (identification == null) {
            identification = String.valueOf(getId());
        }
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
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

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }

    public AccountProfile getProfile() {
        return profile;
    }

    public void setProfile(AccountProfile profile) {
        this.profile = profile;
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

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public EntityFile getLogo() {
        return logo;
    }

    public void setLogo(EntityFile logo) {
        this.logo = logo;
    }

    public boolean isDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public String getCustomDomain() {
        return customDomain;
    }

    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        if (status != this.status) {
            setStatusDate(new Date());
        }
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getName(), getEmail());
    }

    public AccountInfo getInfo() {
        String logoURL = null;
        if (logo != null) {
            logoURL = logo.getStoredEntityFile().getThumbnailUrl(200, 200);
        }

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setAddress(address);
        contactInfo.setCity(city);
        contactInfo.setCountry(country);
        contactInfo.setEmail(email);
        contactInfo.setMobileNumber(mobileNumber);
        contactInfo.setPhoneNumber(phoneNumber);

        AccountInfo info = new AccountInfo(
                getId(),
                name,
                identification,
                email,
                status,
                getType().getPeriodicity(),
                getType().getName(),
                creationDate,
                subdomain,
                customDomain,
                statusDescription,
                skin,
                logoURL,
                locale,
                timeZone,
                getType().getMaxUsers(),
                getType().isAllowAdditionalUsers(),
                paymentDay,
                lastPaymentDate,
                contactInfo);
        return info;
    }
}
