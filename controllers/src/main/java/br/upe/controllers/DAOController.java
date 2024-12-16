package br.upe.controllers;

import br.upe.dao.*;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import br.upe.util.persistencia.PersistenciaInterface;

public class DAOController {
    public final JDBCEventDAO eventDAO;
    public final JDBCSessionDAO sessionDAO;
    public final JDBCSubEventDAO subEventDAO;
    public final JDBCSubmissionDAO submissionDAO;
    public final JDBCSubscriptionDAO subscriptionDAO;
    public final JDBCCertificationDAO certificationDAO;
    public final JDBCSystemUserDAO systemUserDAO;
    public final JDBCSystemAdminDAO systemAdminDAO;


    public DAOController(LambdaEntityManagerFactory lambdaFunction){
        eventDAO = PersistenciaInterface.createJDBCEventDAO(lambdaFunction);
        sessionDAO = PersistenciaInterface.createJDBCSessionDAO(lambdaFunction);
        subEventDAO = PersistenciaInterface.createJDBCSubEventDAO(lambdaFunction);
        submissionDAO = PersistenciaInterface.createJDBCSubmissionDAO(lambdaFunction);
        subscriptionDAO = PersistenciaInterface.createJDBCSubscriptionDAO(lambdaFunction);
        certificationDAO = PersistenciaInterface.createJDBCCertificationDAO(lambdaFunction);
        systemUserDAO = PersistenciaInterface.createJDBCSystemUserDAO(lambdaFunction);
        systemAdminDAO = PersistenciaInterface.createJDBCSystemAdminDAO(lambdaFunction);
    }

}