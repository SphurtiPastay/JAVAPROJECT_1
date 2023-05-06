package BodySurfaceAreaCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BodySurfaceAreaCalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bsaField;
    private JButton calculateButton;
    private static String username; // added field to store username


    public BodySurfaceAreaCalculator(String username) {
        super("BodySurfaceArea Calculator"); 

        BodySurfaceAreaCalculator.username = username;

        BodySurfaceAreaCalculator.username = username;

        // set up labels and text fields
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightField = new JTextField(5);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightField = new JTextField(5);
        JLabel bsaLabel = new JLabel("Body Surface Area (in m²):");
        bsaField = new JTextField(5);
        bsaField.setEditable(false);

        // set up calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);

        // set up panel and add components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10,10));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bsaLabel);
        panel.add(bsaField);
        panel.setBackground(new Color(240,240,240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20,20, 20));

        // add panel and calculate button to content pane
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        // set background color
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        heightLabel.setForeground(Color.BLACK);
        weightLabel.setForeground(Color.BLACK);
        bsaLabel.setForeground(Color.BLACK);

        // set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 220, 220));
        pack();
        setVisible(true);
    }

    public BodySurfaceAreaCalculator() {
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            // Statement statement = connection.createStatement();
            // statement.execute("INSERT INTO bsa (height, weight) VALUES (" + height + ", " + weight + ")"); 
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bsa (height, weight, username) VALUES (?, ?, ?)"); 

            statement.setDouble(1, height);
            statement.setDouble(2, weight);
            statement.setString(3, username); 

            statement.executeUpdate();


            ResultSet resultSet = statement.executeQuery("SELECT * FROM bsa WHERE username='" + username + "'");
            while (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height");
                double dbWeight = resultSet.getDouble("weight");
                double bsa = calculateBSA(dbHeight, dbWeight);
                bsaField.setText(String.format("%.2f", bsa));
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

    private double calculateBSA(double height, double weight) {
        return Math.sqrt((height * weight) / 3600.0);
    }

    public static void main(String[] args) {
        new BodySurfaceAreaCalculator(username);
    }

    public static String getBSA() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bsa WHERE username='" + username + "'");
    
            if (resultSet.next()) {
                double height = resultSet.getDouble("height");
                double weight = resultSet.getDouble("weight");
                double bsa = Math.sqrt((height * weight) / 3600.0); // calculate BSA using cm and kg
                return String.format("Height: %.2f cm\nWeight: %.2f kg\nBSA: %.2f m²", height, weight, bsa);
            } else {
                return "No BSA data available for this user.";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return "Error retrieving BSA data.";
        }
    }
    
}