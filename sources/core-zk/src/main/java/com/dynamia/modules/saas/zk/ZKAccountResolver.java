/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.zk;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author mario
 */
@Component
public class ZKAccountResolver extends HttpAccountResolver {

    @Override
    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) Executions.getCurrent().getNativeRequest();
    }

}
