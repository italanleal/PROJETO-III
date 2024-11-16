package br.upe.dao;

import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JDBCSystemUserDAO extends JDBCGenericDAO<SystemUser, Long>{
    public JDBCSystemUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemUser.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
    public SystemUser findByCPF(String cpf) throws SystemException {
        String jpql = "SELECT u FROM SystemUser u WHERE u.cpf = :cpf";

        EntityManager entityManager = createEntityManager.call();
        TypedQuery<SystemUser> query = entityManager.createQuery(jpql, SystemUser.class);
        query.setParameter("cpf", cpf);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with cpf " + cpf + " not found", null);
        } entityManager.close();

        return query.getSingleResult();
    }

    public SystemUser findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM SystemUser u WHERE u.email = :email";
        EntityManager entityManager = createEntityManager.call();
        TypedQuery<SystemUser> query = entityManager.createQuery(jpql, SystemUser.class);
        query.setParameter("email", email);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with email " + email + " not found", null);
        }
        entityManager.close();
        return query.getSingleResult();
    }
}
