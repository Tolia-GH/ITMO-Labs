package se.ifmo.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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

    private static DataSource dataSource;

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
//        try {
//            // 使用 JNDI 查找 WildFly 配置的数据源
//            InitialContext ctx = new InitialContext();
//            dataSource = (DataSource) ctx.lookup("java:/jdbc/Lab2DS");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
        //return dataSource.getConnection();
    }
}