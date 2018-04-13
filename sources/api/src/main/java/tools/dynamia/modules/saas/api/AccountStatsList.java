package tools.dynamia.modules.saas.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountStatsList implements Serializable {

    private List<AccountStats> data = new ArrayList<>();


    public void add(AccountStats stats) {
        data.add(stats);
    }

    public void add(List<AccountStats> stats) {
        data.addAll(stats);
    }

    public List<AccountStats> getData() {
        return data;
    }

    public void setData(List<AccountStats> data) {
        this.data = data;
    }
}
