package br.upe.dao;

import static org.mockito.Mockito.mock;

import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JDBCSystemUserDAODiffblueTest {
    /**
     * Test {@link JDBCSystemUserDAO#JDBCSystemUserDAO(LambdaEntityManagerFactory)}.
     * <p>
     * Method under test:
     * {@link JDBCSystemUserDAO#JDBCSystemUserDAO(LambdaEntityManagerFactory)}
     */
    @Test
    @DisplayName("Test new JDBCSystemUserDAO(LambdaEntityManagerFactory)")
    void testNewJDBCSystemUserDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     JDBCGenericDAO.createEntityManager
        //     JDBCGenericDAO.entityClass

        SystemUser user = PersistenciaInterface.createSystemUser();
        // Arrange and Act
        new JDBCSystemUserDAO(mock(LambdaEntityManagerFactory.class));
    }
}
