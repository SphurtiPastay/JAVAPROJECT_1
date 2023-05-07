package CorpulenceIndexCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class CorpulenceIndexCalculator extends JFrame implements ActionListener {
    private JTextField weightField, heightField, corpulenceIndexField;
    private JButton calculateButton;
    public static String username; 

    public CorpulenceIndexCalculator(String username) {
        super("Corpulence Index Calculator");

        CorpulenceIndexCalculator.username=username;

        // Set background color
        getContentPane().setBackground(new Color(120, 220, 220));

        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightLabel.setForeground(new Color(60, 60, 60)); // Set label color
        weightField = new JTextField(5);
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightLabel.setForeground(new Color(60, 60, 60)); // Set label color
        heightField = new JTextField(5);
        JLabel corpulenceIndexLabel = new JLabel("Corpulence Index:");
        corpulenceIndexLabel.setForeground(new Color(60, 60, 60)); // Set label color
        corpulenceIndexField = new JTextField(5);
        corpulenceIndexField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.setBackground(new Color(64, 128, 128)); // Set button color
        calculateButton.setForeground(Color.WHITE); // Set button text color
        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240)); // Set panel background color
        panel.setLayout(new GridLayout(3, 2, 10,10));
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(corpulenceIndexLabel);
        panel.add(corpulenceIndexField);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        // Set frame properties
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ci (height, weight, username, ci_value) VALUES (?, ?, ?, ?)");
    
            double ci_value = weight / Math.pow(height / 100.0, 3);
    
            statement.setDouble(1, height);
            statement.setDouble(2, weight);
            statement.setString(3, username);
            statement.setDouble(4, ci_value);
    
            statement.executeUpdate();
    
            corpulenceIndexField.setText(String.format("%.2f", ci_value));
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
        new CorpulenceIndexCalculator(username);
    }

    public static String getCI() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ci WHERE username=? ORDER BY datetime DESC");
    
            statement.setString(1, username);
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                
                double ci_value = resultSet.getDouble("ci_value"); // get CI value from database
                return String.format("%.2f",ci_value);
            }
    
            return "No CI data available for this user.";
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return "Error retrieving CI data.";
        }
    }
    

}
    


