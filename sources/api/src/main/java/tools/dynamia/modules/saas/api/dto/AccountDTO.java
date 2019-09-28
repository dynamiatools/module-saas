
package tools.dynamia.modules.saas.api.dto;

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

import tools.dynamia.commons.StringUtils;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mario Serrano Leones
 */

public class AccountDTO implements Serializable {


    private Long id;
    private String name;
    private String identification;
    private String subdomain;
    private String customDomain;
    private String email;
    private AccountTypeDTO type = new AccountTypeDTO();

    private Date expirationDate;
    private AccountStatus status = AccountStatus.NEW;

    private Date statusDate;
    private String statusDescription;

    private Date creationDate = new Date();
    private boolean defaultAccount;
    private String skin;
    private String logo;
    private String locale;
    private String timeZone;
    private String profile;
    private long users;
    private long activedUsers;
    private int paymentDay = 1;
    private BigDecimal paymentValue;
    private Date lastPaymentDate;
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String city;
    private String country;
    private String contact;
    private String uuid = StringUtils.randomString();
    private String instanceUuid;
    private Boolean requiredInstanceUuid = Boolean.FALSE;
    private boolean remote;
    private String adminUsername = "admin";

    private String globalMessage;
    private boolean showGlobalMessage;
    private String globalMessageType;
    private BigDecimal fixedPaymentValue;
    private BigDecimal discount;
    private Date discountExpire;
    private List<AccountFeatureDTO> features = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;


    public Boolean getRequiredInstanceUuid() {
        return requiredInstanceUuid;
    }

    public void setRequiredInstanceUuid(Boolean requiredInstanceUuid) {
        this.requiredInstanceUuid = requiredInstanceUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
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

    public String getLogo() {
        return logo;
    }

    public String getLogoURL() {
        return logo;
    }

    public void setLogo(String logo) {
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

    public AccountTypeDTO getType() {
        return type;
    }

    public void setType(AccountTypeDTO type) {
        if (type != null) {
            this.type = type;
        }
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

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getName(), getEmail());
    }


    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
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

    public List<AccountFeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<AccountFeatureDTO> features) {
        this.features = features;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        if (getType() != null) {
            return getType().getName();
        } else {
            return "";
        }
    }

    public void setMaxUsers(int maxUsers) {
        type.setMaxUsers(maxUsers);
    }

    public int getMaxUsers() {
        return type.getMaxUsers();
    }

    public boolean isAllowAdditionalUsers() {
        return type.isAllowAdditionalUsers();
    }

    public void setAllowAdditionalUsers(boolean allowAdditionalUsers) {
        type.setAllowAdditionalUsers(allowAdditionalUsers);
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getGlobalMessageType() {
        return globalMessageType;
    }

    public void setGlobalMessageType(String globalMessageType) {
        this.globalMessageType = globalMessageType;
    }

    public BigDecimal getFixedPaymentValue() {
        return fixedPaymentValue;
    }

    public void setFixedPaymentValue(BigDecimal fixedPaymentValue) {
        this.fixedPaymentValue = fixedPaymentValue;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getDiscountExpire() {
        return discountExpire;
    }

    public void setDiscountExpire(Date discountExpire) {
        this.discountExpire = discountExpire;
    }
}
