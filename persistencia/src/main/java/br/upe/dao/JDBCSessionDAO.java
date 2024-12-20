package br.upe.dao;

import br.upe.entities.Session;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public class JDBCSessionDAO extends JDBCGenericDAO<Session, Long> {
    public JDBCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Session.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
}
