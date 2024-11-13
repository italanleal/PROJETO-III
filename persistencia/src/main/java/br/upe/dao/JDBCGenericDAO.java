package br.upe.dao;

import br.upe.util.LambdaEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class JDBCGenericDAO<T, I> implements GenericDAO<T, I>{
    @PersistenceContext
    protected LambdaEntityManagerFactory createEntityManager;
    protected EntityManager em;
    private final Class<T> entityClass;

    public JDBCGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        return entity;
    }

    @Override
    public Optional<T> findById(I id) {
        T entity = em.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> findAll() {
        Query q = em.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass);
        return q.getResultList();
    }

    @Override
    public T update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(I id) {
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        em.remove(entity);
        em.getTransaction().commit();
    }
}
