/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import tools.dynamia.domain.SimpleEntity;

/**
 *
 * @author mario
 */
@MappedSuperclass
public abstract class SimpleEntitySaaS extends SimpleEntity implements AccountAware {

	@NotNull
	private Long accountId;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
