
package tools.dynamia.modules.saas.listeners;

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

import org.springframework.stereotype.Component;
import tools.dynamia.commons.BeanUtils;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.api.AccountAware;
import tools.dynamia.modules.saas.domain.Account;

/**
 * @author Mario Serrano Leones
 */
@Component
public class AccountAwareCrudServiceListener extends CrudServiceListenerAdapter<AccountAware> {

    private static final String ACCOUNT_ID = "accountId";

    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity.getAccountId() == null) {
            try {
                entity.setAccountId(AccountContext.getCurrent().getAccount().getId());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforeQuery(QueryParameters params) {
        if (!params.containsKey(ACCOUNT_ID) || params.get(ACCOUNT_ID) == null || params.get(ACCOUNT_ID).equals(0L)) {
            Class paramsType = params.getType();
            if (paramsType != null) {
                Object obj = BeanUtils.newInstance(paramsType);
                if (obj instanceof AccountAware) {
                    Account account = AccountContext.getCurrent().getAccount();
                    if (account != null) {
                        params.add(ACCOUNT_ID, account.getId());
                    }
                }
            }
        }
    }


}
