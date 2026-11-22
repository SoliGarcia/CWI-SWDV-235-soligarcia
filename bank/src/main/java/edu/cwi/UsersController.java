package edu.cwi;

import java.math.BigDecimal;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import org.apache.ibatis.session.SqlSession;

import edu.cwi.MyBatisConfig;
import edu.cwi.User;
import edu.cwi.UserMapper;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

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
	try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {

		UserMapper userMapper = session.getMapper(UserMapper.class);
		AccountMapper accountMapper = session.getMapper(AccountMapper.class);
		TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);

		// deserialize the body json
		User user = ctx.bodyAsClass(User.class);

		BigDecimal deposit = BigDecimal.valueOf(5.00);

		// hash the user password
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		// insert the user
		userMapper.insert(user);

		// insert an account for the new user
		Account account = new Account();

		account.setUserId(user.getId());
		account.setBalance(deposit);

		accountMapper.insert(account);

		// insert the opening transaction
		Transaction transaction = new Transaction();

		transaction.setAccountId(account.getId());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setAmount(deposit);
		transaction.setDescription("Opening deposit.");

		transactionMapper.insert(transaction);

		// commit the session
		session.commit();

		// return the new user data
		ctx.json(user);
	}
}

    @Override
    public void update(Context ctx, String resourceId) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            User user = ctx.bodyAsClass(User.class);
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
            ctx.json(user);
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
	try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
		UserMapper mapper = session.getMapper(UserMapper.class);
		User user = mapper.getByUsername(username);

		if(user != null) {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return user;
			}
		}
		
		return null;
	}
}

}
