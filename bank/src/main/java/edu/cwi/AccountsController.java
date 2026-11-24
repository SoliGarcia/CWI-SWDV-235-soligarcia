package edu.cwi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import io.javalin.http.Context;

public class AccountsController {

    public static void getAccounts(Context ctx) {
        String username = ctx.cookieStore().get("username");
        if (username == null) {
            ctx.redirect("/");
            return;
        }

        UserAccount demoAccount = AuthController.getUser(username).orElse(null);
        if (demoAccount != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("title", "My Account");
            model.put("userName", demoAccount.username());
            model.put("accounts", List.of());
            model.put("account", null);
            model.put("transactions", List.of());
            model.put("demoAccount", demoAccount);
            ctx.render("jte/accountPage.jte", model);
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
            List<Account> accounts = accountMapper.getByUserId(user.getId());

            Map<String, Object> model = new HashMap<>();
            model.put("title", "My Accounts");
            model.put("userName", displayName(user));
            model.put("accounts", accounts);
            model.put("account", null);
            model.put("transactions", List.of());
            model.put("demoAccount", null);
            ctx.render("jte/accountPage.jte", model);
        }
    }

    public static void getAccount(Context ctx) {
        String username = ctx.cookieStore().get("username");
        if (username == null) {
            ctx.redirect("/");
            return;
        }

        UserAccount demoAccount = AuthController.getUser(ctx.pathParam("id")).orElse(null);
        if (demoAccount != null) {
            if (!username.equals(ctx.pathParam("id"))) {
                ctx.status(404).result("Account not found");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("title", "Account Details");
            model.put("userName", demoAccount.username());
            model.put("accounts", List.of());
            model.put("account", null);
            model.put("transactions", List.of());
            model.put("demoAccount", demoAccount);
            ctx.render("jte/accountPage.jte", model);
            return;
        }

        int accountId;
        try {
            accountId = Integer.parseInt(ctx.pathParam("id"));
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

            TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);
            List<Transaction> transactions = transactionMapper.getByAccountId(accountId);

            Map<String, Object> model = new HashMap<>();
            model.put("title", "Account Details");
            model.put("userName", displayName(user));
            model.put("accounts", List.of());
            model.put("account", account);
            model.put("transactions", transactions);
            model.put("demoAccount", null);
            ctx.render("jte/accountPage.jte", model);
        }
    }

    private static String displayName(User user) {
        String firstName = user.getFirstName() == null ? "" : user.getFirstName().trim();
        String lastName = user.getLastName() == null ? "" : user.getLastName().trim();
        String name = (firstName + " " + lastName).trim();
        return name.isEmpty() ? user.getUsername() : name;
    }
}
