package br.upe.dao;

import br.upe.entities.Event;
import br.upe.util.LambdaEntityManagerFactory;

public class JDBCEventDAO extends JDBCGenericDAO<Event, Long> {
    public JDBCEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Event.class);
        this.createEntityManager = lambdaFunction;
    }
}