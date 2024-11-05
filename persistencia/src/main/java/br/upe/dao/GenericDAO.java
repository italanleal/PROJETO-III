package br.upe.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    T save(T entity);  // Create or Update

    Optional<T> findById(ID id);  // Read

    List<T> findAll();  // Read All

    T update(T entity);  // Update

    void delete(T entity);  // Delete

    void deleteById(ID id);  // Delete by ID
}
