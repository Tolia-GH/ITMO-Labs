package se.ifmo.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("DB/DB.properties")) {
            if (input == null) {
                throw new IOException("DB.properties file not found in DB directory");
            }

            Properties prop = new Properties();
            prop.load(input);

            // 从配置文件加载数据库连接信息
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

            Class.forName("org.postgresql.Driver");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}