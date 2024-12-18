package br.upe.dao;

import br.upe.entities.Userd;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JDBCUserDAO extends JDBCGenericDAO<Userd, Long> {

    public JDBCUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Userd.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }

    public Userd findByCPF(String cpf) throws SystemException {
        String jpql = "SELECT u FROM Userd u WHERE u.cpf = :cpf";
        TypedQuery<Userd> query = em.createQuery(jpql, Userd.class);
        query.setParameter("cpf", cpf);
        List<Userd> result = query.getResultList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("Userd with cpf " + cpf + " not found", null);
        }
        return result.getFirst();
    }

    public Userd findByEmail(String email) throws SystemException {
        String jpql = "SELECT u FROM Userd u WHERE u.email = :email";
        TypedQuery<Userd> query = em.createQuery(jpql, Userd.class);
        query.setParameter("email", email);
        List<Userd> result = query.getResultList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("Userd with email " + email + " not found", null);
        }
        return result.getFirst();
    }
}
