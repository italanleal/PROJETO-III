package br.upe.util;

import br.upe.dao.JBDCEventDAO;
import br.upe.dao.JBDCSessionDAO;
import br.upe.dao.LambdaEntityManagerFactory;
import br.upe.entities.Event;
import br.upe.entities.Session;

public interface PersistenciaInterface {
    public static LambdaEntityManagerFactory getDefaultEntityManagerFactory(){
        return DefaultEntityManagerFactory::createEntityManager;
    }
    public static LambdaEntityManagerFactory getDevelopEntityManagerFactory(){
        return DevelopEntityManagerFactory::createEntityManager;
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
}
