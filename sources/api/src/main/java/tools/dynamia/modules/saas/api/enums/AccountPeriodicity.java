
package tools.dynamia.modules.saas.api.enums;

/**
 *
 * @author Mario Serrano Leones
 */
public enum AccountPeriodicity {

    MONTHLY(30), YEARLY(30 * 12), UNLIMITED(Integer.MAX_VALUE);

    private int days;

    AccountPeriodicity(int numeroDias) {
        this.days = numeroDias;
    }

    public int getDaysCount() {
        return days;
    }

}
