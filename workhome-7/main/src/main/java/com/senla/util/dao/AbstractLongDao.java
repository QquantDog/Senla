package com.senla.util.dao;


import com.senla.exceptions.DaoException;
import com.senla.util.Identifiable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractLongDao<T extends Identifiable<Long>> implements GenericDao<T, Long> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLongDao.class);

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> clazz;

    protected abstract void init();


    @Override
    public Optional<T> findById(Long id) {
        try {
            return Optional.ofNullable(em.find(clazz, id));
        } catch (Throwable e) {
            logger.error("Error fetching entity by id: {}", e.getMessage(), e);
            throw new DaoException("Error fetching by id: " + clazz.getSimpleName());
        }
    }

    @Override
    public List<T> findAll() {
        try{
            return (List<T>) em.createQuery("from " + clazz.getName()).getResultList();
        } catch (Throwable e){
            logger.error("Error fetching all: {}", e.getMessage(), e);
            throw new DaoException("Error fetching all: " + clazz.getSimpleName());
        }
    }

    @Override
    public T create(T entity) {
        try{
            em.persist(entity);
            return entity;
        } catch (Exception e){
            logger.error("Error creating entity: {}", e.getMessage(), e);
            throw new DaoException("Error creating entity");
        }
    }

    @Override
    public T update(T entity) {
        try{
            em.merge(entity);
            return entity;
        } catch (Exception e){
            logger.error("Error updating entity: {}", e.getMessage(), e);
            throw new DaoException("Error updating entity");
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            T entityToDel = em.find(clazz, id);
            em.remove(entityToDel);
        } catch (Exception e){
            logger.error("Error deleting entity: {}", e.getMessage(), e);
            throw new DaoException("Error deleting entity");
        }
//        возможно стоит re-throw new RuntimeException
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return em.find(clazz, id) != null;
        } catch (Exception e) {
            logger.error("Error fetching entity: {}", e.getMessage(), e);
            throw new DaoException("Error fetching entity");
        }
    }

    @Override
    public long count() {
        return -42;
    }
}