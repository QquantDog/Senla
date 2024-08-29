package com.senla.services;

import com.senla.repositories.AbstractRepository;
import com.senla.repositories.Identifiable;


import java.util.Collection;
import java.util.Optional;

public abstract class AbstractLongIdGenericService<T extends Identifiable<Long>> implements GenericService<T, Long> {

    protected AbstractRepository<T, Long> abstractRepository;

    @Override
    public Collection<T> findAll() {
        return abstractRepository.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return abstractRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return abstractRepository.existsById(id);
    }

    @Override
    public T save(T entity) {
        return abstractRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        abstractRepository.deleteById(id);
    }

    @Override
    public long count() {
        return abstractRepository.count();
    }
}
