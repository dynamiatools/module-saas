package tools.dynamia.modules.saas.api;

import java.util.List;

public interface AccountStatsProvider {

    List<AccountStats> getAccountStats(Long accountId);
}
