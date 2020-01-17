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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tools.dynamia.commons.DateTimeUtils;
import tools.dynamia.commons.StringUtils;
import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.Transferable;
import tools.dynamia.domain.contraints.Email;
import tools.dynamia.domain.contraints.NotEmpty;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.modules.entityfile.domain.EntityFile;
import tools.dynamia.modules.saas.AccountStatusCache;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Mario Serrano Leones
 */
@Entity
@Table(name = "saas_accounts")
@BatchSize(size = 10)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account extends SimpleEntity implements Transferable<AccountDTO> {

    /**
     *
     */
    private static final long serialVersionUID = 684169179001325225L;

    @NotNull
    @NotEmpty(message = "ingrese nombre de cuenta")
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "Ingrese numero de identificacion")
    private String identification;
    private String idType;

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
    private AccountStatus status = AccountStatus.NEW;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date statusDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date oldStatusDate;

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
    private int paymentDay = 1;
    private BigDecimal paymentValue;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastPaymentDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastChargeDate;
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String city;
    private String region;
    private String country;
    private String contact;
    private String contactFirstName;
    private String contactLastName;
    @Email(message = "Email valido")
    private String contactEmail;
    private String uuid = StringUtils.randomString();
    private String instanceUuid;
    private Boolean requiredInstanceUuid = Boolean.FALSE;
    private boolean remote;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountStatsData> stats = new ArrayList<>();
    @Size(min = 5, message = "El nombre de usuario debe ser minimo de 5 caracteres")
    private String adminUsername = "admin";
    @Column(length = 2000)
    private String globalMessage;
    private boolean showGlobalMessage;
    private String globalMessageType;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountFeature> features = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal fixedPaymentValue;
    private BigDecimal discount;
    @Temporal(TemporalType.DATE)
    private Date discountExpire;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountAdditionalService> additionalServices = new ArrayList<>();
    @Column(length = 2000)
    private String customerInfo;

    @Temporal(TemporalType.DATE)
    private Date lastInvoiceDate;


    @Transient
    private boolean useTempPaymentDay;
    private AccountStatus oldStatus;

    @OneToOne
    private AccountCategory category;

    @OneToOne
    private AccountReseller reseller;

    private long openTicketsCount;
    private long closedTicketsCount;

    public Account() {
        try {
            Locale current = Locale.getDefault();
            locale = current.toLanguageTag();

            timeZone = ZoneId.systemDefault().getId();

            paymentDay = DateTimeUtils.getCurrentDay();
            if (paymentDay >= 29) {
                paymentDay = 1;
            }
        } catch (Exception e) {
        }

    }

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
        if (contact == null && contactFirstName != null) {
            contact = contactFirstName;
            if (contactLastName != null) {
                contact = contact + " " + contactLastName;
            }
        }
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactFirstName() {
        if (contactFirstName == null && contact != null) {
            contactFirstName = contact;
        }
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
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
        if (paymentDay == 0) {
            paymentDay = 1;
        }

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
            this.oldStatusDate = statusDate;
            this.statusDate = new Date();
            this.oldStatus = this.status;
            AccountStatusCache.statusChanged(this);
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

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public AccountDTO toDTO() {
        System.out.println("Loading Account DTO: " + toString());
        AccountDTO dto = DomainUtils.autoDataTransferObject(this, AccountDTO.class);

        String logoURL = null;
        if (logo != null) {
            logoURL = logo.getStoredEntityFile().getThumbnailUrl(200, 200);
        }
        dto.setLogo(logoURL);
        dto.setType(getType().toDTO());
        try {
            getFeatures().forEach(f -> dto.getFeatures().add(f.toDTO()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dto.setStatus(getStatus());
        dto.setGlobalMessage(getGlobalMessage());
        dto.setShowGlobalMessage(isShowGlobalMessage());
        dto.setGlobalMessageType(getGlobalMessageType());
        dto.setPaymentValue(getPaymentValue());
        return dto;
    }

    public List<AccountStatsData> getStats() {
        return stats;
    }

    public void setStats(List<AccountStatsData> stats) {
        this.stats = stats;
    }

    public AccountStatsData findStats(String name) {
        return stats.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }

    public String getAdminUsername() {
        if (adminUsername == null) {
            adminUsername = "admin";
        }
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

    public List<AccountFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<AccountFeature> features) {
        this.features = features;
    }

    public BigDecimal getBalance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public BigDecimal getFixedPaymentValue() {
        return fixedPaymentValue;
    }

    public void setFixedPaymentValue(BigDecimal fixedPaymentValue) {
        this.fixedPaymentValue = fixedPaymentValue;
    }

    public Date getStartDate() {
        int day = getPaymentDay();
        int month = DateTimeUtils.getMonth(getCreationDate());
        int year = DateTimeUtils.getYear(getCreationDate());
        return DateTimeUtils.createDate(year, month, day);
    }

    public Date getLastChargeDate() {
        return lastChargeDate;
    }

    public void setLastChargeDate(Date lastChargeDate) {
        this.lastChargeDate = lastChargeDate;
    }

    public String getGlobalMessageType() {
        return globalMessageType;
    }

    public void setGlobalMessageType(String globalMessageType) {
        this.globalMessageType = globalMessageType;
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

    public String getIdType() {
        if (idType == null) {
            idType = "ID";
        }
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public List<AccountAdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<AccountAdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public boolean isUseTempPaymentDay() {
        return useTempPaymentDay;
    }

    public void setUseTempPaymentDay(boolean useTempPaymentDay) {
        this.useTempPaymentDay = useTempPaymentDay;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Date getLastInvoiceDate() {
        return lastInvoiceDate;
    }

    public void setLastInvoiceDate(Date lastInvoiceDate) {
        this.lastInvoiceDate = lastInvoiceDate;
    }

    public AccountStatus getOldStatus() {
        if (oldStatus == null) {
            oldStatus = status;
        }
        return oldStatus;
    }

    public void setOldStatus(AccountStatus oldStatus) {
        this.oldStatus = oldStatus;
    }


    public Date getOldStatusDate() {
        if (oldStatusDate == null) {
            oldStatusDate = statusDate;
        }
        return oldStatusDate;
    }

    public void setOldStatusDate(Date oldStatusDate) {
        this.oldStatusDate = oldStatusDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public AccountCategory getCategory() {
        return category;
    }

    public void setCategory(AccountCategory category) {
        this.category = category;
    }

    public AccountReseller getReseller() {
        return reseller;
    }

    public void setReseller(AccountReseller reseller) {
        this.reseller = reseller;
    }

    public long getOpenTicketsCount() {
        return openTicketsCount;
    }

    public void setOpenTicketsCount(long openTicketsCount) {
        this.openTicketsCount = openTicketsCount;
    }

    public long getClosedTicketsCount() {
        return closedTicketsCount;
    }

    public void setClosedTicketsCount(long closedTicketCount) {
        this.closedTicketsCount = closedTicketCount;
    }
}
