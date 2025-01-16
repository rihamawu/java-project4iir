package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.RequirementTabController;
import model.SystemManagement.Requirement;
import utils.ControllersGetter;
import view.components.ButtonsActions;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class RequirementManagementTab extends JPanel {

    private JButton createButton = new JButton("Create New Management System");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<Requirement> data;
    private RequirementTabController RequirementManagementTabController;
    private static String[] columnNamesCreateEdit = {
            "IdOrganization", "IdManagementSystem", "Description", "Reference", "Name"
    };
    DefaultTableModel model;
    JTable requirementTable;

    public RequirementManagementTab() {
        this.data = ControllersGetter.organizationsController.getAllRequirements(); // Get all management systems
        System.out.println("Fetched data: " + data); // Debug statement
        RequirementManagementTabController = new RequirementTabController(this);
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
        String[] columnNames = {
                "IdOtherRequirement", "IdOrganization", "IdManagementSystem", "Description", "Reference", "Name",  "Actions"
        };

        // Create and return the table model
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 5) is editable {accept event}
                return column == 6;
            }
        };

        requirementTable = new JTable(model);
        requirementTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        requirementTable.setRowHeight(30);
        requirementTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        requirementTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        requirementTable.getTableHeader().setForeground(Color.WHITE);
        requirementTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = requirementTable.getColumnModel().getColumn(6);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), requirementTable, RequirementManagementTabController.getIButtonEditorEventsHandler()));

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
                    requirement.getName(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = requirementTable.getColumnModel().getColumn(6);
        requirementTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(6);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonsContainer());
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), requirementTable, RequirementManagementTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        requirementTable.addColumn(actionsColumn);

        // Repaint the table to reflect the changes
        requirementTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the RequirementTab
        JFrame frame = new JFrame("Management Systems Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the RequirementTab panel to the frame
        RequirementManagementTab requirementManagementTab = new RequirementManagementTab();
        frame.add(requirementManagementTab);

        // Display the frame
        frame.setVisible(true);
    }
}