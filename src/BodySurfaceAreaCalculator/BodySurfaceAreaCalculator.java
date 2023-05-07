package BodySurfaceAreaCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BodySurfaceAreaCalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bsaField;
    private JButton calculateButton;
    private static String username;

    public BodySurfaceAreaCalculator(String username) {
        super("Body Surface Area Calculator");

        BodySurfaceAreaCalculator.username = username;

        JLabel heightLabel = new JLabel("Height (in cm):");
        heightLabel.setForeground(new Color(60, 60, 60));
        heightField = new JTextField(5);
        JLabel weightLabel = new JLabel("Weight (in kg):");
        weightLabel.setForeground(new Color(60, 60, 60));
        weightField = new JTextField(5);
        JLabel bsaLabel = new JLabel("BSA (in m²):");
        bsaLabel.setForeground(new Color(60, 60, 60));
        bsaField = new JTextField(5);
        bsaField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bsaLabel);
        panel.add(bsaField);
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 220, 220));
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bsa (height, weight, username, bsa_value) VALUES (?, ?, ?, ?)");
    
            double bsa_value = calculateBSA(height, weight);
    
            statement.setDouble(1, height);
            statement.setDouble(2, weight);
            statement.setString(3, username);
            statement.setDouble(4, bsa_value);
    
            statement.executeUpdate();
    
            bsaField.setText(String.format("%.2f", bsa_value));
    
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bsa WHERE username=? ORDER BY datetime DESC");
    
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                double bsa_value = result.getDouble("bsa_value");
                connection.close();
                return String.format("%.2f", bsa_value);
            } else {
                connection.close();
                return "No BSA record found.";
            }
    
        } catch (ClassNotFoundException ex) {
            return "Error: " + ex.getMessage();
        } catch (SQLException ex) {
            return "Error: " + ex.getMessage();
        }
    }

}
    
