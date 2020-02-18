
package tools.dynamia.modules.saas.ui;

/*-
 * #%L
 * DynamiaModules - SaaS UI
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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tools.dynamia.modules.saas.AccountResolver;
import tools.dynamia.modules.saas.AccountSessionHolder;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.web.util.HttpUtils;

/**
 * @author Mario Serrano Leones
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
