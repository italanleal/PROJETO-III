package br.upe.util.persistencia;

import br.upe.dao.*;
import br.upe.entities.*;

public interface PersistenciaInterface {
     static LambdaEntityManagerFactory getDefaultEMF_lambda(){
        return DefaultEMTFactoryWrapper.defaultEntityManagerFactory::createEntityManager;
    }
     static void closeDefaultEMF(){
         DefaultEMTFactoryWrapper.defaultEntityManagerFactory.close();
    }
     static LambdaEntityManagerFactory getDevelopEMF_lambda(){
        return DevelopEMTFactoryWrapper.developEntityManagerFactory::createEntityManager;
    }
     static void closeDevelopEMF(){
         DevelopEMTFactoryWrapper.developEntityManagerFactory.close();
    }
     static Event createEvent(){
        return new Event();
    }
     static JDBCEventDAO createJDBCEventDAO(LambdaEntityManagerFactory lambdaFunction){
        return new JDBCEventDAO(lambdaFunction);
    }
     static Session createSession() {return new Session(); }
     static JDBCSessionDAO createJDBCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSessionDAO(lambdaFunction);
    }
     static SubEvent createSubEvent() {return new SubEvent(); }
     static JDBCSubEventDAO createJDBCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubEventDAO(lambdaFunction);
    }
     static Subscription createSubscription() {return new Subscription(); }
     static JDBCSubscriptionDAO createJDBCSubscriptionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubscriptionDAO(lambdaFunction);
    }
     static Submission createSubmission() {return new Submission(); }
     static JDBCSubmissionDAO createJDBCSubmissionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubmissionDAO(lambdaFunction);
    }
     static SystemAdmin createSystemAdmin() {return new SystemAdmin(); }
    static JDBCSystemAdminDAO createJDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSystemAdminDAO(lambdaFunction);
    }
    static SystemUser createSystemUser() {return new SystemUser(); }
    static JDBCSystemUserDAO createJDBCSystemUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSystemUserDAO(lambdaFunction);
    }
    static Certification createCertification() {return new Certification(); }
    static JDBCCertificationDAO createJDBCCertificationDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCCertificationDAO(lambdaFunction);
    }
    static JDBCUserDAO createJDBCUserDAO(LambdaEntityManagerFactory lambdaFunction) {
         return new JDBCUserDAO(lambdaFunction);
    }
}
