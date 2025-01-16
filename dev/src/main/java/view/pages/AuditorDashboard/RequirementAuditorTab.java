package view.pages.AuditorDashboard;


import model.SystemManagement.Requirement;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RequirementAuditorTab extends JPanel {

    private List<Requirement> data;
    private static String[] columnNamesCreateEdit = {
            "IdOrganization", "IdManagementSystem", "Description", "Reference", "Name"
    };
    private DefaultTableModel model;
    private JTable requirementTable;

    public RequirementAuditorTab() {
        this.data = ControllersGetter.organizationsController.getAllRequirements(); // Get all requirements
        System.out.println("Fetched data: " + data); // Debug statement
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
        String[] columnNames = {
                "IdOtherRequirement", "IdOrganization", "IdManagementSystem", "Description", "Reference", "Name"
        };

        // Create the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the entire table non-editable
                return false;
            }
        };

        // Create the table
        requirementTable = new JTable(model);
        requirementTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        requirementTable.setRowHeight(30);
        requirementTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        requirementTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        requirementTable.getTableHeader().setForeground(Color.WHITE);
        requirementTable.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(requirementTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllRequirements();
        System.out.println("Refreshed data: " + data); // Debug statement

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Requirement requirement : data) {
            Object[] rowData = {
                    requirement.getIdRequirement(),
                    requirement.getIdOrganization(),
                    requirement.getIdManagementSystem(),
                    requirement.getDescription(),
                    requirement.getReference(),
                    requirement.getName()
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        requirementTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the RequirementAuditorTab
        JFrame frame = new JFrame("Requirements Management (Auditor View)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the RequirementAuditorTab panel to the frame
        RequirementAuditorTab requirementAuditorTab = new RequirementAuditorTab();
        frame.add(requirementAuditorTab);

        // Display the frame
        frame.setVisible(true);
    }
}