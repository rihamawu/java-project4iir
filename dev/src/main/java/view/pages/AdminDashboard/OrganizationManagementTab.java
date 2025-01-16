package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.OrganizationManagementTabController;
import model.Organization.Organization;
import utils.ControllersGetter;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class OrganizationManagementTab extends JPanel {

    private JButton createButton = new JButton("Create New Organization");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<Organization> data = ControllersGetter.organizationsController.getOrganizations();
    private OrganizationManagementTabController organizationManagementTabController;
    private static String[] columnNamesCreateEdit = {"Name", "Description"};
    private DefaultTableModel model;
    private JTable auditTable;

    public OrganizationManagementTab() {
        organizationManagementTabController = new OrganizationManagementTabController(this);
        setUpUi();
    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditButton() {
        return buttonsContainer.getEditButton();
    }

    public JButton getDeleteButton() {
        return buttonsContainer.getDeleteButton();
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a button panel at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241)); // Light gray background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add "Create" button
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(new Color(52, 152, 219)); // Blue color
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(createButton);

        // Add the button panel to the top of the tab
        this.add(buttonPanel, BorderLayout.NORTH);

        // Define column names
        String[] columnNames = {"IdOrganization", "Name", "Description", "Actions"};

        // Create and return the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 3) is editable
                return column == 3;
            }
        };

        auditTable = new JTable(model);
        auditTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        auditTable.setRowHeight(30);
        auditTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        auditTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        auditTable.getTableHeader().setForeground(Color.WHITE);
        auditTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = auditTable.getColumnModel().getColumn(3);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonEditor(auditTable, organizationManagementTabController));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(auditTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getOrganizations();

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Organization organization : data) {
            Object[] rowData = {
                    organization.getIdOrganization(),
                    organization.getName(),
                    organization.getDescription(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        auditTable.repaint();
    }

    // Inner class for ButtonEditor
    public class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private JTable table;
        private int currentRow;
        private Object[] rowData;
        private String id;

        public ButtonEditor(JTable table, OrganizationManagementTabController controller) {
            super(new JCheckBox());
            this.table = table;

            // Create Edit button
            editButton = new JButton("Edit");
            editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            editButton.setBackground(new Color(52, 152, 219)); // Blue color
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            editButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Create Delete button
            deleteButton = new JButton("Delete");
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            deleteButton.setBackground(new Color(231, 76, 60)); // Red color
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Add action listeners
            editButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleEditOrganization(ButtonEditor.this);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleDeleteOrganization(this);
            });

            // Create a panel to hold the buttons
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(new Color(236, 240, 241)); // Match the background color
            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row; // Store the current row index

            // Save the row data to the instance variable
            rowData = new Object[table.getColumnCount() - 1];
            for (int col = 0; col < table.getColumnCount() - 1; col++) {
                rowData[col] = table.getModel().getValueAt(row, col + 1);
            }
            id = (String) table.getModel().getValueAt(row, 0);

            return panel; // Return the panel containing the buttons
        }

        @Override
        public Object getCellEditorValue() {
            return ""; // Return an empty string (no value is edited)
        }

        public Object[] getRowData() {
            return rowData;
        }

        public String getId() {
            return id;
        }
    }

    public static void main(String[] args) {
        // Create a JFrame to display the OrganizationManagementTab
        JFrame frame = new JFrame("Organizations Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the OrganizationManagementTab panel to the frame
        OrganizationManagementTab organizationManagementTab = new OrganizationManagementTab();
        frame.add(organizationManagementTab);

        // Display the frame
        frame.setVisible(true);
    }
}