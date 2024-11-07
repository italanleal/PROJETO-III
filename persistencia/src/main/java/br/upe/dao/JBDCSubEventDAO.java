package br.upe.dao;

import br.upe.entities.SubEvent;

public class JBDCSubEventDAO extends JBDCGenericDAO<SubEvent, Long> {
    public JBDCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SubEvent.class);
        this.createEntityManager = lambdaFunction;
    }
}
