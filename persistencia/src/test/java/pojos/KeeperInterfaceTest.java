package pojos;

import org.junit.jupiter.api.*;
import br.upe.pojos.*;

import static org.junit.jupiter.api.Assertions.*;

class KeeperInterfaceTest {

    @Test
    void testCreateSession(){
        assertNotNull(KeeperInterface.createSession());
    }

    @Test
    void testCreateSubmission(){
        assertNotNull(KeeperInterface.createSubmission());
    }

    @Test
    void testCreateSubscription(){
        assertNotNull(KeeperInterface.createSubscription());
    }
    @Test
    void testCreateCommomUser(){
        assertNotNull(KeeperInterface.createCommomUser());
    }

    @Test
    void testCreateAdmin(){
        assertNotNull(KeeperInterface.createAdminUser());
    }
}
