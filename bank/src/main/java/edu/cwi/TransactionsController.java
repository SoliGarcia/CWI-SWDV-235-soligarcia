package edu.cwi;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

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

    public void showDepositPage(Context ctx) {
        String username = ctx.cookieStore().get("username");
        if (username == null) {
            ctx.redirect("/unauthorized.html");
            return;
        }

        String accountRef = ctx.pathParam("id");
        UserAccount demoAccount = AuthController.getUser(accountRef).orElse(null);
        if (demoAccount != null) {
            if (!username.equals(accountRef)) {
                ctx.status(404).result("Account not found");
                return;
            }

            ctx.render("jte/depositPage.jte", Map.of(
                "title", "Make a Deposit",
                "accountReference", accountRef,
                "accountType", "Balance",
                "balance", demoAccount.formatBalance(),
                "postUrl", "/account/" + accountRef + "/transactions"
            ));
            return;
        }

        int accountId;
        try {
            accountId = Integer.parseInt(accountRef);
        } catch (NumberFormatException exception) {
            ctx.status(404).result("Account not found");
            return;
        }

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = session.getMapper(UserMapper.class).getByUsername(username);
            if (user == null) {
                ctx.cookieStore().clear();
                ctx.redirect("/");
                return;
            }

            Account account = session.getMapper(AccountMapper.class).getById(accountId);
            if (account == null || account.getUserId() != user.getId()) {
                ctx.status(404).result("Account not found");
                return;
            }

            ctx.render("jte/depositPage.jte", Map.of(
                "title", "Make a Deposit",
                "accountReference", String.valueOf(account.getAccountId()),
                "accountType", account.getAccountType(),
                "balance", account.getBalance().toPlainString(),
                "postUrl", "/account/" + account.getAccountId() + "/transactions"
            ));
        }
    }

    public void createForAccount(Context ctx) {
        String username = ctx.cookieStore().get("username");
        if (username == null) {
            ctx.redirect("/unauthorized.html");
            return;
        }

        String accountRef = ctx.pathParam("id");

        String rawAmount = ctx.formParam("amount");
        if (rawAmount == null || rawAmount.isBlank()) {
            ctx.status(400).result("A deposit amount is required");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(rawAmount.trim());
        } catch (NumberFormatException exception) {
            ctx.status(400).result("Enter a valid deposit amount");
            return;
        }

        if (amount.signum() <= 0
                || amount.stripTrailingZeros().scale() > 2
                || amount.compareTo(new BigDecimal("9999999999999.99")) > 0) {
            ctx.status(400).result("Deposit must be positive and no greater than $9,999,999,999,999.99 with at most two decimal places");
            return;
        }

        String description = ctx.formParam("description");
        if (description != null) {
            description = description.trim();
            if (description.isEmpty()) {
                description = null;
            } else if (description.length() > 255) {
                ctx.status(400).result("Description must be 255 characters or fewer");
                return;
            }
        }

        UserAccount demoAccount = AuthController.getUser(accountRef).orElse(null);
        if (demoAccount != null) {
            if (!username.equals(accountRef)) {
                ctx.status(404).result("Account not found");
                return;
            }
            if (AuthController.addDemoDeposit(username, amount, description).isEmpty()) {
                ctx.status(400).result("The resulting balance exceeds the account limit");
                return;
            }
            ctx.redirect("/account/" + accountRef);
            return;
        }

        int accountId;
        try {
            accountId = Integer.parseInt(accountRef);
        } catch (NumberFormatException exception) {
            ctx.status(404).result("Account not found");
            return;
        }

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.getByUsername(username);
            if (user == null) {
                ctx.cookieStore().clear();
                ctx.redirect("/");
                return;
            }

            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            Account account = accountMapper.getById(accountId);
            if (account == null || account.getUserId() != user.getId()) {
                ctx.status(404).result("Account not found");
                return;
            }

            if (accountMapper.deposit(accountId, amount) != 1) {
                ctx.status(404).result("Account not found");
                return;
            }

            Transaction transaction = new Transaction();
            transaction.setAccountId(accountId);
            transaction.setTransactionType("DEPOSIT");
            transaction.setAmount(amount);
            transaction.setDescription(description);
            if (session.getMapper(TransactionMapper.class).insert(transaction) != 1) {
                session.rollback();
                ctx.status(500).result("Could not save deposit transaction");
                return;
            }

            session.commit();
        }

        ctx.redirect("/account/" + accountId);
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