package edu.cwi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void demoDepositUpdatesBalanceAndTransactionHistory()
    {
        UserAccount before = AuthController.getUser("ajones").orElseThrow();
        assertEquals("ajones", before.loginUsername());
        assertEquals("Alice Jones", before.username());

        UserAccount after = AuthController.addDemoDeposit(
            "ajones",
            new BigDecimal("12.34"),
            "test deposit"
        ).orElseThrow();

        assertEquals(
            BigDecimal.valueOf(before.balance()).add(new BigDecimal("12.34")).doubleValue(),
            after.balance(),
            0.001
        );
        assertTrue(after.transactions().containsValue("Deposited $12.34 - test deposit"));
    }
}