/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import tools.dynamia.modules.saas.api.dto.AccountDTO;

/**
 *
 * @author mario
 */
public interface AccountInitializer {

    void init(AccountDTO accountDTO);

}
