/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.zk;

import com.dynamia.modules.saas.AccountResolver;
import com.dynamia.modules.saas.domain.Account;
import com.dynamia.modules.saas.services.AccountService;
import com.dynamia.tools.web.util.HttpUtils;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mario_2
 */
@Component
public class HttpAccountResolver implements AccountResolver {

    @Autowired
    private AccountService service;

    @Override
    public Account resolve() {
        try {

            HttpServletRequest request = HttpUtils.getCurrentRequest();
            return service.getAccount(request);
        } catch (Exception e) {
            return null;
        }
    }
}
