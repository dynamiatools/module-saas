package tools.dynamia.modules.saas.validators;

import tools.dynamia.domain.ValidationError;
import tools.dynamia.domain.Validator;
import tools.dynamia.integration.sterotypes.Provider;
import tools.dynamia.modules.saas.domain.AccountPayment;

@Provider
public class AccountPaymentValidator implements Validator<AccountPayment> {

    @Override
    public void validate(AccountPayment accountPayment) throws ValidationError {
        if (accountPayment.getValue() == null || accountPayment.getValue().longValue() <= 0) {
            throw new ValidationError("Enter valid value");
        }

    }
}
