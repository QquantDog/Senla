package com.senla.services;

import java.util.Collection;
import java.util.Optional;

public interface GenericService<T, ID> {

    Collection<T> findAll();
    Optional<T> findById(ID id);
    boolean existsById(ID id);

    T save(T entity);

    void deleteById(ID id);

    long count();
}
