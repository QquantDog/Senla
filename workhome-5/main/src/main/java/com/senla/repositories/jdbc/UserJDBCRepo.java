package com.senla.repositories.jdbc;

import com.senla.models.address.Address;
import com.senla.models.user.User;
import com.senla.util.repository.Repository;
import com.senla.util.repository.jdbc.AbstractJDBCRepository;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserJDBCRepo extends AbstractJDBCRepository<User, Long> implements Repository<User, Long> {
    @Override
    protected Connection getConnection() {
        return null;
    }

    @Override
    protected String countSqlQuery() {
        return "";
    }

    @Override
    protected String selectAllSqlQuery() {
        return "";
    }

    @Override
    protected String selectByIdSqlQuery() {
        return "";
    }

    @Override
    protected String insertSqlQuery() {
        return "";
    }

    @Override
    protected String updateSqlQuery() {
        return "";
    }

    @Override
    protected String deleteSqlQuery() {
        return "";
    }

    @Override
    protected void setStatementFirstPosId(PreparedStatement st, Long aLong) throws SQLException {

    }

    @Override
    protected User buildObj(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected void setDataToCreateQuery(User entity, PreparedStatement statement) throws SQLException {

    }

    @Override
    protected void setDataToUpdateQuery(User entity, PreparedStatement statement) throws SQLException {

    }
}
