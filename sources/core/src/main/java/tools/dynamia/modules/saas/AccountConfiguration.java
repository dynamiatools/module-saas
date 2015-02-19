/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import tools.dynamia.modules.saas.listeners.AccountAwareCrudServiceListener;
import tools.dynamia.modules.saas.services.AccountService;
import tools.dynamia.modules.saas.services.impl.AccountServiceImpl;

/**
 *
 * @author mario
 */
//@Configuration
@Order(0)
public class AccountConfiguration {
    
    @Autowired
    private AccountAwareCrudServiceListener listener;

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }

}
