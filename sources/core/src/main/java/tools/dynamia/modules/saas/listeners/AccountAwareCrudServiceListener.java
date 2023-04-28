
/*
 * Copyright (C) 2023 Dynamia Soluciones IT S.A.S - NIT 900302344-1
 * Colombia / South America
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tools.dynamia.modules.saas.listeners;

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
            if (params != null && BeanUtils.isAssignable(paramsType, AccountAware.class)) {
                Account account = AccountContext.getCurrent().getAccount();
                if (account != null) {
                    params.add(ACCOUNT_ID, account.getId());
                }
            }
        }
    }


}
