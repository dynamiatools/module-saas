
package tools.dynamia.modules.saas;

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

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.integration.sterotypes.Provider;
import tools.dynamia.modules.entityfile.EntityFileAccountProvider;
import tools.dynamia.modules.saas.api.AccountServiceAPI;

/**
 *
 * @author Mario Serrano Leones
 */
@Provider
public class AccountEntityFileProvider implements EntityFileAccountProvider {

    @Autowired
    private AccountServiceAPI accountServiceAPI;

    @Override
    public Long getAccountId() {
        return accountServiceAPI.getCurrentAccountId();
    }

}
