package tools.dynamia.modules.saas.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tools.dynamia.domain.BaseEntity;
import tools.dynamia.domain.contraints.NotEmpty;

@Entity
@Table(name = "saas_profiles")
public class AccountProfile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4559514760400696153L;
	@NotEmpty
	private String name;
	@Column(length = 1000)
	private String description;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<AccountProfileRestriction> restrictions = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AccountProfileRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<AccountProfileRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return getName();
	}

}
