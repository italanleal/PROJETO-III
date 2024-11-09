package br.upe.operations;

import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.provider.CsvSource;

class HasherInterfaceTest {

    @ParameterizedTest
    @CsvSource({
            "hello, 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
            "'', e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    })
    void testHashWithCsvSource(String input, String expectedHash) {
        String actualHash = HasherInterface.hash(input);
        assertEquals(expectedHash, actualHash);
    }
}


