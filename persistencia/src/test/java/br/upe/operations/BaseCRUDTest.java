package br.upe.operations;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseCRUDTest {

    @Test
    void testStateDirectoryCreation() {
        new BaseCRUD();

        File stateDir = new File(".\\state");
        assertTrue(stateDir.exists() && stateDir.isDirectory(), "O diretório 'state' deveria existir");
    }
}
