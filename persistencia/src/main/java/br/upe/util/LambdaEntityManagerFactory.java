package br.upe.util;

import jakarta.persistence.EntityManager;

public interface LambdaEntityManagerFactory {
    EntityManager call();

    Object createEntityManager();
}
