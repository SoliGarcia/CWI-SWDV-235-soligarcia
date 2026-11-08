package edu.cwi.models;

import java.util.ArrayList;
import java.util.List;

public class AccountModel {

    private static List<Account> accounts = new ArrayList<>();

    static {
        accounts.add(
            new Account(1, "admin", "admin123", "admin")
        );

        accounts.add(
            new Account(2, "user", "user123", "user")
        );
    }

    public static Account authenticate(String username, String password) {

        return accounts.stream()
            .filter(a ->
                a.getUsername().equals(username) &&
                a.getPassword().equals(password))
            .findFirst()
            .orElse(null);
    }
}