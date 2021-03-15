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

package tools.dynamia.modules.saas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.dynamia.commons.SimpleCache;
import tools.dynamia.domain.query.ApplicationParameters;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.AbstractService;
import tools.dynamia.domain.util.QueryBuilder;
import tools.dynamia.integration.sterotypes.Service;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.AccountSessionHolder;
import tools.dynamia.modules.saas.api.AccountServiceAPI;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.dto.AccountLogDTO;
import tools.dynamia.modules.saas.api.dto.AccountPaymentDTO;
import tools.dynamia.modules.saas.api.dto.AccountStatusDTO;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountFeature;
import tools.dynamia.modules.saas.domain.AccountLog;
import tools.dynamia.modules.saas.jpa.AccountParameter;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("accountServiceAPI")
public class AccountServiceAPIImpl extends AbstractService implements AccountServiceAPI {


    @Autowired
    private AccountService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AccountContext accountContext;

    private final SimpleCache<Long, AccountDTO> accountCache = new SimpleCache<>();
    private final SimpleCache<String, Long> domainCache = new SimpleCache<>();

    @Autowired
    public AccountServiceAPIImpl(AccountService service) {
        this.service = service;
    }


    @Override
    public AccountStatus getAccountStatus(Long accountId) {
        try {
            AccountStatusDTO dto = getAccountStatusDetails(accountId);
            return dto.getStatus();
        } catch (Exception e) {
            log("Error getting account status, returning null", e);
            return null;
        }
    }

    @Override
    @Transactional
    public AccountDTO getAccount(Long accountId) {
        AccountDTO dto = null;

        try {
            dto = accountCache.get(accountId);
            if (dto == null) {
                Account account = crudService().findSingle(Account.class,
                        QueryParameters.with("id", accountId).add("status", QueryConditions.isNotNull()));
                if (account != null) {
                    dto = account.toDTO();
                    accountCache.add(accountId, dto);
                } else {
                    log("No account found with id " + accountId);
                }
            }
        } catch (Exception e) {
            log("Error getting account info, returning null", e);
        }
        return dto;
    }

    @Override
    public Long getSystemAccountId() {
        try {
            Account account = crudService().findSingle(Account.class, "name", QueryConditions.eq("System"));
            if (account != null) {
                return account.getId();
            } else {
                log("No system account found");
            }
        } catch (Exception e) {
            log("Error getting system account id, returning null", e);
        }
        return null;
    }

    @Override
    public Long getCurrentAccountId() {
        Long id = null;

        try {
            if (HttpUtils.isInWebScope() && AccountSessionHolder.get().getCurrent() != null) {
                id = AccountSessionHolder.get().getCurrent().getId();
            }
        } catch (Exception e) {
            //ignore
        }


        try {
            if (id == null) {
                id = accountContext.getAccount().getId();
            }
        } catch (Exception e) {
            id = null;
        }

        return id;
    }

    @Override
    public AccountDTO getCurrentAccount() {
        try {
            return accountContext.toDTO();
        } catch (Exception e) {
            log("Error loading current account", e);
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAccountUsers(Long accountId, long users, long activedUsers) {
        Account account = crudService().find(Account.class, accountId);
        if (account != null) {
            account.setActivedUsers(activedUsers);
            account.setUsers(users);
            account.getIdentification();
            service.computeAccountPaymentValue(account);
            crudService().update(account);
        }
    }

    @Override
    public List<AccountPaymentDTO> getPayments(Long accountId) {
        return null;
    }

    @Override
    public List<AccountLogDTO> getLogs(Long accountId, Date startDate, Date endDate) {
        return null;
    }


    @Override
    public String getParameterValue(String name) {
        return ApplicationParameters.get().getValue(AccountParameter.class, name);
    }

    @Override
    public String getParameterValue(String name, String defaultValue) {
        return ApplicationParameters.get().getValue(AccountParameter.class, name, defaultValue);
    }

    @Override
    public void setParameter(String name, String value) {
        ApplicationParameters.get().setParameter(AccountParameter.class, name, value);
    }

    @Override
    public boolean hasFeature(Long accountId, String featureId) {
        AccountFeature feature = crudService().findSingle(AccountFeature.class, QueryParameters.with("account.id", accountId)
                .add("providerId", QueryConditions.eq(featureId)));

        return feature != null && feature.isEnabled();
    }

    @Override
    public boolean isPrintingEnabled(Long accountId) {
        Boolean enabled = crudService().executeProjection(Boolean.class, "select a.type.printingSupport from Account a where a.id = :accountId",
                QueryParameters.with("accountId", accountId));

        if (enabled == null) {
            enabled = true;
        }
        return enabled;
    }

    @Override
    public List<Long> findAccountsIdByFeature(String featureId) {
        String jpql = "select af.account.id from AccountFeature af where af.providerId = :feature and af.enabled = true and af.account.status = :status";
        return entityManager.createQuery(jpql)
                .setParameter("feature", featureId)
                .setParameter("status", AccountStatus.ACTIVE)
                .getResultList();
    }

    @Override
    public void log(Long accountId, String message) {
        if (accountId != null && message != null && !message.isEmpty()) {
            AccountLog log = new AccountLog();
            Account account = new Account();
            account.setId(accountId);
            log.setAccount(account);
            log.setMessage(message);
            log.setIp(HttpUtils.getClientIp());
            log.save();
        }
    }

    @Override
    public AccountStatusDTO getAccountStatusDetails(Long accountId) {
        List<AccountStatusDTO> result = crudService().executeQuery(QueryBuilder.select("id", "name", "status", "statusDate",
                "statusDescription", "globalMessage", "showGlobalMessage", "globalMessageType", "balance")
                .from(Account.class, "a").where("id", QueryConditions.eq(accountId))
                .resultType(AccountStatusDTO.class));

        return result.stream().findFirst().
                orElse(new AccountStatusDTO(accountId, "unknow",
                        AccountStatus.CANCELED, new Date(),
                        null, null,
                        false, null, BigDecimal.ZERO));
    }

    @Override
    public Long getAccountIdByDomain(String domain) {
        Long accountId = domainCache.get(domain);
        if (accountId == null) {
            Account account = service.getAccount(domain);
            if (account != null) {
                accountId = account.getId();
            } else {
                account = service.getAccountByCustomDomain(domain);
                if (account != null) {
                    accountId = account.getId();
                }
            }
            if (accountId != null) {
                domainCache.add(domain, accountId);
            }
        }

        return accountId;
    }

    @Override
    public void clearCache() {
        domainCache.clear();
        accountCache.clear();
    }

    @Override
    public void clearCache(Long accountId, String accountDomain) {
        if (accountId != null) {
            accountCache.remove(accountId);
        }
        if (accountDomain != null) {
            domainCache.remove(accountDomain);
        }
    }

    @Override
    public List<Long> findAccountsId(Map<String, Object> params) {
        var queryParams = new QueryParameters();
        queryParams.putAll(params);
        var query = QueryBuilder.select("id").from(Account.class, "a").where(queryParams);
        return crudService().executeQuery(query);
    }
}
