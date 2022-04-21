
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

package tools.dynamia.modules.saas;

import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tools.dynamia.domain.util.DomainUtils;
import tools.dynamia.integration.Containers;
import tools.dynamia.integration.sterotypes.Component;
import tools.dynamia.modules.saas.api.AccountException;
import tools.dynamia.modules.saas.api.dto.AccountDTO;
import tools.dynamia.modules.saas.domain.Account;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author Mario Serrano Leones
 */
@Component("accountSessionHolder")
@SessionScope
public class AccountSessionHolder implements Serializable {

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
                    this.current = DomainUtils.lookupCrudService().find(Account.class,account.getId());
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
