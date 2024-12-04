package br.upe.util.persistencia;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class DevelopEntityManagerFactory implements EnvironmentConfig {
    private final EntityManagerFactory emf;
    @Setter
    private Dotenv env;

    public DevelopEntityManagerFactory() {
        accept(new Enver());
        Map<String, String> properties = new HashMap<>();

        properties.put("jakarta.persistence.jdbc.url", env.get("DB_URL"));
        properties.put("jakarta.persistence.jdbc.user", env.get("DB_USER"));
        properties.put("jakarta.persistence.jdbc.password", env.get("DB_PASSWORD"));

        emf = Persistence.createEntityManagerFactory("develop", properties);
    }

    public EntityManager createEntityManager() {
        EntityManager em = emf.createEntityManager();
        em.setFlushMode(FlushModeType.COMMIT);
        return em;
    }
    public void close() {
        emf.close();
    }

    @Override
    public void accept(Enver enver) {
        enver.setDevelopEnv(this);
    }
}
