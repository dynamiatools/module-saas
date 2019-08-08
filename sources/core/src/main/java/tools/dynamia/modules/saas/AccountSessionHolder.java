
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
import tools.dynamia.commons.LocaleProvider;
import tools.dynamia.integration.Containers;
import tools.dynamia.integration.sterotypes.Component;
import tools.dynamia.modules.saas.domain.Account;

import java.util.Locale;

/**
 * @author Mario Serrano Leones
 */
@Component("accountSessionHolder")
@Scope("session")
public class AccountSessionHolder implements LocaleProvider {

    private Locale accountLocale;
    private Account current;

    public static AccountSessionHolder get() {
        return Containers.get().findObject(AccountSessionHolder.class);
    }

    public Account getCurrent() {
        return current;
    }

    public void setCurrent(Account current) {
        this.current = current;
        if (current != null) {
            try {
                accountLocale = current.getLocale() != null ? Locale.forLanguageTag(current.getLocale()) : Locale.getDefault();
            } catch (Exception e) {
                accountLocale = Locale.getDefault();
            }
        }
    }
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Locale getDefaultLocale() {
        return accountLocale;
    }
}
