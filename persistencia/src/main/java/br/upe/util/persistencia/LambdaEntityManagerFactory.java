package br.upe.util.persistencia;

import jakarta.persistence.EntityManager;

public interface LambdaEntityManagerFactory {
    EntityManager call();

    Object createEntityManager();
}
