package br.upe.dao;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JDBCSystemUserDAO extends JDBCGenericDAO<SystemUser, Long>{
    public JDBCSystemUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemUser.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
    public SystemUser findByCPF(String cpf) throws SystemException {
        String jpql = "SELECT u FROM SystemUser u WHERE u.cpf = :cpf";

        TypedQuery<SystemUser> query = em.createQuery(jpql, SystemUser.class);
        query.setParameter("cpf", cpf);
        List<SystemUser> result = query.getResultList();

        if (result.isEmpty()) {
            throw new UserNotFoundException("User with cpf " + cpf + " not found", null);
        }
        return result.getFirst();
    }

    public SystemUser findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM SystemUser u WHERE u.email = :email";
        TypedQuery<SystemUser> query = em.createQuery(jpql, SystemUser.class);
        query.setParameter("email", email);
        List<SystemUser> result = query.getResultList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found", null);
        }
        return result.getFirst();
    }
}
