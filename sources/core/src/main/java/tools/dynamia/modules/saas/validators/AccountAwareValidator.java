
package tools.dynamia.modules.saas.validators;

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

import org.springframework.stereotype.Component;

import tools.dynamia.domain.ValidationError;
import tools.dynamia.domain.Validator;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.api.AccountAware;

/**
 *
 * @author Mario Serrano Leones
 */
@Component
public class AccountAwareValidator implements Validator<AccountAware> {

    @Override
    public void validate(AccountAware t) throws ValidationError {
        if (t.getAccountId() == null) {
            try {
				t.setAccountId(AccountContext.getCurrent().getAccount().getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

}
