package tools.dynamia.modules.saas;

import tools.dynamia.commons.LocaleProvider;
import tools.dynamia.commons.logger.LoggingService;
import tools.dynamia.commons.logger.SLF4JLoggingService;
import tools.dynamia.integration.sterotypes.Provider;

import java.util.Locale;

@Provider
public class AccountLocaleProvider implements LocaleProvider {

    private LoggingService logger = new SLF4JLoggingService(AccountLocaleProvider.class);

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public Locale getDefaultLocale() {
        try {
            return AccountSessionHolder.get().getAccountLocale();
        } catch (Exception e) {
            logger.warn("Cannot get current account locale: " + e.getMessage());
            return null;
        }
    }
}
