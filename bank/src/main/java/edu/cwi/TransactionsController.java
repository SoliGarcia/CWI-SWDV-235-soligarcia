package edu.cwi;

import java.util.Map;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {

    private static final UserAccount sampleAccount = new UserAccount(
        "demo-user",
        "John Public",
        AppRole.CUSTOMER,
        500.00,
        Map.of("1", "Deposited $500.00", "2", "Paid rent -$125.00")
    );

    @Override
    public void getAll(Context ctx) {
        ctx.render("jte/accountPage.jte", Map.of(
            "title", "Account Transactions",
            "account", sampleAccount,
            "name", sampleAccount.name(),
            "balance", sampleAccount.toMoney(),
            "transactions", sampleAccount.transactions()
        ));
    }

    @Override
    public void getOne(Context ctx, String id) {
        ctx.render("jte/accountPage.jte", Map.of(
            "title", "Account Transactions",
            "account", sampleAccount,
            "name", sampleAccount.name(),
            "balance", sampleAccount.toMoney(),
            "transactions", sampleAccount.transactions()
        ));
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