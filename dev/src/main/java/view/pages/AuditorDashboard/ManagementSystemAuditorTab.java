package view.pages.AuditorDashboard;


import model.SystemManagement.ManagementSystem;
import utils.TableConverterUtility;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagementSystemAuditorTab extends JPanel {

    private List<ManagementSystem> data;
    private DefaultTableModel model;
    private JTable managementSystemTable;

    public ManagementSystemAuditorTab() {
        this.data = ControllersGetter.organizationsController.getAllManagementSystems(); // Get all management systems
        setUpUi();
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define column names (without "Actions" column)
        String[] columnNames = {"IdManagementSystem", "IdOrganization", "Description", "Certificate"};

        // Convert data to table format
        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // No cells are editable
                return false;
            }
        };

        // Create the table
        managementSystemTable = new JTable(model);
        managementSystemTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        managementSystemTable.setRowHeight(30);
        managementSystemTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        managementSystemTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        managementSystemTable.getTableHeader().setForeground(Color.WHITE);
        managementSystemTable.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(managementSystemTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllManagementSystems();
        System.out.println("Refreshed data: " + data);

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (ManagementSystem managementSystem : data) {
            Object[] rowData = {
                    managementSystem.getIdManagementSystem(),
                    managementSystem.getIdOrganization(), // Include the organization ID
                    managementSystem.getDescription(),
                    managementSystem.getCertificate()
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        managementSystemTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the ManagementSystemAuditorTab
        JFrame frame = new JFrame("Management Systems (Auditor View)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the ManagementSystemAuditorTab panel to the frame
        ManagementSystemAuditorTab managementSystemAuditorTab = new ManagementSystemAuditorTab();
        frame.add(managementSystemAuditorTab);

        // Display the frame
        frame.setVisible(true);
    }
}
