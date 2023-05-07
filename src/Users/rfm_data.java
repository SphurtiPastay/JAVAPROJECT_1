package Users;

import java.sql.*;
import java.util.Random;

public class rfm_data {

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javapproject", "root", "12345");
            System.out.println("Connected to database");

            int numRecords = 1000;

            for (int i = 0; i < numRecords; i++) {
                int min = 130; // minimum value of the range
                int max = 200; // maximum value of the range
                int min1 = 70; // minimum value of the range
                int max1 = 120;
                
                Random random = new Random();

                double height = random.nextInt(max - min + 1) + min; 
                double waist = random.nextInt(max1 - min1 + 1) + min1;
                String sex = random.nextBoolean() ? "M" : "F";
                String username = "user" + i;
                double rfm_value;

                if (sex.length() != 1 || (sex.charAt(0) != 'M' && sex.charAt(0) != 'F')) {
                    throw new IllegalArgumentException("Invalid sex input. Please enter 'M' or 'F'.");
                }
                if (sex.equalsIgnoreCase("M")) {
                    rfm_value = 64 - (20 * (height / waist));
                } else if (sex.equalsIgnoreCase("F")) {
                    rfm_value = 76 - (20 * (height / waist));
                } else {
                    throw new IllegalArgumentException("Invalid sex input. Please enter 'M' or 'F'.");
                }

                PreparedStatement statement = connection.prepareStatement("INSERT INTO rfm (height, waist, sex, username, rfm_value) VALUES (?, ?, ?, ?, ?)");
                statement.setDouble(1, height);
                statement.setDouble(2, waist);
                statement.setString(3, sex);
                statement.setString(4, username);
                statement.setDouble(5, rfm_value);

                statement.executeUpdate();
            }

            System.out.println("Random data inserted successfully");

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
}
