package br.upe.dao;

import br.upe.entities.Event;

public class JBDCEventDAO extends JBDCGenericDAO<Event, Long> {
    public JBDCEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Event.class);
        this.createEntityManager = lambdaFunction;
    }
}
