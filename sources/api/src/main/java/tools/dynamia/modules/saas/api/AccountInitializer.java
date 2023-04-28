
/*
 * Copyright (C) 2023 Dynamia Soluciones IT S.A.S - NIT 900302344-1
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

package tools.dynamia.modules.saas.api;

import tools.dynamia.commons.LocalizedMessagesProvider;
import tools.dynamia.commons.Messages;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.api.dto.AccountDTO;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Mario Serrano Leones
 */
public interface AccountInitializer {

    void init(AccountDTO accountDTO);

    /**
     * Define account initializer priority or order. Lower value is high priority
     *
     * @return
     */
    default int getPriority() {
        return 0;
    }


    default String localizedMessage(String key, AccountDTO accountDTO) {
        try {
            var provider = Containers.get().findObjects(LocalizedMessagesProvider.class)
                    .stream().min(Comparator.comparingInt(LocalizedMessagesProvider::getPriority))
                    .orElse(null);

            if (provider != null) {
                var locale = accountDTO.getLocale() != null ? Locale.forLanguageTag(accountDTO.getLocale()) : Messages.getDefaultLocale();
                if (locale != null) {
                    return provider.getMessage(key, "* " + getClass().getSimpleName(), locale, key);
                } else {
                    return key;
                }
            } else {
                return key;
            }
        } catch (Exception e) {
            System.err.println("Error loading localized message " + e);
            e.printStackTrace();
            return key;
        }
    }

}
