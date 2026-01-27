package se.ifmo.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {
    
    public static Connection getConnection() throws SQLException {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:jboss/datasources/Lab2DS");
            return ds.getConnection();
        } catch (NamingException e) {
            throw new SQLException("Could not lookup datasource: java:jboss/datasources/Lab2DS", e);
        }
    }
}