package tools.dynamia.modules.saas.api;

/*-
 * #%L
 * DynamiaModules - SaaS API
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

import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.util.Date;
import java.util.List;

public interface AccountServiceAPI {

    AccountStatus getAccountStatus(Long accountId);

    AccountDTO getAccount(Long accountId);

    Long getSystemAccountId();

    Long getCurrentAccountId();

    void updateAccountUsers(Long accountId, long users, long activedUsers);

    List<AccountPaymentDTO> getPayments(Long accountId);

    List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate);

    String getParameterValue(String name);

    String getParameterValue(String name, String defaultValue);

    void setParameter(String name, String value);

    default boolean hasFeature(Long accountId, String featureId) {
        return false;
    }
}
