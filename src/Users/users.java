package Users;
import java.sql.*;

public class users {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            System.out.println("Connected to database");

            int numRecords = 1000;

            for (int i = 0; i < numRecords; i++) {
                String username = "user" + i;
                String password = "user"+ i;

                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                statement.setString(1, username);
                statement.setString(2, password);

                statement.executeUpdate();
            }

            System.out.println("Random data inserted successfully");


        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
}