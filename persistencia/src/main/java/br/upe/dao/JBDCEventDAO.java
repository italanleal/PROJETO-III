package br.upe.dao;

import br.upe.entities.Event;

public class JBDCEventDAO extends JBDCGenericDAO<Event, Long> {
    public JBDCEventDAO(LambdaEntityManagerFactory function) {
        super(Event.class);
        this.createEntityManager = function;
    }
}
