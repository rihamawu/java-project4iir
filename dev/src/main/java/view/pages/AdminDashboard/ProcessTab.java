package view.pages.AdminDashboard;

import controller.Pages.adminDashboard.Tabs.ProcessTabController;
import model.Organization.OrganizationProcess;
import utils.globalControllersGetter;
import view.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class ProcessTab extends JPanel {

    private JButton createButton = new JButton("Create New Process");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<OrganizationProcess> data;
    private ProcessTabController processTabController;
    private static String[] columnNamesCreateEdit = {"IdOrganization", "Name", "Description"};
    private DefaultTableModel model;
    private JTable orgProcessTable;

    public ProcessTab() {
        this.data = globalControllersGetter.organizationsController.getAllProcesses(); // Get all processes
        processTabController = new ProcessTabController(this);
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
        String[] columnNames = {"IdOrgProcess", "IdOrganization", "Name", "Description", "Actions"};

        // Create and return the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 4) is editable
                return column == 4;
            }
        };

        orgProcessTable = new JTable(model);
        orgProcessTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orgProcessTable.setRowHeight(30);
        orgProcessTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        orgProcessTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        orgProcessTable.getTableHeader().setForeground(Color.WHITE);
        orgProcessTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = orgProcessTable.getColumnModel().getColumn(4);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonEditor(orgProcessTable, processTabController));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(orgProcessTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = globalControllersGetter.organizationsController.getAllProcesses();

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (OrganizationProcess process : data) {
            Object[] rowData = {
                    process.getIdOrgProcess(),
                    process.getIdOrganization(),
                    process.getName(),
                    process.getDescription(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        orgProcessTable.repaint();
    }

    // Inner class for ButtonEditor
    // Inner class for ButtonEditor
    public class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private JTable table;
        private int currentRow;
        private Object[] rowData;
        private String idOrg; // Organization ID
        private String idProc; // Process ID

        public ButtonEditor(JTable table, ProcessTabController controller) {
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
                controller.handleEditProcess(ButtonEditor.this);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleDeleteProcess(this);
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

            // Extract the organization ID and process ID from the table
            idOrg = (String) table.getModel().getValueAt(row, 1); // Organization ID is in the second column
            idProc = (String) table.getModel().getValueAt(row, 0); // Process ID is in the first column

            return panel; // Return the panel containing the buttons
        }

        @Override
        public Object getCellEditorValue() {
            return ""; // Return an empty string (no value is edited)
        }

        public Object[] getRowData() {
            return rowData;
        }

        public String getIdOrg() {
            return idOrg;
        }

        public String getIdProc() {
            return idProc;
        }
    }
    public static void main(String[] args) {
        // Create a JFrame to display the ProcessTab
        JFrame frame = new JFrame("Process Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the ProcessTab panel to the frame
        ProcessTab processTab = new ProcessTab();
        frame.add(processTab);

        // Display the frame
        frame.setVisible(true);
    }
}