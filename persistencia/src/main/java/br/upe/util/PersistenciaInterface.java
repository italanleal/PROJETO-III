package br.upe.util;

import br.upe.dao.*;
import br.upe.entities.*;

public interface PersistenciaInterface {
    public static LambdaEntityManagerFactory getDefaultEMF_lambda(){
        return DefaultEntityManagerFactory::createEntityManager;
    }
    public static void closeDefaultEMF(){
        DefaultEntityManagerFactory.close();
    }
    public static LambdaEntityManagerFactory getDevelopEMF_lambda(){
        return DevelopEntityManagerFactory::createEntityManager;
    }
    public static void closeDevelopEMF(){
        DevelopEntityManagerFactory.close();
    }
    public static Event createEvent(){
        return new Event();
    }
    public static JDBCEventDAO createJDBCEventDAO(LambdaEntityManagerFactory lambdaFunction){
        return new JDBCEventDAO(lambdaFunction);
    }
    public static Session createSession() {return new Session(); }
    public static JDBCSessionDAO createJDBCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSessionDAO(lambdaFunction);
    }
    public static SubEvent createSubEvent() {return new SubEvent(); }
    public static JDBCSubEventDAO createJDBCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubEventDAO(lambdaFunction);
    }
    public static Subscription createSubscription() {return new Subscription(); }
    public static JDBCSubscriptionDAO createJDBCSubscriptionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubscriptionDAO(lambdaFunction);
    }
    public static Submission createSubmission() {return new Submission(); }
    public static JDBCSubmissionDAO createJDBCSubmissionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSubmissionDAO(lambdaFunction);
    }
    public static SystemAdmin createSystemAdmin() {return new SystemAdmin(); }
    public static JDBCSystemAdminDAO createJDBCSystemAdminDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSystemAdminDAO(lambdaFunction);
    }
    public static SystemUser createSystemUser() {return new SystemUser(); }
    public static JDBCSystemUserDAO createJDBCSystemUserDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCSystemUserDAO(lambdaFunction);
    }
    public static Certification createCertification() {return new Certification(); }
    public static JDBCCertificationDAO createJDBCCertificationDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JDBCCertificationDAO(lambdaFunction);
    }
}
