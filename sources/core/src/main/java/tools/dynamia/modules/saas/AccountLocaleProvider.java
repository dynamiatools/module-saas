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
