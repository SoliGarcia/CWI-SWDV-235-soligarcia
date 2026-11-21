package edu.cwi;

import java.text.DecimalFormat;
import java.util.Map;

public record UserAccount (String username, String password, AppRole role, Double balance, Map<String, String> transactions) {

    public String Balance() {
        DecimalFormat df = new DecimalFormat("$#,###.00");
        return df.format(this.balance);
    }
}