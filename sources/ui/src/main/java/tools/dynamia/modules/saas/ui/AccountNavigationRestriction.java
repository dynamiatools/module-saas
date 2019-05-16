
package tools.dynamia.modules.saas.ui;

/*-
 * #%L
 * DynamiaModules - SaaS UI
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

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountProfileRestriction;
import tools.dynamia.navigation.Module;
import tools.dynamia.navigation.NavigationElement;

/**
 *
 * @author Mario Serrano Leones
 */
@Component
@Scope("session")
public class AccountNavigationRestriction implements tools.dynamia.navigation.NavigationRestriction, Serializable {

	@Override
	public Boolean allowAccess(NavigationElement element) {
		if (element instanceof Module) {
			Account account = AccountContext.getCurrent().getAccount();
			if (account.getProfile() != null) {
				for (AccountProfileRestriction permiso : account.getProfile().getRestrictions()) {
					if (permiso.getValue().equals(element.getId())) {
						return true;
					}
				}
				return false;
			}
		}
		return null;

	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}
}
