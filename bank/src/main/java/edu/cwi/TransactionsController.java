package edu.cwi;

import java.util.Map;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {

    private final Map<String, String> transactions = Map.of();

    @Override
    public void getAll(Context ctx) {
        ctx.render("jte/transactionPage.jte", Map.of("transactions", transactions));

        String userId = ctx.cookieStore().get("user-id");

            if (userId == null) {
                ctx.redirect("/unauthorized.html");
                return;
            }

        AuthController.getUser(userId).ifPresentOrElse(
            account -> ctx.render(
                "jte/accountPage.jte",
                Map.of(
                    "title", "Account Transactions",
                    "account", account
                )
            ),
            () -> ctx.redirect("/unauthorized.html")
        );
          
    }

    @Override
    public void getOne(Context ctx, String id) {
    }

    @Override
    public void create(Context ctx) {
        ctx.redirect("/transactions");
    }

    @Override
    public void update(Context ctx, String id) {
        ctx.redirect("/transactions");
    }

    @Override
    public void delete(Context ctx, String id) {
        ctx.redirect("/transactions");

    }

}