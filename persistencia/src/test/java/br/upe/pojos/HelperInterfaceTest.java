package br.upe.pojos;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelperInterfaceTest {

    private TestClass source;
    private TestClass destination;

    @BeforeEach
    public void setUp() {
        source = new TestClass("João Mario", 30);
        destination = new TestClass("", 0); // Valores vazios para testar o checkout
    }

    @Test
    void testIsGetter() throws NoSuchMethodException {
        Method validGetter = TestClass.class.getMethod("getName");
        Method invalidGetter = TestClass.class.getMethod("setName", String.class);

        assertTrue(HelperInterface.isGetter(validGetter), "Método válido não foi reconhecido como getter.");
        assertFalse(HelperInterface.isGetter(invalidGetter), "Método inválido foi reconhecido como getter.");
    }

    @Test
    void testGetSetterName() {
        String getterName = "getName";
        String expectedSetterName = "setName";
        assertEquals(expectedSetterName, HelperInterface.getSetterName(getterName), "O nome do setter não está correto.");
    }

    @Test
    void testCheckout() {
        assertEquals("", destination.getName(), "O nome de destino não deveria estar preenchido inicialmente.");
        assertEquals(0, destination.getAge(), "A idade de destino não deveria estar preenchida inicialmente.");

        HelperInterface.checkout(source, destination);

        assertEquals("João Mario", destination.getName(), "O nome de destino não foi copiado corretamente.");
        assertEquals(30, destination.getAge(), "A idade de destino não foi copiada corretamente.");
    }

    static class TestClass {
        private String name;
        private int age;

        public TestClass(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
