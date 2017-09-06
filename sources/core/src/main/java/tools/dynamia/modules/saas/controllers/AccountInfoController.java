package tools.dynamia.modules.saas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.modules.saas.api.AccountInfo;
import tools.dynamia.modules.saas.api.enums.AccountPeriodicity;
import tools.dynamia.modules.saas.api.enums.AccountStatus;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountLog;
import tools.dynamia.web.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/saas")
public class AccountInfoController {

    private static final AccountInfo NO_ACCOUNT = new AccountInfo(1L, "Invalid License", "1", "account@api.com",
            AccountStatus.CANCELED, AccountPeriodicity.MONTHLY, "Invalid", new Date(), "", null, "Licencia Invalida", null, "", null,
            "GMT-5", 10000, true, 1, null, "Invalid", false, null);


    @Autowired
    private CrudService crudService;

    @GetMapping("/account/{uuid}")
    public AccountInfo getAccount(@PathVariable("uuid") String uuid, HttpServletRequest request) {

        if (uuid != null && !uuid.isEmpty()) {

            Account account = crudService.findSingle(Account.class, QueryParameters.with("uuid", QueryConditions.eq(uuid))
                    .add("remote", true)
                    .add("status", QueryConditions.isNotNull()));

            if (account != null) {
                newLog(uuid,request,account);
                return account.getInfo();
            }

        }

        return NO_ACCOUNT;

    }

    private void newLog(String uuid, HttpServletRequest request, Account account) {
        try {
            AccountLog log = new AccountLog(account, HttpUtils.getIpFromRequest(request),"Remote account check");
            log.setPathInfo(request.getPathInfo());
            log.setClientInfo(request.getParameter("info"));
            crudService.create(log);
        }catch (Exception e){

        }
    }
}
