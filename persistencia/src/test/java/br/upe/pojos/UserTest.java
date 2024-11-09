package br.upe.pojos;


import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private ConcreteUser user;

    @BeforeEach
    public void setUp() {
        user = new ConcreteUser();
    }

    @Test
    void testSetNameAndGetName() {
        user.setName("João da Silva");
        assertEquals("João da Silva", user.getName(), "O nome não foi configurado corretamente.");
    }

    @Test
    void testSetEmailAndGetEmail() {
        user.setEmail("joao.silva@email.com");
        assertEquals("joao.silva@email.com", user.getEmail(), "O email não foi configurado corretamente.");
    }

    @Test
    void testSetPasswordAndGetPassword() {
        user.setPassword("senha123");
        assertEquals("senha123", user.getPassword(), "A senha não foi configurada corretamente.");
    }

    @Test
    void testSetUuidAndGetUuid() {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        assertEquals(uuid, user.getUuid(), "O UUID não foi configurado corretamente.");
    }

    @Test
    void testAddSubscriptionAndGetSubscriptions() {
        Subscription sub1 = new Subscription();
        user.addSubscription(sub1);
        assertTrue(user.getSubscriptions().contains(sub1), "A assinatura não foi adicionada corretamente.");
    }

    @Test
    void testIsAdmin() {
        assertFalse(user.isAdmin(), "O usuário não deve ser administrador por padrão.");
    }

}
