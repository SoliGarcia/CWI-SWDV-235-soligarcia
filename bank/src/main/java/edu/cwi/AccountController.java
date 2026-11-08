package edu.cwi;

import io.javalin.http.Context;

import java.util.Map;

public class AccountController {
    public static void renderAccount(Context ctx) {
        String userId = ctx.cookieStore().get("user-id");
        if (userId == null || userId.isBlank()) {
            ctx.redirect("/");
            return;
        }

        UserAccount user = AuthController.getUser(userId).orElse(null);
        if (user == null) {
            ctx.cookieStore().clear();
            ctx.redirect("/");
            return;
        }

        ctx.render("jte/accountPage.jte", Map.of(
            "title", "Account Transactions",
            "account", user,
            "name", user.name(),
            "balance", user.toMoney(),
            "transactions", user.transactions()
        ));
    }
}
