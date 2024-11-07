package br.upe.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnvConfigDiffblueTest {
    /**
     * Test {@link EnvConfig#get(String)}.
     */
    @Test
    @DisplayName("Test get(DB_URL)")
    void testGetDB_URL() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(EnvConfig.get("DB_URL"));
    }
    @Test
    @DisplayName("Test get(DB_USER)")
    void testGetDB_USER() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(EnvConfig.get("DB_USER"));
    }
    @Test
    @DisplayName("Test get(DB_PASSWORD)")
    void testGetDB_PASSWORD() {
        // Arrange, Act and Assert
        Assertions.assertNotNull(EnvConfig.get("DB_PASSWORD"));
    }

}
