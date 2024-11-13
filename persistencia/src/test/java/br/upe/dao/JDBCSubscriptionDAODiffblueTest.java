package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.util.persistencia.LambdaEntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JDBCSubscriptionDAODiffblueTest {
    /**
     * Test
     * {@link JDBCSubscriptionDAO#JDBCSubscriptionDAO(LambdaEntityManagerFactory)}.
     * <p>
     * Method under test:
     * {@link JDBCSubscriptionDAO#JDBCSubscriptionDAO(LambdaEntityManagerFactory)}
     */
    @Test
    @DisplayName("Test new JDBCSubscriptionDAO(LambdaEntityManagerFactory)")
    void testNewJDBCSubscriptionDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     JDBCGenericDAO.createEntityManager
        //     JDBCGenericDAO.entityClass

        // Arrange and Act
        new JDBCSubscriptionDAO(mock(LambdaEntityManagerFactory.class));
    }
}
