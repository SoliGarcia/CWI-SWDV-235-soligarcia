package edu.cwi;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TransactionMapper {
    @Select("SELECT t.transaction_id, t.account_id, t.transaction_type, t.amount, t.description, t.created_at " +
            "FROM transactions t JOIN accounts a ON a.account_id = t.account_id " +
            "WHERE a.user_id = #{userId} ORDER BY t.created_at DESC")
    List<Transaction> getByUserId(int userId);

    @Select("SELECT t.transaction_id, t.account_id, t.transaction_type, t.amount, t.description, t.created_at " +
            "FROM transactions t JOIN accounts a ON a.account_id = t.account_id " +
            "WHERE t.account_id = #{accountId} AND a.user_id = #{userId} " +
            "ORDER BY t.created_at DESC")
    List<Transaction> getByAccountId(@Param("accountId") int accountId, @Param("userId") int userId);

    @Select("SELECT t.transaction_id, t.account_id, t.transaction_type, t.amount, t.description, t.created_at " +
            "FROM transactions t JOIN accounts a ON a.account_id = t.account_id " +
            "WHERE t.transaction_id = #{transactionId} AND a.user_id = #{userId}")
    Transaction getByIdForUser(@Param("transactionId") long transactionId, @Param("userId") int userId);
}
