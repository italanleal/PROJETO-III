package br.upe.dao;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JBDCSessionDAODiffblueTest {
    /**
     * Test {@link JBDCSessionDAO#JBDCSessionDAO(LambdaEntityManagerFactory)}.
     * <p>
     * Method under test:
     * {@link JBDCSessionDAO#JBDCSessionDAO(LambdaEntityManagerFactory)}
     */
    @Test
    @DisplayName("Test new JBDCSessionDAO(LambdaEntityManagerFactory)")
    void testNewJBDCSessionDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     JBDCGenericDAO.createEntityManager
        //     JBDCGenericDAO.entityClass

        // Arrange and Act
        new JBDCSessionDAO(mock(LambdaEntityManagerFactory.class));
    }
}
