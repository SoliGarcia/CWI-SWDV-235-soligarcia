package edu.cwi;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
    @Select("SELECT account_id, user_id, account_type, balance FROM accounts WHERE user_id = #{userId} ORDER BY account_id")
    List<Account> getByUserId(int userId);

    @Insert("INSERT INTO accounts (user_id, account_type, balance) VALUES (#{userId}, #{accountType}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId")
    int insert(Account account);
}
