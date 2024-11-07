package br.upe.util;

import br.upe.dao.JBDCEventDAO;
import br.upe.dao.JBDCSessionDAO;
import br.upe.dao.JBDCSubEventDAO;
import br.upe.dao.LambdaEntityManagerFactory;
import br.upe.entities.Event;
import br.upe.entities.Session;
import br.upe.entities.SubEvent;

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
    public static JBDCEventDAO createJBDCEventDAO(LambdaEntityManagerFactory lambdaFunction){
        return new JBDCEventDAO(lambdaFunction);
    }
    public static Session createSession() {return new Session(); }
    public static JBDCSessionDAO createJBDCSessionDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JBDCSessionDAO(lambdaFunction);
    }
    public static SubEvent createSubEvent() {return new SubEvent(); }
    public static JBDCSubEventDAO createJBDCSubEventDAO(LambdaEntityManagerFactory lambdaFunction) {
        return new JBDCSubEventDAO(lambdaFunction);
    }
}
