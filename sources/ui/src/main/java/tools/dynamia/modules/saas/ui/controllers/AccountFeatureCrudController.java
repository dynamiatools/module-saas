package tools.dynamia.modules.saas.ui.controllers;

import org.zkoss.zul.Checkbox;
import tools.dynamia.domain.query.QueryConditions;
import tools.dynamia.domain.query.QueryParameters;
import tools.dynamia.integration.Containers;
import tools.dynamia.modules.saas.AccountFeatureProvider;
import tools.dynamia.modules.saas.domain.Account;
import tools.dynamia.modules.saas.domain.AccountFeature;
import tools.dynamia.zk.crud.SubcrudController;

import java.util.ArrayList;
import java.util.List;

public class AccountFeatureCrudController extends SubcrudController<AccountFeature> {

    public AccountFeatureCrudController(Object parent, String parentName, String childrenName) {
        super(parent, parentName, childrenName);
    }

    public AccountFeatureCrudController(Class<AccountFeature> entityClass, Object parent, String parentName, String childrenName) {
        super(entityClass, parent, parentName, childrenName);
    }

    @Override
    public void query() {
        List<AccountFeature> result = new ArrayList<>();
        Containers.get().findObjects(AccountFeatureProvider.class).forEach(p -> {
            AccountFeature feature = crudService.findSingle(AccountFeature.class, QueryParameters.with("account", getParentEntity())
                    .add("providerId", QueryConditions.eq(p.getId())));
            if (feature == null) {
                feature = new AccountFeature();
                feature.setAccount((Account) getParentEntity());
                feature.setProviderId(p.getId());
                feature.setName(p.getName());
                feature.save();
            }

            result.add(feature);

        });
        setQueryResult(result);
    }
}
