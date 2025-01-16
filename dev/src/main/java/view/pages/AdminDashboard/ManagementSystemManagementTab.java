package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.ManagementSystemManagementTabController;
import model.SystemManagement.ManagementSystem;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonsActions;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class ManagementSystemManagementTab extends JPanel {

    private JButton createButton = new JButton("Create New Management System");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<ManagementSystem> data;
    private ManagementSystemManagementTabController managementSystemManagementTabController;
    private static String[] columnNamesCreateEdit = {"IdOrganization", "Description", "Certificate" };
    DefaultTableModel model;
    JTable managementSystemTable;

    public ManagementSystemManagementTab() {
        this.data = ControllersGetter.organizationsController.getAllManagementSystems(); // Get all management systems
        managementSystemManagementTabController = new ManagementSystemManagementTabController(this);
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
        String[] columnNames = { "IdManagementSystem", "IdOrganization", "Description", "Certificate", "Actions" };

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 4) is editable {accept event}
                return column == 4;
            }
        };

        managementSystemTable = new JTable(model);
        managementSystemTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        managementSystemTable.setRowHeight(30);
        managementSystemTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        managementSystemTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        managementSystemTable.getTableHeader().setForeground(Color.WHITE);
        managementSystemTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = managementSystemTable.getColumnModel().getColumn(4);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), managementSystemTable, managementSystemManagementTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(managementSystemTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllManagementSystems();
        System.out.println(data);
        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (ManagementSystem managementSystem : data) {
            Object[] rowData = {
                    managementSystem.getIdManagementSystem(),
                    managementSystem.getIdOrganization(), // Include the organization ID
                    managementSystem.getDescription(),
                    managementSystem.getCertificate(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = managementSystemTable.getColumnModel().getColumn(4);
        managementSystemTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(4);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonsContainer());
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), managementSystemTable, managementSystemManagementTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        managementSystemTable.addColumn(actionsColumn);

        // Repaint the table to reflect the changes
        managementSystemTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the ManagementSystemManagementTab
        JFrame frame = new JFrame("Management Systems Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the ManagementSystemManagementTab panel to the frame
        ManagementSystemManagementTab managementSystemManagementTab = new ManagementSystemManagementTab();
        frame.add(managementSystemManagementTab);

        // Display the frame
        frame.setVisible(true);
    }
}