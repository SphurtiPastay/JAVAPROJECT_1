package WaistHipCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class WaistHipCalculator extends JFrame implements ActionListener {
    private JTextField waistField, hipField, ratioField;
    private JButton calculateButton;
    private JLabel resultLabel;
    public static String username;

    public WaistHipCalculator(String username) {
        super("Waist-to-Hip Ratio Calculator");

        WaistHipCalculator.username=username;

        JLabel waistLabel = new JLabel("Waist (in cm):");
        waistField = new JTextField(5);
        JLabel hipLabel = new JLabel("Hip (in cm):");
        hipField = new JTextField(5);
        JLabel ratioLabel = new JLabel("Waist-to-Hip Ratio:");
        ratioField = new JTextField(5);
        ratioField.setEditable(false);
        resultLabel = new JLabel();

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2,10,10));
        panel.add(waistLabel);
        panel.add(waistField);
        panel.add(hipLabel);
        panel.add(hipField);
        panel.add(ratioLabel);
        panel.add(ratioField);
        panel.add(new JLabel()); // empty cell
        panel.add(resultLabel);
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);
        getContentPane().setBackground(new Color(220, 220, 220, 220));

        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double waist = Double.parseDouble(waistField.getText());
            double hip = Double.parseDouble(hipField.getText());
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO waisthip (waist, hip, username, waisthip_value) VALUES (?, ?, ?, ?)"); 

            double waisthip_value =  waist / hip;

            statement.setDouble(1, waist);
            statement.setDouble(2, hip);
            statement.setString(3, username); // use the stored username
            
            statement.setDouble(4, waisthip_value);  


            statement.executeUpdate(); 

            ratioField.setText(String.format("%.2f", waisthip_value));

            if (waisthip_value < 0.9) {
                resultLabel.setText("Low Risk");
                resultLabel.setForeground(new Color(0, 128, 0));
                //ratioField.setText(String.format("%.2f", waisthip_value) + " (Low risk)");
            } else if (waisthip_value >= 0.9 && waisthip_value < 1.0) {
                resultLabel.setText("Moderate Risk");
                resultLabel.setForeground(new Color(0, 128, 0));
                //ratioField.setText(String.format("%.2f", waisthip_value) + " (Moderate risk)");
            } else {
                resultLabel.setText("High Rsik");
                resultLabel.setForeground(new Color(0, 128, 0));
                //ratioField.setText(String.format("%.2f", waisthip_value) + " (High risk)");
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
        new WaistHipCalculator(username);
    }

    public static String getWHR() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            // Statement statement = connection.createStatement();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM waisthip WHERE username=? ORDER BY datetime DESC");
    
            statement.setString(1, username);
    
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                
                double waisthip_value = resultSet.getDouble("waisthip_value"); // calculate waisthip using cm and kg

                String waisthip_Overview;
                if (waisthip_value < 0.9) {
                    waisthip_Overview = "Low risk";
                } else if (waisthip_value >= 0.9 && waisthip_value < 1.0) {
                    waisthip_Overview = "Moderate risk";
                } else {
                    waisthip_Overview = "High risk";
                }
                return String.format("Waist-to-Hip Ratio: %.2f\nWHR Overview: %s", waisthip_value, waisthip_Overview);
            } else {
                return "No Waist-to-Hip data available for this user.";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return "Error retrieving Waist-to-Hip data.";
        }
    }
}