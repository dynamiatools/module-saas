package tools.dynamia.modules.saas.controllers;

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
import org.springframework.web.bind.annotation.*;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.AbstractService;
import tools.dynamia.modules.saas.api.AccountStatsList;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountLog;
import tools.dynamia.modules.saas.jpa.AccountParameter;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/saas")
public class AccountApiController extends AbstractService {

    private static final AccountDTO NO_ACCOUNT;

    static {
        NO_ACCOUNT = new AccountDTO();
        NO_ACCOUNT.setId(1L);
        NO_ACCOUNT.setStatus(AccountStatus.CANCELED);
        NO_ACCOUNT.setStatusDate(new Date());
        NO_ACCOUNT.setStatusDescription("Invalid License");
    }

    private AccountService service;

    @Autowired
    public AccountApiController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/account/{uuid}")
    public AccountDTO getAccount(@PathVariable("uuid") String uuid, HttpServletRequest request) {


        Account account = getAccount(uuid);

        if (account != null) {
            newLog(uuid, request, account);
            AccountDTO accountDTO = account.toDTO();
            if (accountDTO.getRequiredInstanceUuid()) {
                String uuidhw = request.getParameter("uuid");
                if (!uuidhw.equalsIgnoreCase(accountDTO.getInstanceUuid())) {
                    accountDTO.setStatus(AccountStatus.NEW);
                    accountDTO.setStatusDescription("Licencia invalida");
                }
            }
            return accountDTO;
        }


        return NO_ACCOUNT;

    }

    @PostMapping("/account/{uuid}/stats")
    public String updateStats(@PathVariable("uuid") String uuid, @RequestBody AccountStatsList stats, HttpServletRequest request) {


        Account account = getAccount(uuid);
        if (account != null) {
            if (stats != null) {
                service.updateStats(account, stats.getData());
                return "DONE: Account stats updated";
            } else {
                return "Invalid stats";
            }
        } else {
            return "Cannot found account with uuid: " + uuid;
        }
    }

    private Account getAccount(@PathVariable("uuid") String uuid) {
        if (uuid != null && !uuid.isEmpty()) {

            return crudService().findSingle(Account.class, QueryParameters.with("uuid", QueryConditions.eq(uuid)));
        } else {
            return null;
        }
    }

    @GetMapping("/account/{uuid}/parameter/{name}")
    public String getAccountParameter(@PathVariable("uuid") String uuid, @PathVariable("name") String name, HttpServletRequest request) {
        String defautlValue = request.getParameter("defaultValue");
        String value = null;
        Account account = getAccount(uuid);
        if (account != null) {
            AccountParameter parameter = crudService().findSingle(AccountParameter.class, QueryParameters.with("name", QueryConditions.eq(name)).add("accountId", account.getId()));
            if (parameter != null) {
                value = parameter.getValue();
            } else if (defautlValue != null) {
                value = defautlValue;
                crudService().executeWithinTransaction(() -> {
                    AccountParameter newParam = new AccountParameter();
                    newParam.setAccountId(account.getId());
                    newParam.setName(name);
                    newParam.setValue(defautlValue);
                    newParam.save();
                });
            }
        }
        return value;
    }

    private void newLog(String uuid, HttpServletRequest request, Account account) {
        try {
            AccountLog log = new AccountLog(account, HttpUtils.getIpFromRequest(request), "Remote account check");
            log.setPathInfo(request.getPathInfo());
            log.setClientInfo(request.getParameter("info"));
            crudService().create(log);
        } catch (Exception e) {

        }
    }
}
