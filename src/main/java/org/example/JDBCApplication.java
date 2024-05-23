package org.example;

import domain.User;
import org.example.util.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class JDBCApplication {
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String AGE = "age";


    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(
                ApplicationProperties.DB_URL,
                ApplicationProperties.DB_USERNAME,
                ApplicationProperties.DB_PASSWORD
        )


        ) {
            String userName = "sepehr";
            getUserByUserName(connection, userName);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getUserByUserName(Connection connection, String userName) throws SQLException {


        String sql = """
                                Select * From users
                                where username = ?
                """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();

        connection.setAutoCommit(true);
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User tempUser = new User(resultSet.getString(JDBCApplication.FIRST_NAME),
                    resultSet.getString(JDBCApplication.LAST_NAME),
                    resultSet.getString(JDBCApplication.USER_NAME),
                    resultSet.getInt(JDBCApplication.AGE),
                    resultSet.getString(JDBCApplication.PASSWORD)
            );
            users.add(tempUser);

        }
        users.forEach(System.out::println);
    }

}
