package edu.cwi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import io.javalin.http.Context;

public class AccountController {
    public static void renderAccount(Context ctx) {
        String userIdValue = ctx.cookieStore().get("user-id");
        if (userIdValue == null) {
            ctx.redirect("/login");
            return;
        }

        final int userId;
        try {
            userId = Integer.parseInt(userIdValue);
        } catch (NumberFormatException exception) {
            ctx.cookieStore().clear();
            ctx.redirect("/login");
            return;
        }

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = session.getMapper(UserMapper.class).getById(userId);
            if (user == null) {
                ctx.cookieStore().clear();
                ctx.redirect("/login");
                return;
            }

            List<Account> accounts = session.getMapper(AccountMapper.class).getByUserId(userId);
            TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);
            Map<Integer, List<Transaction>> transactionsByAccount = new HashMap<>();
            for (Account account : accounts) {
                transactionsByAccount.put(
                    account.getAccountId(),
                    transactionMapper.getByAccountId(account.getAccountId(), userId)
                );
            }

            ctx.render("jte/accountPage.jte", Map.of(
                "title", "Account Transactions",
                "user", user,
                "accounts", accounts,
                "transactionsByAccount", transactionsByAccount
            ));
        }
    }
}
