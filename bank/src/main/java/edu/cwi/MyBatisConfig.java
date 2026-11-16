package edu.cwi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class MyBatisConfig {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environmentValue(
            "BANK_DB_URL",
            "jdbc:mysql://localhost:3306/online_bank"
        ));
        hikariConfig.setUsername(environmentValue("BANK_DB_USERNAME", "root"));
        String password = System.getenv("BANK_DB_PASSWORD");
        if (password != null) {
            hikariConfig.setPassword(password);
        }
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSource dataSource = new HikariDataSource(hikariConfig);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        configuration.addMapper(AccountMapper.class);
        configuration.addMapper(TransactionMapper.class);
        configuration.setMapUnderscoreToCamelCase(true);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private static String environmentValue(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}