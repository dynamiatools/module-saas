/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tools.dynamia.modules.saas.AccountResolver;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

/**
 *
 * @author mario_2
 */
@Component
public class HttpAccountResolver implements AccountResolver {

    @Autowired
    private AccountService service;

    private static final String ATTRIBUTE_SAAS_ACCOUNT = "saas_account";

    @Override
    public Account resolve() {
        try {

            HttpServletRequest request = getHttpRequest();

            Account account = (Account) request.getSession().getAttribute(ATTRIBUTE_SAAS_ACCOUNT);

            if (account == null) {
                account = service.getAccount(request);
                if (account == null) {
                    account = service.getDefaultAccount();
                }
                if (account != null) {
                    request.getSession().setAttribute(ATTRIBUTE_SAAS_ACCOUNT, account);
                }
            }

            return account;
        } catch (Exception e) {
            return null;
        }
    }

    protected HttpServletRequest getHttpRequest() {
        return HttpUtils.getCurrentRequest();
    }
}
