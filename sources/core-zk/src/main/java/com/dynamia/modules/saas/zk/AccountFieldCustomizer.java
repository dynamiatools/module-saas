/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.zk;

import com.dynamia.modules.saas.domain.Account;
import com.dynamia.tools.viewers.Field;
import com.dynamia.tools.viewers.FieldCustomizer;
import com.dynamia.tools.viewers.FieldGroup;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario
 */
@Component
public class AccountFieldCustomizer implements FieldCustomizer {

    @Override
    public void customize(String viewTypeName, Field field) {
        if (field.getFieldClass() == Account.class) {
            field.getViewDescriptor().removeField(field.getName());
            FieldGroup group = field.getGroup();
            if (group != null) {
                group.removeField(field);
            }
        }
    }

}
