package br.upe.dao;

import br.upe.util.persistencia.LambdaEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class JDBCGenericDAO<T, I> implements GenericDAO<T, I>{
    @PersistenceContext
    protected LambdaEntityManagerFactory createEntityManager;
    private final Class<T> entityClass;

    public JDBCGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        EntityManager em = createEntityManager.call();

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        return entity;
    }

    @Override
    public Optional<T> findById(I id) {
        EntityManager em = createEntityManager.call();
        T entity = em.find(entityClass, id);
        em.close();
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> findAll() {
        EntityManager em = createEntityManager.call();
        Query q = em.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass);
        List<T> response = q.getResultList();
        em.close();
        return response;
    }

    @Override
    public T update(T entity) {
        EntityManager em = createEntityManager.call();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    @Override
    public void delete(T entity) {
        EntityManager em = createEntityManager.call();
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteById(I id) {
        EntityManager em = createEntityManager.call();
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }
}
