package ru.vitoliot.namornik.managers;

import ru.vitoliot.namornik.Main;
import ru.vitoliot.namornik.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    public static UserEntity selectOne(String login) throws SQLException {
        UserEntity user = null;

        try (Connection connection = Main.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from Users where login = ?;");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new UserEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
            }
            else {
                throw new SQLException(" Неверные данные");
            }
            return user;
        }

    }
}
