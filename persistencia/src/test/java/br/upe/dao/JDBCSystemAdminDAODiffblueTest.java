package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.util.LambdaEntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JDBCSystemAdminDAODiffblueTest {
    /**
     * Test
     * {@link JDBCSystemAdminDAO#JDBCSystemAdminDAO(LambdaEntityManagerFactory)}.
     * <p>
     * Method under test:
     * {@link JDBCSystemAdminDAO#JDBCSystemAdminDAO(LambdaEntityManagerFactory)}
     */
    @Test
    @DisplayName("Test new JDBCSystemAdminDAO(LambdaEntityManagerFactory)")
    void testNewJDBCSystemAdminDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     JDBCGenericDAO.createEntityManager
        //     JDBCGenericDAO.entityClass

        // Arrange and Act
        new JDBCSystemAdminDAO(mock(LambdaEntityManagerFactory.class));
    }
}
