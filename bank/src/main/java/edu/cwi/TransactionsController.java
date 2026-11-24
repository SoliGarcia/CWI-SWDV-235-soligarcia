package edu.cwi;

import java.util.Map;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {

    private final Map<String, String> transactions = Map.of();

    @Override
    public void getAll(Context ctx) {
        String username = ctx.cookieStore().get("username");

        if (username == null) {
            ctx.redirect("/unauthorized.html");
            return;
        }

        ctx.redirect("/accounts");
    }


    @Override
    public void getOne(Context ctx, String resourceId) {

        String transaction = transactions.get(resourceId);

        if (transaction != null) {
            // Use ctx to render transaction html
            ctx.render("transactions.html");
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void create(Context ctx) {

        String body = ctx.body();
        String id = String.valueOf(transactions.size() + 1);

        transactions.put(id, body);

		// Use ctx to render all transactions html
        ctx.render("transactions.html");
    }

    @Override
    public void update(Context ctx, String resourceId) {

        if (transactions.containsKey(resourceId)) {
            transactions.put(resourceId, ctx.body());

            // Use ctx to render all transactions html
            ctx.render("transactions.html");
        } else {
            ctx.status(404).result("Transaction not found");
        }
    }

    @Override
    public void delete(Context ctx, String resourceId) {

        transactions.remove(resourceId);

		// Use ctx to render all transactions html
        ctx.render("transactions.html");
    }
}