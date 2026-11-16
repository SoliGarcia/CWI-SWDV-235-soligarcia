package edu.cwi;

import java.math.BigDecimal;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

public class UsersController implements CrudHandler {
    
    @Override
    public void getAll(Context ctx) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            ctx.json(mapper.getAllUsers());
        }
    }

    @Override
    public void getOne(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            ctx.json(mapper.getById(Integer.parseInt(resourceId)));
        }
    }

    @Override
    public void create(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            ctx.status(400).json(Map.of("error", "password is required"));
            return;
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);

            userMapper.insert(user);

            Account account = new Account();
            account.setUserId(user.getId());
            account.setAccountType("CHECKING");
            account.setBalance(BigDecimal.ZERO);
            accountMapper.insert(account);

            session.commit();
            ctx.status(201).json(Map.of("id", user.getId(), "username", user.getUsername()));
        }
    }


    @Override
    public void update(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = ctx.bodyAsClass(User.class);
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                ctx.status(400).json(Map.of("error", "password is required"));
                return;
            }
            user.setId(Integer.parseInt(resourceId));
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
            ctx.json(Map.of("id", user.getId(), "username", user.getUsername()));
        }
    }

    @Override
    public void delete(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(Integer.parseInt(resourceId));
            session.commit();
            ctx.json(Map.of("id", resourceId, "deleted", true));
        }
    }

    public User authenticateUser(Context ctx, String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getByUsername(username);
            return user != null && BCrypt.checkpw(password, user.getPassword()) ? user : null;
        }
    }
}