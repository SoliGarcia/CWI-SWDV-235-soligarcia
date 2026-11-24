package edu.cwi;

import java.util.Map;
import java.util.Optional;

import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;


public class AuthController {
    
    private static final Map<String, UserAccount> userMap = Map.of(
        "ajones", new UserAccount(BCrypt.hashpw("weak", BCrypt.gensalt()), "Alice Jones", AppRole.CUSTOMER, 750.00, Map.of("1", "Deposited $750.00")),
        "bjones", new UserAccount(BCrypt.hashpw("weak", BCrypt.gensalt()), "Bob Jones", AppRole.CUSTOMER, 500.00, Map.of("1", "Deposited $500.00"))
    );

    public static Optional<UserAccount> getUser(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userMap.get(username));
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