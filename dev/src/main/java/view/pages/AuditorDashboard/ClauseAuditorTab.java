package view.pages.AuditorDashboard;

import model.SystemManagement.Standard.Clause;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClauseAuditorTab extends JPanel {

    private List<Clause> data = ControllersGetter.organizationsController.getAllClauses();
    private static String[] columnNamesCreateEdit = {"IdOrganization", "IdManagementSystem", "IdStandard", "Name", "Description", "Reference"};
    private DefaultTableModel model;
    private JTable clauseTable;

    public ClauseAuditorTab() {
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
        String[] columnNames = {"IdClause", "IdOrganization", "IdManagementSystem", "IdStandard", "Name", "Description", "Reference"};

        // Create the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the entire table non-editable
                return false;
            }
        };

        // Create the table
        clauseTable = new JTable(model);
        clauseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clauseTable.setRowHeight(30);
        clauseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        clauseTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        clauseTable.getTableHeader().setForeground(Color.WHITE);
        clauseTable.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(clauseTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllClauses();
        System.out.println("Refreshed data: " + data); // Debug statement

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Clause clause : data) {
            Object[] rowData = {
                    clause.getIdClause(),
                    clause.getIdOrganization(),
                    clause.getIdManagementSystem(),
                    clause.getIdStandard(),
                    clause.getName(),
                    clause.getDescription(),
                    clause.getReference()
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        clauseTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the ClauseAuditorTab
        JFrame frame = new JFrame("Clauses Management (Auditor View)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the ClauseAuditorTab panel to the frame
        ClauseAuditorTab clauseAuditorTab = new ClauseAuditorTab();
        frame.add(clauseAuditorTab);

        // Display the frame
        frame.setVisible(true);
    }
}