/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas;

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
