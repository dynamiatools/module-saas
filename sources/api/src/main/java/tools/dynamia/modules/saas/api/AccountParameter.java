/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tools.dynamia.domain.query.Parameter;

/**
 *
 * @author mario
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accountId", "param_name"})
})
public class AccountParameter extends Parameter implements AccountAware {

	
	private Long accountId;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


}
