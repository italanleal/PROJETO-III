package br.upe.dao;

import br.upe.entities.SubEvent;
import br.upe.util.LambdaEntityManagerFactory;

public class JDBCSubEventDAO extends JDBCGenericDAO<SubEvent, Long> {
    public JDBCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SubEvent.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
}
