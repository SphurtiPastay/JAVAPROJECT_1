package FitnessReportGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import BMICalculator.BMICalculator;
import BodySurfaceAreaCalculator.BodySurfaceAreaCalculator;
import CorpulenceIndexCalculator.CorpulenceIndexCalculator;
import RelativeFatMassCalculator.RelativeFatMassCalculator;
import WaistHipCalculator.WaistHipCalculator;

public class FitnessReportGUI extends JFrame implements ActionListener {

    public static String username;
    private JTextArea reportArea;
    private JButton saveButton, displayButton;

    public FitnessReportGUI(String username) {

        super("Fitness Report");
        FitnessReportGUI.username = username;

        // Create the report area
        reportArea = new JTextArea(20, 40);
        reportArea.setEditable(false);

        // Create the save and display buttons
        saveButton = new JButton("Save Report");
        saveButton.addActionListener(this);
        displayButton = new JButton("Display Report");
        displayButton.addActionListener(this);

        // Add the components to the frame
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(displayButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame properties
        setTitle("Fitness Report");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String report = "Fitness Report for " + username + "\n\n";
            report += "BMI: " + BMICalculator.getBMI() + "\n";
            report += "WHR: " + WaistHipCalculator.getWHR() + "\n";
            report += "RFM: " + RelativeFatMassCalculator.getRFM() + "\n";
            report += "BSA: " + BodySurfaceAreaCalculator.getBSA() + "\n";
            report += "CI: " + CorpulenceIndexCalculator.getCI() + "\n";

            if (e.getSource() == saveButton) {
                // Save the report to a file
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    PrintWriter writer = new PrintWriter(file);
                    writer.print(report);
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Report saved successfully");
                }
            } else if (e.getSource() == displayButton) {
                // Display the report in a dialog box
                reportArea.setText(report);
                JOptionPane.showMessageDialog(this, new JScrollPane(reportArea));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
      
        new FitnessReportGUI(username);
       
    }
}
