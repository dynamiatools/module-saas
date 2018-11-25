
package tools.dynamia.modules.saas.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.integration.sterotypes.Listener;
import tools.dynamia.modules.saas.domain.AccountPayment;

/**
 *
 * @author Mario Serrano Leones
 */
@Listener
public class AccountPaymenCrudListener extends CrudServiceListenerAdapter<AccountPayment> {

    @Autowired
    private CrudService crudService;

    @Override
    public void afterCreate(AccountPayment payment) {
        payment.getAccount().setLastPaymentDate(payment.getCreationDate());
        crudService.update(payment.getAccount());

    }

}
