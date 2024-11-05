package br.upe.dao;

import br.upe.entities.Event;

import java.util.UUID;

public class JBDCEventDAO extends JBDCGenericDAO<Event, Long> {
    public JBDCEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Event.class);
        this.createEntityManager = lambdaFunction;
    }
}
