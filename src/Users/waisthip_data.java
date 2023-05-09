package Users;

import java.sql.*;
import java.util.Random;

public class waisthip_data {

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            System.out.println("Connected to database");

            Random random = new Random();

            int numRecords = 1000;

            for (int i = 0; i < numRecords; i++) {
                int min = 70; // minimum value of the range
                int max = 120; // maximum value of the range
                int min1 = 70; // minimum value of the range
                int max1 = 120;

                double waist = random.nextInt(max - min + 1) + min;
                double hip = random.nextInt(max1 - min1 + 1) + min1;
                String username = "user" + i;

                PreparedStatement statement = connection.prepareStatement("INSERT INTO waisthip (waist, hip, username, waisthip_value) VALUES (?, ?, ?, ?)");
                statement.setDouble(1, waist);
                statement.setDouble(2, hip);
                statement.setString(3, username);
                statement.setDouble(4, waist/hip);

                statement.executeUpdate();
            }

            System.out.println("Random data inserted successfully");

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
}
