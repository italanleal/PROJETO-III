package br.upe.util.persistencia;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class EnverDiffblueTest {
    /**
     * Test {@link Enver#get(String)}.
     */
    Dotenv env = (new Enver()).getDevelopEnv();

    @Test
    @DisplayName("Test get(DB_URL)")
    void testGetDB_URL() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(env.get("DB_URL"));
    }
    @Test
    @DisplayName("Test get(DB_USER)")
    void testGetDB_USER() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(env.get("DB_USER"));
    }
    @Test
    @DisplayName("Test get(DB_PASSWORD)")
    void testGetDB_PASSWORD() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(env.get("DB_PASSWORD"));
    }

}
