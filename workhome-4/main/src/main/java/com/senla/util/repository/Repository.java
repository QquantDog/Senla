package com.senla.util.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface Repository<T extends Identifiable<ID>, ID extends Serializable> {
    long count();

    T save(T entity);

    void deleteById(ID id);

    Optional<T> findById(ID id);
    Collection<T> findAll();

    boolean existsById(ID id);
}
