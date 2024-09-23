package com.senla.pool;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;

@Getter
public class ManageableConnection {
    private final Connection connection;
    private boolean isActive = false;
    @Getter
    @Setter
    private boolean isTransactional = false;

    public ManageableConnection(Connection connection) {
        this.connection = connection;
    }

    public void activate(boolean isTransactional) throws SQLException {
        if(isActive) throw new IllegalStateException("Connection is already active");
        synchronized (this) {
//            TR -> autoCommit false; isTransactional = true
//         notTR -> autoCommit true;  isTransactional = false
            if(isTransactional) {connection.setAutoCommit(false);}
            this.isTransactional = isTransactional;
            isActive = true;
        }
    }
    public void deactivate() throws SQLException {
        if(!isActive) throw new IllegalStateException("Connection is not active");
        synchronized (this) {
            connection.setAutoCommit(true);
            isTransactional = false;
            isActive = false;
        }
    }
}
