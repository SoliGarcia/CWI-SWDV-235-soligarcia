package edu.cwi;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {

    @Select(" SELECT account_id, user_id, account_type, balance FROM accounts ") List<Account> getAll();

    @Select("SELECT account_id, user_id, account_type, balance FROM accounts WHERE account_id = #{accountId}") Account getById(int accountId);

    @Select("SELECT account_id, user_id, account_type, balance FROM accounts WHERE user_id = #{userId}") List<Account> getByUserId(int userId);

    @Insert("INSERT INTO accounts (user_id, account_type, balance) VALUES (#{userId}, #{accountType}, #{balance})")
    
    @Options(useGeneratedKeys = true, keyProperty = "accountId") int insert(Account account);

    @Update("UPDATE accounts SET account_type = #{accountType}, balance = #{balance} WHERE account_id = #{accountId}") int update(Account account);

    @Update("UPDATE accounts SET balance = balance + #{amount} WHERE account_id = #{accountId}")
    int deposit(@Param("accountId") int accountId, @Param("amount") BigDecimal amount);

    @Delete("DELETE FROM accounts WHERE account_id = #{accountId}") int delete(int accountId);
}