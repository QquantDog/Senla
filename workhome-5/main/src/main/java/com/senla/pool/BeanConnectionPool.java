package com.senla.pool;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BeanConnectionPool {

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;
    private final List<ManageableConnection> pool = new ArrayList<>();

    private final ThreadLocal<ManageableConnection> threadLocalConnection = new ThreadLocal<>();

    @PostConstruct
    public void initConnectionPool() {
        try{
            int poolSize = Integer.parseInt(Objects.requireNonNull(env.getProperty("pool.size")));
            for (int i = 0; i < poolSize; i++) {
                pool.add(new ManageableConnection(dataSource.getConnection()));
            }
        }
        catch (NullPointerException e){
            System.out.println("No connection pool size field: ");
            throw new RuntimeException(e);
        }
        catch (NumberFormatException e){
            System.out.println("Error parsing connection pool size: ");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Error establishing connection with db: ");
            throw new RuntimeException(e);
        }
    }


    public void setupLocalConnection(boolean isTransactional) throws SQLException {
        synchronized (pool) {
            for (ManageableConnection mc : pool) {
                if(!mc.isActive()){
                    checkConnection(mc);
                    mc.activate(isTransactional);
                    threadLocalConnection.set(mc);
                    return;
                }
            }
        }
        throw new RuntimeException("No free connections in pool");
    }

    public ManageableConnection getLocalConnection() throws SQLException {
        if(threadLocalConnection.get() == null){
            setupLocalConnection(false);
        }
        return threadLocalConnection.get();
    }

    public void checkConnection(ManageableConnection mc) throws SQLException {
        Connection connection = mc.getConnection();
        if (connection.isClosed() && mc.isTransactional()) {throw new RuntimeException("Transactional connection is closed");}
        else if(connection.isClosed() && !mc.isTransactional()) {
            int pos = -1;
            for(int i = 0; i<pool.size(); i++) {
                if(pool.get(i) == mc){
                    pos = i;
                    break;
                }
            }
            if(pos == -1) throw new RuntimeException("Manageable Connection doesn't exist in connection pool");
            ManageableConnection mcNew = new ManageableConnection(dataSource.getConnection());
            pool.set(pos, mcNew);
        }
    }
    public void releaseLocalConnection() throws SQLException {
        ManageableConnection local = threadLocalConnection.get();
        if(local == null) throw new RuntimeException("No Local ManageableConnection found");
        local.deactivate();
        threadLocalConnection.remove();
    }

}
