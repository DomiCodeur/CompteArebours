package CompteAr.backend;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class connectDB {

    private static Connection connection = null;
    // récupérer les variables d'environnement du gitignore
    @Value("${spring.datasource.url}")
    private static String url;

    @Value("${spring.datasource.username}")
    private static String username;

    @Value("${spring.datasource.password}")
    private static String password;

public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // utiliser DataSourceConfiguration
        Connection connection = connectDB.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
