package RelativeFatMassCalculator;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class RelativeFatMassCalculator extends JFrame implements ActionListener {
    private JTextField heightField, waistField, sexField, rfMField;
    private JButton calculateButton;
    public static String username;

    public RelativeFatMassCalculator(String username) {
        super("Relative Fat Mass Calculator");

        RelativeFatMassCalculator.username=username;

        // Set background color
        getContentPane().setBackground(new Color(220, 220, 220));

        // Create labels with colors
        JLabel heightLabel = new JLabel("Height (in cm):");
        heightLabel.setForeground(new Color(60, 60, 60));
        JLabel waistLabel = new JLabel("Waist (in cm):");
        waistLabel.setForeground(new Color(60, 60, 60));
        JLabel sexLabel = new JLabel("Sex (M/F):");
        sexLabel.setForeground(new Color(60, 60, 60));
        JLabel rfMLabel = new JLabel("Relative Fat Mass:");
        rfMLabel.setForeground(new Color(60, 60, 60));

        // Create text fields
        heightField = new JTextField(5);
        waistField = new JTextField(5);
        sexField = new JTextField(5);
        rfMField = new JTextField(5);
        rfMField.setEditable(false);

        // Create button with color
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setBackground(new Color(64, 128, 128));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        // Create panel with layout and add components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(waistLabel);
        panel.add(waistField);
        panel.add(sexLabel);
        panel.add(sexField);
        panel.add(rfMLabel);
        panel.add(rfMField);

        // Add panel and button to frame
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        // Set frame properties and make it visible
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double height = Double.parseDouble(heightField.getText());
            double waist = Double.parseDouble(waistField.getText());
            String sex = sexField.getText().toUpperCase();
            
            if (sex.length() != 1 || (sex.charAt(0) != 'M' && sex.charAt(0) != 'F')) {
                throw new IllegalArgumentException("Invalid sex input. Please enter 'M' or 'F'.");
            }
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");

            PreparedStatement statement = connection.prepareStatement("INSERT INTO rfm (height, waist, sex, username) VALUES (?, ?, ?, ?)"); 

            statement.setDouble(1, height);
            statement.setDouble(2, waist);
            statement.setString(3, sex);
            statement.setString(4, username); // use the stored username

            statement.executeUpdate();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rfm WHERE username='" + username + "'");
            while (resultSet.next()) {
                double dbHeight = resultSet.getDouble("height");
                double dbWaist = resultSet.getDouble("waist");
                String dbSex = resultSet.getString("sex");
                
                if (dbSex.equalsIgnoreCase("M")) {
                    double rfm = 64 - (20 * (dbHeight / dbWaist));
                    rfMField.setText(String.format("%.2f", rfm));
                } else if (dbSex.equalsIgnoreCase("F")) {
                    double rfm = 76 - (20 * (dbHeight / dbWaist));
                    rfMField.setText(String.format("%.2f", rfm));
                }
            }
            connection.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new RelativeFatMassCalculator(username);
    }

    public static String getRFM() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
    
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rfm WHERE username=?"); 
    
            statement.setString(1, username); // use the stored username
    
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double height = resultSet.getDouble("height");
                double waist = resultSet.getDouble("waist");
                String sex = resultSet.getString("sex");
                
                double rfm;
                if (sex.equalsIgnoreCase("M")) {
                    rfm = 64 - (20 * (height / waist));
                } else if (sex.equalsIgnoreCase("F")) {
                    rfm = 76 - (20 * (height / waist));
                } else {
                    throw new IllegalArgumentException("Invalid sex input. Please enter 'M' or 'F'.");
                }
                return String.format("%.2f", rfm);
            } else {
                throw new SQLException("No RFM value found for user " + username);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
}