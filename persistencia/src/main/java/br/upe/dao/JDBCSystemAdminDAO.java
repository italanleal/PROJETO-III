package br.upe.dao;

import br.upe.entities.SystemAdmin;
import br.upe.entities.SystemUser;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.SystemException;
import br.upe.util.persistencia.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JDBCSystemAdminDAO extends JDBCGenericDAO<SystemAdmin, Long>{
    public JDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(SystemAdmin.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }


}
