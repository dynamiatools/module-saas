
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
import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.ModelAndView;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountProfileRestriction;
import tools.dynamia.modules.saas.domain.AccountTypeRestriction;
import tools.dynamia.modules.saas.domain.enums.AccessControl;
import tools.dynamia.navigation.Module;
import tools.dynamia.navigation.NavigationElement;
import tools.dynamia.navigation.Page;
import tools.dynamia.navigation.PageGroup;

/**
 * @author Mario Serrano Leones
 */
@Component
public class AccountNavigationRestriction implements tools.dynamia.navigation.NavigationRestriction, Serializable {

    /**
     * Check if navigation element is a/from module with access permission
     *
     * @param element
     * @return
     */
    @Override
    public Boolean allowAccess(NavigationElement element) {

        Account account = AccountContext.getCurrent().getAccount();
        if (account == null) {
            return false;
        }


        if (account.getProfile() != null) {

            var restrictions = account.getProfile().getRestrictions();

            var rest = restrictions.stream().filter(r -> r.getValue().equalsIgnoreCase(element.getVirtualPath())).findFirst();

            if (rest.isEmpty() && !(element instanceof Module)) {
                return null;
            }

            if (rest.isPresent()) {
                var accessControl = rest.get().getAccessControl();
                switch (accessControl) {
                    case ALLOWED:
                        return true;
                    case DENIED:
                        return false;
                    case DELEGATE:
                        return null;
                }
            }
        }

        if (account.getType().isAdmin()) {
            return true;
        }

        return false;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
