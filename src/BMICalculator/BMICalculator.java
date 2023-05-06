package BMICalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BMICalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bmiField;
    private JButton calculateButton;
    private static String username; // added field to store username

    public BMICalculator(String username) { // added username parameter to constructor
        super("BMI Calculator");

        BMICalculator.username = username; // store username in the field

        // set up labels and text fields
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightLabel.setForeground(new Color(60, 60, 60));
        heightField = new JTextField(5);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightLabel.setForeground(new Color(60, 60, 60));
        weightField = new JTextField(5);
        JLabel bmiLabel = new JLabel("BMI:");
        bmiLabel.setForeground(new Color(60, 60, 60));
        bmiField = new JTextField(5);
        bmiField.setEditable(false);

        // set up calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        // set up panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bmiLabel);
        panel.add(bmiField);
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // add panel and calculate button to content pane
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        // set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 220, 220));
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText()); // In cm
            double weight = Double.parseDouble(weightField.getText()); // In kg 
    
            //System.out.println(username);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bmi (height, weight, username) VALUES (?, ?, ?)"); 
    
            statement.setDouble(1, height);
            statement.setDouble(2, weight);
            statement.setString(3, username); // use the stored username
    
            statement.executeUpdate();
    
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bmi WHERE username='" + username + "'");
           
            if (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height") / 100; // convert cm to m
                double dbWeight = resultSet.getDouble("weight"); // In kg
    
                System.out.println(dbHeight);
                System.out.println(dbWeight);
                
                double bmi = dbWeight / (dbHeight * dbHeight); // use m and kg
                
                
                bmiField.setText(String.format("%.2f", bmi));
            }
            connection.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    

    public static void main(String[] args) {
        new BMICalculator(username);
    }

    public static String getBMI() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bmi WHERE username=? ORDER BY datetime DESC LIMIT 1");
    
            statement.setString(1, username);
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                double height = resultSet.getDouble("height");
                double weight = resultSet.getDouble("weight");
                double bmi = weight / (height * height); // calculate BMI using cm and kg
                return String.format("Height: %.2f cm\nWeight: %.2f kg\nBMI: %.2f", height, weight, bmi);
            } else {
                return "No BMI data available for this user.";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return "Error retrieving BMI data.";
        }
    }
    
}    

