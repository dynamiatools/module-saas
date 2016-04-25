/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.api.enums;

/**
 *
 * @author mario
 */
public enum AccountPeriodicity {

    MONTHLY(30), YEARLY(30 * 12), UNLIMITED(Integer.MAX_VALUE);

    private int days;

    private AccountPeriodicity(int numeroDias) {
        this.days = numeroDias;
    }

    public int getDaysCount() {
        return days;
    }

}
