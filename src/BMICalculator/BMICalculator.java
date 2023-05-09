package BMICalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BMICalculator extends JFrame implements ActionListener {
    private JTextField heightField, weightField, bmiField;
    private JButton calculateButton;
    private JLabel resultLabel;
    private static String username;

    public BMICalculator(String username) {
        super("BMI Calculator");

        BMICalculator.username = username;

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
        resultLabel = new JLabel();

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(bmiLabel);
        panel.add(bmiField);
        panel.add(new JLabel()); // empty cell
        panel.add(resultLabel);
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bmi (height, weight, username, bmi_value) VALUES (?, ?, ?, ?)");

            double bmi_value = weight / ((height / 100) * (height / 100));

            statement.setDouble(1, height);
            statement.setDouble(2, weight);
            statement.setString(3, username);
            statement.setDouble(4, bmi_value);

            statement.executeUpdate();

            bmiField.setText(String.format("%.2f", bmi_value));

            // Update result section with BMI overview
            if (bmi_value < 18.5) {
                resultLabel.setText("Underweight");
                resultLabel.setForeground(new Color(0, 128, 0));
            } else if (bmi_value >= 18.5 && bmi_value < 25) {
                resultLabel.setText("Normal weight");
                resultLabel.setForeground(new Color(0, 128, 0));
            } else if (bmi_value >= 25 && bmi_value < 30) {
                resultLabel.setText("Overweight");
                resultLabel.setForeground(Color.ORANGE);
            } else {
                resultLabel.setText("Obese");
                resultLabel.setForeground(Color.RED);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bmi WHERE username=? ORDER BY datetime DESC");
    
            statement.setString(1, username);
    
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                // double height = resultSet.getDouble("height");
                // double weight = resultSet.getDouble("weight");
                double bmi_value = resultSet.getDouble("bmi_value"); // get BMI value from database
    
                String bmiOverview;
                if (bmi_value < 18.5) {
                    bmiOverview = "Underweight";
                } else if (bmi_value >= 18.5 && bmi_value < 25) {
                    bmiOverview = "Normal weight";
                } else if (bmi_value >= 25 && bmi_value < 30) {
                    bmiOverview = "Overweight";
                } else {
                    bmiOverview = "Obese";
                }
    
                return String.format("BMI: %.2f\nBMI Overview: %s", bmi_value, bmiOverview);                
            }
    
            return "No BMI data available for this user.";
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return "Error retrieving BMI data.";
        }
    }
}   