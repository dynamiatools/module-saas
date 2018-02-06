package tools.dynamia.modules.saas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tools.dynamia.domain.SimpleEntity;
import tools.dynamia.domain.contraints.NotEmpty;

@Entity
@Table(name = "saas_profiles_restrictions")
public class AccountProfileRestriction extends SimpleEntity {

	@ManyToOne
	private AccountProfile profile;

	@NotEmpty
	private String name;
	private String type;
	@Column(length = 1000)
	private String value;

	public AccountProfileRestriction() {
		// TODO Auto-generated constructor stub
	}

	public AccountProfileRestriction(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AccountProfile getProfile() {
		return profile;
	}

	public void setProfile(AccountProfile profile) {
		this.profile = profile;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", name, type);
	}

}
