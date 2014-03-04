/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas;

import com.dynamia.modules.saas.listeners.AccountAwareCrudServiceListener;
import com.dynamia.modules.saas.services.AccountService;
import com.dynamia.modules.saas.services.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 *
 * @author mario
 */
@Configuration
@Order(0)
public class AccountConfiguration {
    
    @Autowired
    private AccountAwareCrudServiceListener listener;

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

}
