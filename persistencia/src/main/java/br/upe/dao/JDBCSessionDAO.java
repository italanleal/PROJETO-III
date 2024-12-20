package br.upe.dao;

import br.upe.entities.Session;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JDBCSessionDAO extends JDBCGenericDAO<Session, Long> {
    public JDBCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Session.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }

    public List<Session> findByEvent(Long eventId) {
        TypedQuery<Session> query = em.createQuery("SELECT s FROM Session s WHERE s.event.id = :eventId", Session.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}
