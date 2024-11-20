package br.upe.util.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DevelopEntityManagerFactory {
    private static final EntityManagerFactory emf;
    static {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", EnvConfig.get("DB_URL"));
        properties.put("jakarta.persistence.jdbc.user", EnvConfig.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", EnvConfig.get("DB_PASSWORD"));

        emf = Persistence.createEntityManagerFactory("develop", properties);
    }

    public static EntityManager createEntityManager() {
        EntityManager em = emf.createEntityManager();
        em.setFlushMode(FlushModeType.COMMIT);
        return em;
    }
    public static void close() {
        emf.close();
    }
}
