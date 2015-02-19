/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dynamia.modules.saas.enums;

/**
 *
 * @author mario
 */
public enum AccountPeriodicity {

    MONTHLY(30), YEARLY(30 * 12), UNLIMITED(Integer.MAX_VALUE);

    private int numeroDias;

    private AccountPeriodicity(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    public int getNumeroDias() {
        return numeroDias;
    }

}
