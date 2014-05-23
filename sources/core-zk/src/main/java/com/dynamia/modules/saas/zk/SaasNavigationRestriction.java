/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.zk;

import com.dynamia.modules.saas.AccountContext;
import com.dynamia.tools.web.navigation.NavigationElement;
import com.dynamia.tools.web.navigation.NavigationRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario_2
 */
@Component
public class SaasNavigationRestriction implements NavigationRestriction {

    @Autowired
    private AccountContext accountContext;

    @Override
    public Boolean allowAccess(NavigationElement element) {
        if (element.getVirtualPath().startsWith("saas") && !accountContext.isAdminAccount()) {
            return false;
        } else {
            return null;
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
