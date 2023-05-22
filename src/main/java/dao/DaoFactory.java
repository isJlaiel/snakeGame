package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DaoConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/snakeTable", "root", "new_password");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(this);
    }
}