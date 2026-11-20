package edu.cwi;

import java.util.List;
import java.util.Map;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {

    private final List<?> transactions = List.of();

    @Override
    public void getAll(Context ctx) {
        UserAccount account = new UserAccount("John Public", 500.00, transactions);
	
	    ctx.render("jte/accountPage.jte", Map.of("title", "Account Transactions", "account", account));
          
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