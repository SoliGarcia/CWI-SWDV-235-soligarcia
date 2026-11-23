package edu.cwi;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface TransactionMapper {

    @Select("SELECT transaction_id, account_id, transaction_type, amount, description, created_at FROM transactions") List<Transaction> getAll();

    @Select("SELECT transaction_id, account_id, transaction_type, amount, description, created_at FROM transactions WHERE transaction_id = #{transactionId}") Transaction getById(long transactionId);

    @Select("SELECT transaction_id, account_id, transaction_type, amount, description, created_at FROM transactions WHERE account_id = #{accountId} ORDER BY created_at DESC") List<Transaction> getByAccountId(int accountId);

    @Insert("INSERT INTO transactions (account_id, transaction_type, amount, description) VALUES (#{accountId}, #{transactionType}, #{amount}, #{description})")
    
    @Options(useGeneratedKeys = true, keyProperty = "transactionId") int insert(Transaction transaction);
}