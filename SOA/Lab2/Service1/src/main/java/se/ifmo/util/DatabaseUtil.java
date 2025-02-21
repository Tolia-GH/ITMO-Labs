package se.ifmo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String url = "jdbc:postgresql://localhost:5432/studs?currentSchema=s336184";
    private static final String user = "s336184";
    private static final String password = "Di2oaLyDd20js6Ox";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
