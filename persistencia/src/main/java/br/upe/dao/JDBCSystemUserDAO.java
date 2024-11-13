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
    }
    public User findByCPF(String cpf) throws UserNotFoundException {
        String jpql = "SELECT u FROM User u WHERE u.cpf = :cpf";

        EntityManager entityManager = createEntityManager.call();
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("cpf", cpf);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with cpf " + cpf + " not found");
        }
        return query.getSingleResult();
    }

    public SystemUser findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM SystemUser u WHERE u.email = :email";
        EntityManager entityManager = createEntityManager.call();
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with email " + email + " not found", null);
        }
        return query.getSingleResult();
    }
}
