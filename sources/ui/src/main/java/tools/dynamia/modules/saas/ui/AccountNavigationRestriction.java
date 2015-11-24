/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui;

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
 * @author mario
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
