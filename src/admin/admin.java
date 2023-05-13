package admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class admin extends JFrame implements ActionListener {

   
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private String currentUser;

    public admin(String currentUser) {
        try {
            // Create a connection to the database
            System.out.println("Trying to connect to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "12345");
            System.out.println("Connected to database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }  

        this.currentUser = currentUser;

        // Create a menu bar with options for each table
        JMenuBar menuBar = new JMenuBar();
        JMenu bmiMenu = new JMenu("BMI");
        
        // Add "Select All" menu item
        JMenuItem bmiSelectAll = new JMenuItem("Select All");
        bmiSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bmi");
                    ResultSet rs = stmt.executeQuery();
                    JTable table = new JTable(buildTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("BMI Data");
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        
            private TableModel buildTableModel(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
        
                // Get number of columns
                int columnCount = metaData.getColumnCount();
        
                // Create vector to hold column names and data
                Vector<String> columnNames = new Vector<>();
                Vector<Vector<Object>> data = new Vector<>();
        
                // Add column names to vector
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }
        
                // Add data rows to vector
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getObject(i));
                    }
                    data.add(row);
                }
        
                // Create DefaultTableModel object with data and column names
                return new DefaultTableModel(data, columnNames);
            }
        
        });
        bmiMenu.add(bmiSelectAll);
        
        // Add "Save" menu item
        JMenuItem bmiSave = new JMenuItem("Save");
        bmiSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bmi");
                    ResultSet rs = stmt.executeQuery();
        
                    // Create a file chooser dialog to get the file path from the user
                    JFileChooser fileChooser = new JFileChooser();
                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
        
                        // Write the query results to the file
                        FileWriter writer = new FileWriter(fileToSave);
                        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
        
                        // Write column names to file
                        for (int i = 1; i <= columnCount; i++) {
                            writer.write(metaData.getColumnName(i) + ",");
                        }
                        writer.write("\n");
        
                        // Write data rows to file
                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                Object value = rs.getObject(i);
                                writer.write(value.toString() + ",");
                            }
                            writer.write("\n");
                        }
        
                        writer.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        bmiMenu.add(bmiSave);
        
        menuBar.add(bmiMenu);
        

        // JMenuBar menuBar = new JMenuBar();
        JMenu bsaMenu = new JMenu("Body Surface Area");
        JMenuItem bsaSelectAll = new JMenuItem("Select All");
        bsaSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bsa");
                    ResultSet rs = stmt.executeQuery();
                    JTable table = new JTable(buildTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Body Surface Area Data");
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            private TableModel buildTableModel(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            
                // Get number of columns
                int columnCount = metaData.getColumnCount();
            
                // Create a Vector to hold the column names
                Vector<String> columnNames = new Vector<String>();
            
                // Get the column names
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnLabel(column));
                }
            
                // Create a Vector to hold the data
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            
                // Get the data
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    for (int column = 1; column <= columnCount; column++) {
                        row.add(rs.getObject(column));
                    }
                    data.add(row);
                }
            
                // Create and return the TableModel
                return new DefaultTableModel(data, columnNames);
            }
            
        });
        bsaMenu.add(bsaSelectAll);
        menuBar.add(bsaMenu);
    

        // JMenuBar menuBar = new JMenuBar();
        JMenu ciMenu = new JMenu("Corpulence Index");
        JMenuItem ciSelectAll = new JMenuItem("Select All");
        ciSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ci");
                    ResultSet rs = stmt.executeQuery();
                    JTable table = new JTable(buildTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Corpulence Index Data");
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            private TableModel buildTableModel(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            
                // Get number of columns
                int columnCount = metaData.getColumnCount();
            
                // Create a Vector to hold the column names
                Vector<String> columnNames = new Vector<String>();
            
                // Get the column names
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnLabel(column));
                }
            
                // Create a Vector to hold the data
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            
                // Get the data
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    for (int column = 1; column <= columnCount; column++) {
                        row.add(rs.getObject(column));
                    }
                    data.add(row);
                }
            
                // Create and return the TableModel
                return new DefaultTableModel(data, columnNames);
            }
            
        });
        ciMenu.add(ciSelectAll);
        menuBar.add(ciMenu);

        // JMenuBar menuBar = new JMenuBar+");
        JMenu rfmMenu = new JMenu("Relative Mass Index");
        JMenuItem rfmSelectAll = new JMenuItem("Select All");
        rfmSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rfm");
                    ResultSet rs = stmt.executeQuery();
                    JTable table = new JTable(buildTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Relative Fat Mass");
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            private TableModel buildTableModel(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            
                // Get number of columns
                int columnCount = metaData.getColumnCount();
            
                // Create a Vector to hold the column names
                Vector<String> columnNames = new Vector<String>();
            
                // Get the column names
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnLabel(column));
                }
            
                // Create a Vector to hold the data
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            
                // Get the data
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    for (int column = 1; column <= columnCount; column++) {
                        row.add(rs.getObject(column));
                    }
                    data.add(row);
                }
            
                // Create and return the TableModel
                return new DefaultTableModel(data, columnNames);
            }
            
        });
        rfmMenu.add(rfmSelectAll);
        menuBar.add(rfmMenu);
 

        // JMenuBar menuBar = new JMenuBar();
        JMenu waisthipMenu = new JMenu("Waist to Hip Ratio ");
        JMenuItem waisthipSelectAll = new JMenuItem("Select All");
        waisthipSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM waisthip");
                    ResultSet rs = stmt.executeQuery();
                    JTable table = new JTable(buildTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Waist to Hip Ratio");
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.setSize(500, 500);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            private TableModel buildTableModel(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            
                // Get number of columns
                int columnCount = metaData.getColumnCount();
            
                // Create a Vector to hold the column names
                Vector<String> columnNames = new Vector<String>();
            
                // Get the column names
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnLabel(column));
                }
            
                // Create a Vector to hold the data
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            
                // Get the data
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    for (int column = 1; column <= columnCount; column++) {
                        row.add(rs.getObject(column));
                    }
                    data.add(row);
                }
            
                // Create and return the TableModel
                return new DefaultTableModel(data, columnNames);
            }
            
        });
        waisthipMenu.add(waisthipSelectAll);
        menuBar.add(waisthipMenu);


        setJMenuBar(menuBar);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }  

    
    public admin() {
    }


    private boolean checkAdminPermission() {
        // Query the user table to check if the current user has admin permission
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT Admin FROM user WHERE username = ?");
            stmt.setString(1, currentUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boolean Admin = rs.getBoolean("Admin");
                return Admin;
            } else {
                // User not found in database, deny access
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your action listener code here
    }

    public static void main(String[] args) {
         String currentUser = "sphurti"; // Replace with actual username
        new admin(currentUser);

    }

}
