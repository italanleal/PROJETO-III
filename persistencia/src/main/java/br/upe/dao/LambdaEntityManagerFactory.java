package br.upe.dao;

import jakarta.persistence.EntityManager;

public interface LambdaEntityManagerFactory {
    EntityManager call();
}
