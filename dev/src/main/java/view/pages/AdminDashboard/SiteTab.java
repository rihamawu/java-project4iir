package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.SiteTabController;
import model.Organization.Site;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonsActions;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class SiteTab extends JPanel {

    private JButton createButton = new JButton("Create New Site");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<Site> data;
    private SiteTabController siteTabController;
    private static String[] columnNamesCreateEdit = {"IdOrganization", "Name", "Address", "Description"};
    DefaultTableModel model;
    JTable siteTable;

    public SiteTab() {
        this.data = ControllersGetter.organizationsController.getAllSites(); // Get all sites

        siteTabController = new SiteTabController(this);
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
        String[] columnNames = {"IdSite", "IdOrganization", "Name", "Address", "Description", "Actions"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 5) is editable {accept event}
                return column == 5;
            }
        };

        siteTable = new JTable(model);
        siteTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        siteTable.setRowHeight(30);
        siteTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        siteTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        siteTable.getTableHeader().setForeground(Color.WHITE);
        siteTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = siteTable.getColumnModel().getColumn(5);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), siteTable, siteTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(siteTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllSites();
        System.out.println(data);
        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Site site : data) {
            Object[] rowData = {
                    site.getIdSite(),
                    site.getIdOrganization(), // Include the organization ID
                    site.getName(),
                    site.getAddress(),
                    site.getDescription(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = siteTable.getColumnModel().getColumn(5);
        siteTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(5);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonsContainer());
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), siteTable, siteTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        siteTable.addColumn(actionsColumn);

        // Repaint the table to reflect the changes
        siteTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the SiteTab
        JFrame frame = new JFrame("Sites Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the SiteTab panel to the frame
        SiteTab siteTab = new SiteTab();
        frame.add(siteTab);

        // Display the frame
        frame.setVisible(true);
    }
}