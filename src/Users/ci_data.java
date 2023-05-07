package Users;

import java.sql.*;
import java.util.Random;

public class ci_data {

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            System.out.println("Connected to database");

            int numRecords = 1000;

            for (int i = 0; i < numRecords; i++) {
                int min = 130; // minimum value of the range
                int max = 200; // maximum value of the range
                int min1 = 45; // minimum value of the range
                int max1 = 100;
                
                Random random = new Random();

                double height = random.nextInt(max - min + 1) + min; 
                double weight = random.nextInt(max1 - min1 + 1) + min1;
                String username = "user" + i;

                PreparedStatement statement = connection.prepareStatement("INSERT INTO ci (weight, height, username, ci_value) VALUES (?, ?, ?, ?)");
                statement.setDouble(1, weight);
                statement.setDouble(2, height);
                statement.setString(3, username);
                statement.setDouble(4, weight / Math.pow(height / 100.0, 3));

                statement.executeUpdate();
            }

            System.out.println("Random data inserted successfully");

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
}
