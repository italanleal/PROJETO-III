package br.upe.dao;

import br.upe.entities.SystemUser;
import br.upe.entities.User;
import br.upe.util.LambdaEntityManagerFactory;
import br.upe.util.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.SystemException;

import java.nio.file.attribute.UserPrincipalNotFoundException;

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

    public User findByEmail(String email) throws UserNotFoundException {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        EntityManager entityManager = createEntityManager.call();
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        if(query.getSingleResult() == null){
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        return query.getSingleResult();
    }
}
