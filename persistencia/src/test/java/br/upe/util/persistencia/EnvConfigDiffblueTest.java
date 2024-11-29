package br.upe.util.persistencia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnvConfigDiffblueTest {
    /**
     * Test {@link EnvConfig#get(String)}.
     */
    EnvConfig env = new EnvConfig(true);
    
    @Test
    @DisplayName("Test get(DB_URL)")
    void testGetDB_URL() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(envg.get("DB_URL"));
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
