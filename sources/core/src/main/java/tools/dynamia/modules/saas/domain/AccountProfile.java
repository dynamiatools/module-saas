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
