package edu.cwi;

import java.text.DecimalFormat;
import java.util.Map;

public record UserAccount(String id, String name, AppRole role, double balance, Map<String, String> transactions) {

    public String toMoney() {
        DecimalFormat df = new DecimalFormat("$#,###.00");
        return df.format(this.balance);
    }

    public String toMoneyFormat() {
        return toMoney();
    }
}

