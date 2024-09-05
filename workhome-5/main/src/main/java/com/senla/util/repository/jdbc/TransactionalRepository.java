package com.senla.util.repository.jdbc;

import com.senla.pool.BeanConnectionPool;
import com.senla.util.repository.Identifiable;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public abstract class TransactionalRepository<T extends Identifiable<ID>, ID extends Serializable> extends AbstractJDBCRepository<T, ID> {


    protected BeanConnectionPool connectionPool;

    private void releaseIfNotTransactional() throws SQLException {
        connectionPool.releaseLocalConnection();
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return connectionPool.getLocalConnection().getConnection();
    }

    @Override
    @SneakyThrows
    public long count() {
        long result = super.count();

        releaseIfNotTransactional();
        return result;
    }

    @Override
    @SneakyThrows
    public T save(T entity) {
        T result = super.save(entity);

        releaseIfNotTransactional();
        return result;
    }

    @Override
    @SneakyThrows
    public void deleteById(ID id) {
        super.deleteById(id);

        releaseIfNotTransactional();
    }

    @Override
    @SneakyThrows
    public Optional<T> findById(ID id) {
        Optional<T> result = super.findById(id);

        releaseIfNotTransactional();
        return result;
    }

    @Override
    @SneakyThrows
    public Collection<T> findAll() {
        Collection<T> result = super.findAll();

        releaseIfNotTransactional();
        return result;
    }

    @Override
    @SneakyThrows
    public boolean existsById(ID id) {
        boolean result = super.existsById(id);

        releaseIfNotTransactional();
        return result;
    }
}
