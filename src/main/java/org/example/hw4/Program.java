package org.example.hw4;

/*
Создайте базу данных (например, SchoolDB).
В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
Настройте Hibernate для работы с вашей базой данных.
Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        String url = "jdbc:mysql://localhost:3307";
        String user = "root";
        String password = "password";

        try(Connection connection = DriverManager.getConnection(url, user, password)){

            createDatabase(connection);
            System.out.println("Database created successfully");

            useDatabase(connection);
            System.out.println("Use database successfully");

            createTable(connection);
            System.out.println("Create table successfully");

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //region Вспомогательные методы

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL =  "CREATE DATABASE IF NOT EXISTS SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL =  "USE SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    //endregion

}