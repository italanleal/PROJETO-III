package br.upe.util.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DefaultEntityManagerFactory {
    private static final EntityManagerFactory emf;
    static {
        EnvConfig env = new EnvConfig();
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", env.get("DB_URL_PRODUCTION"));
        properties.put("jakarta.persistence.jdbc.user", env.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", env.get("DB_PASSWORD"));

        emf = Persistence.createEntityManagerFactory("default", properties);
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    public static void close() {
        emf.close();
    }
}
