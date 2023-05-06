import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import BMICalculator.BMICalculator; 
import WaistHipCalculator.WaistHipCalculator;
import RelativeFatMassCalculator.RelativeFatMassCalculator;
import BodySurfaceAreaCalculator.BodySurfaceAreaCalculator;
import CorpulenceIndexCalculator.CorpulenceIndexCalculator;

public class App extends JFrame implements ActionListener {
    private JFrame frame;

    public App() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fitness Calculator");
        frame.setSize(550, 400);
        frame.getContentPane().setBackground(Color.WHITE); 
        
        // Add title to the top of the frame
        JLabel titleLabel = new JLabel("Fitness Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH); 

       

        // Add logo to the frame
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("logo.png");
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(icon);
        logoPanel.add(logoLabel);
        frame.add(logoPanel, BorderLayout.WEST);

        Font btnFont = new Font("Arial", Font.BOLD, 18); 

        JButton btnBmi = new JButton("Calculate BMI");
        btnBmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new BMICalculator(getName());                   
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnBmi.setBackground(Color.BLUE);
        btnBmi.setForeground(Color.WHITE);
        btnBmi.setFocusPainted(false);
        btnBmi.setFont(btnFont);

        JButton btnWHR = new JButton("Calculate WHR");
        btnWHR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new WaistHipCalculator();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnWHR.setBackground(Color.BLUE);
        btnWHR.setForeground(Color.WHITE);
        btnWHR.setFocusPainted(false);
        btnWHR.setFont(btnFont);

        JButton btnRFM = new JButton("Calculate RFM");
        btnRFM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                   new RelativeFatMassCalculator();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnRFM.setBackground(Color.BLUE);
        btnRFM.setForeground(Color.WHITE);
        btnRFM.setFocusPainted(false);
        btnRFM.setFont(btnFont);

        JButton btnBSA = new JButton("Calculate BSA");
        btnBSA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new BodySurfaceAreaCalculator();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnBSA.setBackground(Color.BLUE);
        btnBSA.setForeground(Color.WHITE);
        btnBSA.setFocusPainted(false);
        btnBSA.setFont(btnFont);

        JButton btnCI = new JButton("Calculate CI");
        btnCI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CorpulenceIndexCalculator();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnCI.setBackground(Color.BLUE);
        btnCI.setForeground(Color.WHITE);
        btnCI.setFocusPainted(false);
        btnCI.setFont(btnFont);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.add(btnBmi);
        panel.add(btnWHR);
        panel.add(btnRFM);
        panel.add(btnBSA);
        panel.add(btnCI);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}