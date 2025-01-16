package view.pages.AuditorDashboard;

import model.SystemManagement.Standard.Standard;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StandardAuditorTab extends JPanel {

    private List<Standard> data = ControllersGetter.organizationsController.getAllStandards();
    private static String[] columnNamesCreateEdit = {"IdOrganization", "IdManagementSystem", "Name", "Description", "Reference"};
    private DefaultTableModel model;
    private JTable standardTable;

    public StandardAuditorTab() {
        System.out.println("The data: " + data); // Debug statement
        setUpUi();
    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define column names (without the "Actions" column)
        String[] columnNames = {"IdStandard", "IdOrganization", "IdManagementSystem", "Name", "Description", "Reference"};

        // Create the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the entire table non-editable
                return false;
            }
        };

        // Create the table
        standardTable = new JTable(model);
        standardTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        standardTable.setRowHeight(30);
        standardTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        standardTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        standardTable.getTableHeader().setForeground(Color.WHITE);
        standardTable.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(standardTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllStandards();
        System.out.println("Refreshed data: " + data); // Debug statement

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Standard standard : data) {
            Object[] rowData = {
                    standard.getIdStandard(),
                    standard.getIdOrganization(),
                    standard.getIdManagementSystem(),
                    standard.getName(),
                    standard.getDescription(),
                    standard.getReference()
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        standardTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the StandardAuditorTab
        JFrame frame = new JFrame("Standards Management (Auditor View)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the StandardAuditorTab panel to the frame
        StandardAuditorTab standardAuditorTab = new StandardAuditorTab();
        frame.add(standardAuditorTab);

        // Display the frame
        frame.setVisible(true);
    }
}