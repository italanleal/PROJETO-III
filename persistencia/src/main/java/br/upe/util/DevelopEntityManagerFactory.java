package br.upe.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
        return emf.createEntityManager();
    }
    public static void close() {
        emf.close();
    }
}
