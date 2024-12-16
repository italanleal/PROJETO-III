package br.upe.util.persistencia;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class DefaultEntityManagerFactory implements EnvironmentConfig {
    private final EntityManagerFactory emf;
    @Setter
    private Dotenv env;

    public DefaultEntityManagerFactory() {
        accept(new Enver());
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", env.get("DB_URL_PRODUCTION"));
        properties.put("jakarta.persistence.jdbc.user", env.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", env.get("DB_PASSWORD"));

        emf = Persistence.createEntityManagerFactory("default", properties);
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    public void close() {
        emf.close();
    }

    @Override
    public void accept(Enver enver) {
        enver.setDefaultEnv(this);
    }
}
