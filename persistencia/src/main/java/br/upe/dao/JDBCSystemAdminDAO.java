package br.upe.dao;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JDBCSystemAdminDAO extends JDBCGenericDAO<SystemAdmin, Long>{
    public JDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemAdmin.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
    public SystemAdmin findByCPF(String cpf) throws SystemException {
        String jpql = "SELECT u FROM SystemAdmin u WHERE u.cpf = :cpf";
        TypedQuery<SystemAdmin> query = em.createQuery(jpql, SystemAdmin.class);
        query.setParameter("cpf", cpf);
        List<SystemAdmin> result = query.getResultList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("User with cpf " + cpf + " not found", null);
        }
        return result.getFirst();
    }

    public SystemAdmin findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM SystemAdmin u WHERE u.email = :email";
        TypedQuery<SystemAdmin> query = em.createQuery(jpql, SystemAdmin.class);
        query.setParameter("email", email);
        List<SystemAdmin> result = query.getResultList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found", null);
        }
        return result.getFirst();
    }

}
