package edu.cwi;

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

            ctx.render("jte/accountsPage.jte", Map.of(
                "userName", displayName(user),
                "accounts", accounts
            ));
        }
    }

    public static void getAccount(Context ctx) {
        String username = ctx.cookieStore().get("username");
        if (username == null) {
            ctx.redirect("/");
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

            ctx.render("jte/accountDetailPage.jte", Map.of(
                "userName", displayName(user),
                "account", account,
                "transactions", transactions
            ));
        }
    }

    private static String displayName(User user) {
        String firstName = user.getFirstName() == null ? "" : user.getFirstName().trim();
        String lastName = user.getLastName() == null ? "" : user.getLastName().trim();
        String name = (firstName + " " + lastName).trim();
        return name.isEmpty() ? user.getUsername() : name;
    }
}
