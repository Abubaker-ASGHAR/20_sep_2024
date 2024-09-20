package org.example;


import java.sql.*;

public class databasemanager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/example2users";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Ts123456!"; // Replace with your password

    private Connection connection;

    // Establishes a connection to the database
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        System.out.println("Connected to the Postgresql database.");
    }

    public void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +  // Change AUTO_INCREMENT to SERIAL
                "name VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'users' created successfully.");
        }
    }


    // Inserts a user into the table
    public void insertUser(String name, String email) throws SQLException {
        String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Inserted user: " + name + ", " + email);
        }
    }

    // Retrieves and prints all users
    public void getAllUsers() throws SQLException {
        String querySQL = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {
            System.out.println("Users in the database:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(id + ": " + name + " - " + email);
            }
        }
    }

    // Closes the database connection
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.err.println("Error while disconnecting: " + e.getMessage());
            }
        }
    }
}
