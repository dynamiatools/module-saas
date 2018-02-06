/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import tools.dynamia.domain.services.CrudService;
import tools.dynamia.domain.util.CrudServiceListenerAdapter;
import tools.dynamia.integration.sterotypes.Listener;
import tools.dynamia.modules.saas.domain.AccountPayment;

/**
 *
 * @author mario
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
