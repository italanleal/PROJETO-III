package br.upe.dao;

import br.upe.entities.SystemAdmin;
import br.upe.util.LambdaEntityManagerFactory;

public class JDBCSystemAdminDAO extends JDBCGenericDAO<SystemAdmin, Long>{
    public JDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemAdmin.class);
        this.createEntityManager = lambdaFunction;
    }

}
