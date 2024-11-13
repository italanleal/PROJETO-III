package br.upe.dao;

import br.upe.entities.Subscription;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public class JDBCSubscriptionDAO extends JDBCGenericDAO<Subscription, Long>{
    public JDBCSubscriptionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Subscription.class);
        this.createEntityManager = lambdaFunction;
    }
}
