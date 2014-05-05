/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.validators;

import com.dynamia.modules.saas.AccountAware;
import com.dynamia.modules.saas.AccountContext;
import com.dynamia.tools.domain.ValidationError;
import com.dynamia.tools.domain.Validator;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario_2
 */
@Component
public class AccountAwareValidator implements Validator<AccountAware> {

    @Override
    public void validate(AccountAware t) throws ValidationError {
        if (t.getAccount() == null) {
            t.setAccount(AccountContext.getCurrent().getAccount());
        }
    }

}
