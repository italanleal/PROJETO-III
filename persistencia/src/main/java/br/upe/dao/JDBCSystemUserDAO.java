package br.upe.dao;

import br.upe.entities.SystemUser;
import br.upe.util.LambdaEntityManagerFactory;

public class JDBCSystemUserDAO extends JDBCGenericDAO<SystemUser, Long>{
    public JDBCSystemUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemUser.class);
        this.createEntityManager = lambdaFunction;
        this.em = createEntityManager.call();
    }
}
