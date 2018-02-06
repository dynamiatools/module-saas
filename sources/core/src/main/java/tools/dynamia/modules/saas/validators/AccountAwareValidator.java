/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.validators;

import org.springframework.stereotype.Component;

import tools.dynamia.domain.ValidationError;
import tools.dynamia.domain.Validator;
import tools.dynamia.modules.saas.AccountContext;
import tools.dynamia.modules.saas.api.AccountAware;

/**
 *
 * @author mario_2
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
