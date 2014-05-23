/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.zk.action;

import com.dynamia.modules.saas.domain.Account;
import com.dynamia.modules.saas.services.AccountService;
import com.dynamia.tools.commons.ApplicableClass;
import com.dynamia.tools.web.actions.InstallAction;
import com.dynamia.tools.web.crud.AbstractCrudAction;
import com.dynamia.tools.web.crud.CrudActionEvent;
import com.dynamia.tools.web.crud.CrudState;
import com.dynamia.tools.web.ui.UIMessages;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mario_2
 */
@InstallAction
public class SetDefaultAccountAction extends AbstractCrudAction {

    @Autowired
    private AccountService accountService;

    public SetDefaultAccountAction() {
        setName("Set as Default Account");
        setImage("star");
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        Account account = (Account) evt.getData();
        if (account != null) {
            accountService.setDefaultAccount(account);
            UIMessages.showMessage(account + " set as default account succefully");
            evt.getController().doQuery();
        }
    }

    @Override
    public CrudState[] getApplicableStates() {
        return CrudState.get(CrudState.READ, CrudState.UPDATE);
    }

    @Override
    public ApplicableClass[] getApplicableClasses() {
        return ApplicableClass.get(Account.class);
    }

}
