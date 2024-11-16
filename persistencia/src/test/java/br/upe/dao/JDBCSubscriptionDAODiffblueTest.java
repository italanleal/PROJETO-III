package br.upe.dao;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import br.upe.entities.Subscription;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.apache.openjpa.persistence.EntityManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

class JDBCSubscriptionDAODiffblueTest {
    /**
     * Test
     * {@link JDBCSubscriptionDAO#JDBCSubscriptionDAO(LambdaEntityManagerFactory)}.
     */
    private final Logger logger = Logger.getLogger(JDBCSubscriptionDAODiffblueTest.class.getName());
    private final JDBCSubscriptionDAO subscriptionDAO = PersistenciaInterface.createJDBCSubscriptionDAO(PersistenciaInterface.getDevelopEMF_lambda());
    @Test
    @DisplayName("Test save method for subscriptionDAO")
    void testSaveSubscription() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);

        Assertions.assertNotEquals(subscriptionDAO.findById(subscription.getId()), Optional.empty());
    }

}
