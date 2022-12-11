package CompteAr.backend.User;
import CompteAr.backend.connectDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManagerDAO {

    public static void registerUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connectDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
    }

    public static User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connectDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
        return null;
    }


}
