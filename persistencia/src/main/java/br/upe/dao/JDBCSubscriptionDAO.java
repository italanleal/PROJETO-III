package br.upe.dao;

import br.upe.entities.Subscription;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JDBCSubscriptionDAO extends JDBCGenericDAO<Subscription, Long> {
    public JDBCSubscriptionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Subscription.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }

    public List<Subscription> findByEvent(Long eventId) {
        TypedQuery<Subscription> query = em.createQuery("SELECT sub FROM Subscription sub WHERE sub.event.id = :eventId", Subscription.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}
