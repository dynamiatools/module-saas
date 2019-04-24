package tools.dynamia.modules.saas.ui.action;

import tools.dynamia.actions.InstallAction;
import tools.dynamia.crud.AbstractCrudAction;
import tools.dynamia.crud.CrudActionEvent;
import tools.dynamia.domain.AbstractEntity;
import tools.dynamia.domain.query.ListDataSet;
import tools.dynamia.modules.saas.domain.AccountFeature;
import tools.dynamia.ui.UIMessages;

@InstallAction
public class SaveAccountFeaturesAction extends AbstractCrudAction {
    public SaveAccountFeaturesAction() {
        setName("Save");
        setApplicableClass(AccountFeature.class);
    }

    @Override
    public void actionPerformed(CrudActionEvent evt) {
        ListDataSet<AccountFeature> list = (ListDataSet<AccountFeature>) evt.getController().getQueryResult();
        crudService().executeWithinTransaction(() -> {
            list.getData().forEach(AbstractEntity::save);
        });
        UIMessages.showMessage("OK");
    }
}
