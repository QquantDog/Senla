package com.senla.aspect;

import com.senla.pool.BeanConnectionPool;
import com.senla.pool.ManageableConnection;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Order(1)
@Component
public class Wrapper {

    @Autowired
    private BeanConnectionPool pool;

    public void returnConnection(Connection connection) {}

    @Around("@annotation(com.senla.annotation.MyTransaction)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        pool.setupLocalConnection(true);
        ManageableConnection mc = pool.getLocalConnection();
        Connection connection = mc.getConnection();

        try {
            Object result = joinPoint.proceed();
            connection.commit();
            return result;
        } catch (Exception e) {
            System.err.println("Error executing transaction - rollback");
            System.out.println(e.getMessage());
            connection.rollback();
        } finally {
            pool.releaseLocalConnection();
        }
        return null;
    }

}
