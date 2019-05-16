package tools.dynamia.modules.saas.ui.controllers;

/*-
 * #%L
 * DynamiaModules - SaaS UI
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
