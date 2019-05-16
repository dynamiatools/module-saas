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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.navigation.NavigationElement;
import tools.dynamia.navigation.NavigationRestriction;

@Component
public class HelpSystemNavigationRestriction implements NavigationRestriction {

    @Autowired
    private AccountServiceAPI accountServiceAPI;

    @Override
    public Boolean allowAccess(NavigationElement element) {
        if (element.getVirtualPath().startsWith("system/help")) {
            Long accountId = accountServiceAPI.getCurrentAccountId();
            if (accountId != null) {
                AccountDTO accountDTO = accountServiceAPI.getAccount(accountId);
                if (accountDTO != null && !accountDTO.getType().equals("admin")) {
                    return false;
                }
            }

        }
        return null;
    }

    @Override
    public int getOrder() {

        return 0;
    }

}
