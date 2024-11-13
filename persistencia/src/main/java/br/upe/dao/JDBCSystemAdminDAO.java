package br.upe.dao;

import br.upe.entities.SystemAdmin;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JDBCSystemAdminDAO extends JDBCGenericDAO<SystemAdmin, Long>{
    public JDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemAdmin.class);
        this.createEntityManager = lambdaFunction;
    }
    public SystemAdmin findByCPF(String cpf) throws SystemException {
        String jpql = "SELECT u FROM SystemAdmin u WHERE u.cpf = :cpf";

        EntityManager entityManager = createEntityManager.call();
        TypedQuery<SystemAdmin> query = entityManager.createQuery(jpql, SystemAdmin.class);
        query.setParameter("cpf", cpf);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with cpf " + cpf + " not found", null);
        }
        entityManager.close();
        return query.getSingleResult();
    }

    public SystemAdmin findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM SystemAdmin u WHERE u.email = :email";
        EntityManager entityManager = createEntityManager.call();
        TypedQuery<SystemAdmin> query = entityManager.createQuery(jpql, SystemAdmin.class);
        query.setParameter("email", email);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with email " + email + " not found", null);
        }
        entityManager.close();
        return query.getSingleResult();
    }

}
