package edu.cwi;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {

    private final Map<String, String> transactions = Map.of();

    @Override
        public void getAll(Context ctx) {

            Integer userId = ctx.cookieStore().get("user-id");

            if (userId == null) {
                ctx.redirect("/unauthorized.html");
                return;
            }

            try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {

                UserMapper userMapper = session.getMapper(UserMapper.class);
                AccountMapper accountMapper = session.getMapper(AccountMapper.class);
                TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);

                User user = userMapper.getById(userId);

                if (user == null) {
                    ctx.redirect("/unauthorized.html");
                    return;
                }

                var accounts = accountMapper.getByUserId(userId);

                if (accounts.isEmpty()) {
                    ctx.redirect("/unauthorized.html");
                    return;
                }

                Account account = accounts.get(0);

                Map<String, String> transactionMap = new java.util.LinkedHashMap<>();

                for (Transaction transaction :
                        transactionMapper.getByAccountId(account.getAccountId())) {

                    transactionMap.put(
                        String.valueOf(transaction.getTransactionId()),
                        transaction.getDescription()
                    );
                }

                UserAccount userAccount = new UserAccount(
                    String.valueOf(user.getId()),
                    user.getFirstName() + " " + user.getLastName(),
                    AppRole.valueOf(user.getRole()),
                    account.getBalance().doubleValue(),
                    transactionMap
                );

                ctx.render(
                    "jte/accountPage.jte",
                    Map.of(
                        "title", "Account Transactions",
                        "account", userAccount,
                        "detail", "Account Details"
                    )
                );
            }
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