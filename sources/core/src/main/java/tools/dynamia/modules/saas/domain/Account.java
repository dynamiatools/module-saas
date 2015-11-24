/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.contraints.Email;
import tools.dynamia.domain.contraints.NotEmpty;
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

		AccountInfo info = new AccountInfo(
				getId(),
				name,
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
				getType().getMaxUsers());
		return info;
	}
}
