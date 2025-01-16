package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.StandardTabController;
import model.SystemManagement.Standard.Standard;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonsActions;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class StandardTab extends JPanel {

    private JButton createButton = new JButton("Create New Standard");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<Standard> data = ControllersGetter.organizationsController.getAllStandards();
    private StandardTabController standardTabController;
    private static String[] columnNamesCreateEdit = {"IdOrganization", "IdManagementSystem", "Name", "Description", "Reference"};
    DefaultTableModel model;
    JTable standardTable;

    public StandardTab() {
        standardTabController = new StandardTabController(this);
        System.out.println("the data :" + data);
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
        String[] columnNames = {"IdStandard", "IdOrganization", "IdManagementSystem", "Name", "Description", "Reference", "Actions"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 6) is editable {accept event}
                return column == 6;
            }
        };

        standardTable = new JTable(model);
        standardTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        standardTable.setRowHeight(30);
        standardTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        standardTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        standardTable.getTableHeader().setForeground(Color.WHITE);
        standardTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = standardTable.getColumnModel().getColumn(6);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), standardTable, standardTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(standardTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllStandards();
        System.out.println(data);
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
                    standard.getReference(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = standardTable.getColumnModel().getColumn(6);
        standardTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(6);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonsContainer());
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), standardTable, standardTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        standardTable.addColumn(actionsColumn);

        // Repaint the table to reflect the changes
        standardTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the StandardTab
        JFrame frame = new JFrame("Standards Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the StandardTab panel to the frame
        StandardTab standardTab = new StandardTab();
        frame.add(standardTab);

        // Display the frame
        frame.setVisible(true);
    }
}