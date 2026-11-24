package edu.cwi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

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
    public void demoAccountsStoreAndVerifyBcryptPasswordHashes()
    {
        UserAccount alice = AuthController.getUser("ajones").orElseThrow();
        UserAccount bob = AuthController.getUser("bjones").orElseThrow();

        assertNotEquals("weak", alice.id());
        assertNotEquals("weak", bob.id());
        assertTrue(BCrypt.checkpw("weak", alice.id()));
        assertTrue(BCrypt.checkpw("weak", bob.id()));
        assertTrue(AuthController.authenticateDemoUser("ajones", "weak").isPresent());
        assertTrue(AuthController.authenticateDemoUser("bjones", "weak").isPresent());
        assertFalse(AuthController.authenticateDemoUser("ajones", "wrong").isPresent());
    }
}