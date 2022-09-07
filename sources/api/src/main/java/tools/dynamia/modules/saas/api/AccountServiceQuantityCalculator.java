package tools.dynamia.modules.saas.api;

/**
 * Provider of quantity for aditional services
 */
public interface AccountServiceQuantityCalculator {

    String getId();

    String getName();

    long calculate(Long accountId);
}
