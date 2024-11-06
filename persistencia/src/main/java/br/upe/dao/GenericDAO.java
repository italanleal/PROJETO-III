package br.upe.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, I> {
    T save(T entity);  // Create or Update

    Optional<T> findById(I id);  // Read

    List<T> findAll();  // Read All

    T update(T entity);  // Update

    void delete(T entity);  // Delete

    void deleteById(I id);  // Delete by ID
}
