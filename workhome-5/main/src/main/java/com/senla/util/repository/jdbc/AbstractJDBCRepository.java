package com.senla.util.repository.jdbc;


import com.senla.util.repository.Identifiable;
import com.senla.util.repository.Repository;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJDBCRepository<T extends Identifiable<ID>, ID extends Serializable> implements Repository<T, ID> {

    protected abstract Connection getConnection() throws SQLException;

    protected abstract String countSqlQuery();
    protected abstract String selectAllSqlQuery();
    protected abstract String selectByIdSqlQuery();
    protected abstract String insertSqlQuery();
    protected abstract String updateSqlQuery();
    protected abstract String deleteSqlQuery();

    @Override
    public long count() {
        try(var statement = getConnection().prepareStatement(countSqlQuery())) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T save(T entity) {
        if(entity.getId() == null) {
            try(PreparedStatement statement = getConnection().prepareStatement(insertSqlQuery())) {
                setDataToCreateQuery(entity, statement);
                statement.executeUpdate();
                return entity;
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        } else {
            try(PreparedStatement statement = getConnection().prepareStatement(updateSqlQuery())) {
                setDataToUpdateQuery(entity, statement);
                statement.executeUpdate();
                return entity;
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteById(ID id) {
        try(var statement = getConnection().prepareStatement(deleteSqlQuery())) {
            setStatementFirstPosId(statement, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try(var statement = getConnection().prepareStatement(selectByIdSqlQuery())) {
            setStatementFirstPosId(statement, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                T entity = buildObj(rs);
                return Optional.of(entity);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<T> findAll() {
        try(var statement = getConnection().prepareStatement(selectAllSqlQuery())) {
            ResultSet rs = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while(rs.next()){
                entities.add(buildObj(rs));
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(ID id) {
        try(var statement = getConnection().prepareStatement(selectByIdSqlQuery())) {
            setStatementFirstPosId(statement, id);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected abstract void setStatementFirstPosId(PreparedStatement st, ID id) throws SQLException;
    protected abstract T buildObj(ResultSet rs) throws SQLException;
    protected abstract void setDataToCreateQuery(T entity, PreparedStatement statement) throws SQLException;
    protected abstract void setDataToUpdateQuery(T entity, PreparedStatement statement) throws SQLException;
}
