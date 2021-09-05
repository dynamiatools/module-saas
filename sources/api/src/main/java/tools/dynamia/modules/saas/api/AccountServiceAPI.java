/*
 * Copyright (C) 2021 Dynamia Soluciones IT S.A.S - NIT 900302344-1
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

package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.dto.AccountStatusDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountServiceAPI {

    AccountStatus getAccountStatus(Long accountId);

    AccountDTO getAccount(Long accountId);

    Long getSystemAccountId();

    Long getCurrentAccountId();

    AccountDTO getCurrentAccount();

    void updateAccountUsers(Long accountId, long users, long activedUsers);

    List<AccountPaymentDTO> getPayments(Long accountId);

    List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate);

    String getParameterValue(String name);

    String getParameterValue(String name, String defaultValue);

    void setParameter(String name, String value);

    default boolean hasFeature(Long accountId, String featureId) {
        return false;
    }

    default boolean isPrintingEnabled(Long accountId) {
        return true;
    }

    List<Long> findAccountsIdByFeature(String featureId);

    void log(Long accountId, String message);

    default AccountStatusDTO getAccountStatusDetails(Long accountId) {
        AccountDTO dto = getAccount(accountId);
        return new AccountStatusDTO(dto.getId(), dto.getName(), dto.getStatus(), dto.getStatusDate(),
                dto.getStatusDescription(), dto.getGlobalMessage(), dto.isShowGlobalMessage(), dto.getGlobalMessageType(), BigDecimal.ZERO);
    }

    default Long getParentAccountId(Long accountId) {
        return null;
    }

    Long getAccountIdByDomain(String domain);

    default void clearCache() {
        //do nothing
    }

    default void clearCache(Long accountId, String accountDomain) {
        //do nothing
    }

    default List<Long> findAccountsId(Map<String, Object> params) {
        return Collections.emptyList();
    }

    default List<Long> findActivePaymentRequiredAccounts() {
        return findAccountsId(Map.of("status", AccountStatus.ACTIVE, "type.paymentRequired", true));
    }
}
