package br.upe.dao;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.upe.entities.Submission;
import br.upe.entities.Subscription;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import jakarta.persistence.EntityManager;
import org.apache.openjpa.persistence.EntityManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
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

    @Test
    @DisplayName("Test update method for subscriptionDAO")
    public void updateSubscriptionTest() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);

        subscription.setDate(LocalDate.now().plusDays(1));
        subscriptionDAO.update(subscription);

        Assertions.assertEquals(subscription.getDate(), LocalDate.now().plusDays(1));
    }

    @Test
    @DisplayName("Test delete method for subscriptionDAO")
    public void deleteSubscriptionTest() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);

        Long id = subscription.getId();
        subscriptionDAO.delete(subscription);
        Assertions.assertEquals(subscriptionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for submissionDAO")
    public void deleteByIdSubscriptionTest() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);


        Long id = subscription.getId();
        subscriptionDAO.deleteById(id);
        Assertions.assertEquals(subscriptionDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for subscriptionDAO")
    public void findByIdSubscriptionTest() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);

        Long id = subscription.getId();
        Assertions.assertEquals(subscriptionDAO.findById(id).get(), subscription);
    }

    @Test
    @DisplayName("Test findAll method for subscriptionDAO")
    public void findAllSubscriptionTest() {
        Subscription subscription = PersistenciaInterface.createSubscription();
        subscription.setDate(LocalDate.now());
        subscriptionDAO.save(subscription);

        List<Subscription> subscriptions = subscriptionDAO.findAll();
        Assertions.assertFalse(subscriptions.isEmpty());
    }

}
