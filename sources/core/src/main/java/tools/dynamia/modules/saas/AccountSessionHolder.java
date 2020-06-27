
package tools.dynamia.modules.saas;

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

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.integration.Containers;
import tools.dynamia.integration.sterotypes.Component;
import tools.dynamia.modules.saas.api.AccountException;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.domain.Account;

import java.util.Locale;

/**
 * @author Mario Serrano Leones
 */
@Component("accountSessionHolder")
@Scope("session")
public class AccountSessionHolder {

    private Locale accountLocale;
    private Account current;
    private AccountDTO currentDTO;

    public static AccountSessionHolder get() {
        AccountSessionHolder accountSessionHolder = null;
        try {
            accountSessionHolder = Containers.get().findObject(AccountSessionHolder.class);
        } catch (Exception e) {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                accountSessionHolder = (AccountSessionHolder) attributes.getAttribute("accountSessionHolder", RequestAttributes.SCOPE_SESSION);
            }
        }

        if (accountSessionHolder == null) {
            throw new AccountException("Cannot found instance of " + AccountSessionHolder.class + " in current thread.");
        }
        return accountSessionHolder;
    }

    public Account getCurrent() {
        return current;
    }

    public void setCurrent(final Account account) {
        if (account != null) {
            try {
                DomainUtils.lookupCrudService().executeWithinTransaction(() -> {
                    this.current = DomainUtils.lookupCrudService().reload(account);
                    current.getFeatures().size();
                    current.getAdditionalServices().size();
                    current.getStats().size();
                    current.getType().getRestrictions().size();
                    accountLocale = current.getLocale() != null ? Locale.forLanguageTag(current.getLocale()) : null;

                    currentDTO = null;
                    toDTO();
                });
            } catch (Exception e) {
                //ignore
            }
        }
    }


    public AccountDTO toDTO() {
        if (currentDTO == null && current != null) {
            currentDTO = current.toDTO();
        }
        return currentDTO;
    }

    public Locale getAccountLocale() {
        if (accountLocale == null) {
            accountLocale = Locale.getDefault();
        }
        return accountLocale;
    }
}
