package edu.cwi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;


public class AuthController {
    
    private static final ConcurrentMap<String, UserAccount> userMap = new ConcurrentHashMap<>(Map.of(
        "ajones", new UserAccount(BCrypt.hashpw("weak", BCrypt.gensalt()), "ajones", "Alice Jones", AppRole.CUSTOMER, 750.00, Map.of("1", "Deposited $750.00")),
        "bjones", new UserAccount(BCrypt.hashpw("weak", BCrypt.gensalt()), "bjones", "Bob Jones", AppRole.CUSTOMER, 500.00, Map.of("1", "Deposited $500.00"))
    ));

    public static Optional<UserAccount> getUser(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userMap.get(username));
    }

    public static synchronized Optional<UserAccount> addDemoDeposit(
            String username, BigDecimal amount, String description) {
        if (username == null || amount == null || amount.signum() <= 0) {
            return Optional.empty();
        }

        UserAccount account = userMap.get(username);
        if (account == null) {
            return Optional.empty();
        }

        BigDecimal newBalance = BigDecimal.valueOf(account.balance()).add(amount);
        if (newBalance.compareTo(new BigDecimal("9999999999999.99")) > 0) {
            return Optional.empty();
        }

        Map<String, String> transactions = new HashMap<>(account.transactions());
        String transactionDescription = "Deposited $" + amount.setScale(2).toPlainString();
        if (description != null && !description.isBlank()) {
            transactionDescription += " - " + description;
        }
        transactions.put(String.valueOf(transactions.size() + 1), transactionDescription);
        UserAccount updatedAccount = new UserAccount(
            account.id(),
            account.loginUsername(),
            account.username(),
            account.role(),
            newBalance.doubleValue(),
            Map.copyOf(transactions)
        );
        userMap.put(username, updatedAccount);
        return Optional.of(updatedAccount);
    }

    public static Optional<UserAccount> authenticateDemoUser(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }

        UserAccount account = userMap.get(username);
        if (account == null || !BCrypt.checkpw(password, account.id())) {
            return Optional.empty();
        }
        return Optional.of(account);
    }

    public static void authenticate(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        Optional<UserAccount> demoAccount = authenticateDemoUser(username, password);
        if (demoAccount.isPresent()) {
            ctx.cookieStore().set("username", username);
            ctx.cookieStore().set("user-role", demoAccount.get().role().name());
            ctx.redirect("/accounts");
            return;
        }

        if (username != null && password != null) {
            User user = new UsersController().authenticateUser(ctx, username, password);
            if (user != null) {
                ctx.cookieStore().set("username", user.getUsername());
                ctx.cookieStore().set("user-role", user.getRole());
                ctx.redirect("/accounts");
                return;
            }
        }

        ctx.cookieStore().clear();
        ctx.redirect("/unauthorized.html");
    }

    public static void logout(Context ctx) {
        ctx.cookieStore().clear();
        ctx.redirect("/");
    }
}