package br.upe.dao;

import br.upe.entities.Session;

public class JBDCSessionDAO extends JBDCGenericDAO<Session, Long> {
    public JBDCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Session.class);
        this.createEntityManager = lambdaFunction;
    }
}
