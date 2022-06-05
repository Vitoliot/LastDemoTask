package ru.vitoliot.namornik.managers;

import ru.vitoliot.namornik.Main;
import ru.vitoliot.namornik.entities.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static List<ProductEntity> selectAll() throws SQLException {
        List<ProductEntity> list = new ArrayList<>();

        try (Connection connection = Main.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * from product;");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                list.add(new ProductEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getTimestamp(7).toLocalDateTime().toLocalDate()
                ));
            }
            return list;
        }
    }

    public static void insert(ProductEntity product) throws SQLException{
        try (Connection connection = Main.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `newdemo`.`product`\n" +
                    "(`Title`,\n" +
                    "`ProductType`,\n" +
                    "`Description`,\n" +
                    "`Image`,\n" +
                    "`Cost`,\n" +
                    "`RegisterDate`)" +
                    "VALUES" +
                    "(?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getType());
            statement.setString(3, product.getDesc());
            statement.setString(4, product.getImage());
            statement.setDouble(5, product.getCost());
            statement.setTimestamp(6, Timestamp.valueOf(product.getRegDate().atTime(0,0)));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                product.setID(resultSet.getInt(1));
                return;
            }
            throw new SQLException("error");
        }
    }

    public static void update(ProductEntity product) throws SQLException{
        try (Connection connection = Main.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE `newdemo`.`product`\n" +
                    "SET\n" +
                    "`Title` = ?,\n" +
                    "`ProductType` = ?,\n" +
                    "`Description` = ?,\n" +
                    "`Image` = ?,\n" +
                    "`Cost` = ?,\n" +
                    "`RegisterDate` = ?\n" +
                    "WHERE `ID` =?;");
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getType());
            statement.setString(3, product.getDesc());
            statement.setString(4, product.getImage());
            statement.setDouble(5, product.getCost());
            statement.setTimestamp(6, Timestamp.valueOf(product.getRegDate().atTime(0,0)));
            statement.setInt(7,product.getID());
            statement.executeUpdate();
        }
    }
    public static int delete(int productId) throws SQLException{
        try (Connection connection = Main.getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `newdemo`.`product` WHERE id = ?");
            statement.setInt(1, productId);
            statement.executeUpdate();
            return statement.getUpdateCount();
        }
    }
}
