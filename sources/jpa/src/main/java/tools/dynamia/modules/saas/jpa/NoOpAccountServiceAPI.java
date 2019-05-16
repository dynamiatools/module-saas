package tools.dynamia.modules.saas.jpa;

/*-
 * #%L
 * DynamiaModules - SaaS JPA
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

import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.modules.saas.api.AccountAware;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NoOpAccountServiceAPI extends CrudServiceListenerAdapter<AccountAware> implements AccountServiceAPI {

    private static final Long ACCOUNT_ID = 1L;
    private static final AccountDTO CURRENT_ACCOUNT = new AccountDTO(ACCOUNT_ID, "", "1", "account@api.com",
            AccountStatus.ACTIVE, AccountPeriodicity.UNLIMITED, "NoOp", new Date(), "", null, null, null, null, null,
            "GMT-5", 10000, true, 1, null, "UUID", false, "admin", "", false);

    @Override
    public AccountStatus getAccountStatus(Long accountId) {
        return AccountStatus.ACTIVE;
    }

    @Override
    public AccountDTO getAccount(Long accountId) {
        return CURRENT_ACCOUNT;
    }

    @Override
    public Long getSystemAccountId() {
        return ACCOUNT_ID;
    }

    @Override
    public Long getCurrentAccountId() {
        return ACCOUNT_ID;
    }

    @Override
    public void updateAccountUsers(Long accountId, long users, long activedUsers) {

    }

    @Override
    public void beforeCreate(AccountAware entity) {
        if (entity != null) {
            entity.setAccountId(ACCOUNT_ID);
        }
    }

    @Override
    public void afterUpdate(AccountAware entity) {
        if (entity != null && entity.getAccountId() == null) {
            entity.setAccountId(ACCOUNT_ID);
        }
    }

    @Override
    public List<AccountPaymentDTO> getPayments(Long accountId) {
        return Collections.emptyList();
    }

    @Override
    public List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate) {
        return Collections.emptyList();
    }


    @Override
    public String getParameterValue(String name) {
        return null;
    }

    @Override
    public String getParameterValue(String name, String defaultValue) {
        return defaultValue;
    }

    @Override
    public void setParameter(String name, String value) {
//do nothin
    }
}
