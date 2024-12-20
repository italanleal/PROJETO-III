package br.upe.dao;

import br.upe.entities.SubEvent;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JDBCSubEventDAO extends JDBCGenericDAO<SubEvent, Long> {
    public JDBCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SubEvent.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }

    public List<SubEvent> findBySession(Long sessionId) {
        TypedQuery<SubEvent> query = em.createQuery("SELECT sub FROM SubEvent sub WHERE sub.session.id = :sessionId", SubEvent.class);
        query.setParameter("sessionId", sessionId);
        return query.getResultList();
    }
}
