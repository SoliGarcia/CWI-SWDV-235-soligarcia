package edu.cwi;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class TransactionsController implements CrudHandler {
    @Override
    public void getAll(Context ctx) {
        Integer userId = authenticatedUserId(ctx);
        if (userId == null) {
            return;
        }

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            ctx.json(session.getMapper(TransactionMapper.class).getByUserId(userId));
        }
    }

    @Override
    public void getOne(Context ctx, String resourceId) {
        Integer userId = authenticatedUserId(ctx);
        if (userId == null) {
            return;
        }

        final long transactionId;
        try {
            transactionId = Long.parseLong(resourceId);
        } catch (NumberFormatException exception) {
            ctx.status(400).json(Map.of("error", "transaction id must be a number"));
            return;
        }

        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            Transaction transaction = session.getMapper(TransactionMapper.class)
                .getByIdForUser(transactionId, userId);
            if (transaction == null) {
                ctx.status(404).json(Map.of("error", "transaction not found"));
                return;
            }
            ctx.json(transaction);
        }
    }

    @Override
    public void create(Context ctx) {
        ctx.status(405).json(Map.of("error", "transactions are created by account operations"));
    }

    @Override
    public void update(Context ctx, String resourceId) {
        ctx.status(405).json(Map.of("error", "transaction history cannot be changed"));
    }

    @Override
    public void delete(Context ctx, String resourceId) {
        ctx.status(405).json(Map.of("error", "transaction history cannot be deleted"));
    }

    private Integer authenticatedUserId(Context ctx) {
        String value = ctx.cookieStore().get("user-id");
        if (value == null) {
            ctx.status(401).json(Map.of("error", "login required"));
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            ctx.cookieStore().clear();
            ctx.status(401).json(Map.of("error", "login required"));
            return null;
        }
    }
}
