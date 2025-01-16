package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.AuditsTabController;
import model.audit.Audit;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonsActions;
import view.components.ButtonsContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class AuditsTab extends JPanel {

    private JButton createButton = new JButton("Create New Audit");
    private ButtonsContainer buttonsContainer = new ButtonsContainer();
    private List<Audit> data;
    private AuditsTabController auditsTabController;
    private static String[] columnNamesCreateEdit = { "DateDebut", "ExpDate", "Subject", "Status", "takeCertificate", "IdAuditor", "IdOrganization", "IdSystemManagement"};
    private JButton viewDetailsButton;
    DefaultTableModel model;
    JTable auditsTable;

    public AuditsTab() {
        this.data = ControllersGetter.auditsController.getAllAudits(); // Get all audits
        System.out.println("data data data \t"+data);
        auditsTabController = new AuditsTabController(this);
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

    public JButton getViewDetailsButton() {
        return viewDetailsButton;
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
        String[] columnNames = {"IdAudit", "DateDebut", "ExpDate", "Subject", "Status", "takeCertificate", "IdAuditor", "IdOrganization", "IdSystemManagement", "Actions", "View Details"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" and "View Details" columns are editable
                return column == 9 || column == 10;
            }
        };

        auditsTable = new JTable(model);
        auditsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        auditsTable.setRowHeight(30);
        auditsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        auditsTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        auditsTable.getTableHeader().setForeground(Color.WHITE);
        auditsTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to the "Actions" column
        TableColumn actionsColumn = auditsTable.getColumnModel().getColumn(9);
        actionsColumn.setCellRenderer(buttonsContainer);
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), auditsTable, auditsTabController.getIButtonEditorEventsHandler()));

        // Add "View Details" button to the "View Details" column
        TableColumn viewDetailsColumn = auditsTable.getColumnModel().getColumn(10);
        viewDetailsColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                viewDetailsButton.setBackground(Color.white); // Blue color
                viewDetailsButton.setForeground(Color.black);
                viewDetailsButton.setFocusPainted(false);
                viewDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));



                return viewDetailsButton;
            }
        });

        viewDetailsColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                viewDetailsButton.setBackground(new Color(52, 152, 219)); // Blue color
                viewDetailsButton.setForeground(Color.WHITE);
                viewDetailsButton.setFocusPainted(false);
                viewDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add action listener for "View Details" button
                viewDetailsButton.addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String idAudit = (String) table.getValueAt(selectedRow, 0); // Get the ID from the first column
//                        PageSwitcher.switchToAuditDetails(idAudit,"adminDashboard");
                    }
                });

                return viewDetailsButton;
            }
        });

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(auditsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Method to handle "View Details" button click
    private void viewDetails(String idAudit) {
        // Implement logic to view details of the selected audit
        System.out.println("Viewing details for audit with ID: " + idAudit);
        // You can open a new dialog or window to display the details
    }
    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.auditsController.getAllAudits();
        System.out.println(data);

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Audit audit : data) {
            Object[] rowData = {
                    audit.getIdAudit(),
                    audit.getDateDebut(),
                    audit.getExpDate(),
                    audit.getSubject(),
                    audit.getStatus(),
                    audit.getTakeCertificate(),
                    audit.getIdAuditor(),
                    audit.getIdOrganization(),
                    audit.getIdSystemManagement(),
                    "Actions", // Placeholder for the action buttons
                    "View Details" // Placeholder for the view details button
            };
            model.addRow(rowData);
        }

        // Remove the "Actions" column if it exists
        TableColumn actionsColumn = auditsTable.getColumnModel().getColumn(9); // Column index for "Actions"
        auditsTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(9); // Column index for "Actions"
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonsContainer());
        actionsColumn.setCellEditor(new ButtonsActions(new JCheckBox(), auditsTable, auditsTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        auditsTable.addColumn(actionsColumn);

        // Move the "Actions" column back to its original position (index 9)
        auditsTable.moveColumn(auditsTable.getColumnCount() - 1, 9);

        // Remove the "View Details" column if it exists
        TableColumn viewDetailsColumn = auditsTable.getColumnModel().getColumn(10); // Column index for "View Details"
        auditsTable.removeColumn(viewDetailsColumn);

        // Recreate the "View Details" column with a new renderer and editor
        viewDetailsColumn = new TableColumn(10); // Column index for "View Details"
        viewDetailsColumn.setHeaderValue("View Details");
        viewDetailsColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                viewDetailsButton.setBackground(Color.WHITE);
                viewDetailsButton.setForeground(Color.BLACK);
                viewDetailsButton.setFocusPainted(false);
                viewDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                return viewDetailsButton;
            }
        });

        viewDetailsColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                viewDetailsButton.setBackground(new Color(52, 152, 219)); // Blue color
                viewDetailsButton.setForeground(Color.WHITE);
                viewDetailsButton.setFocusPainted(false);
                viewDetailsButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Add action listener for "View Details" button
                viewDetailsButton.addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String idAudit = (String) table.getValueAt(selectedRow, 0); // Get the ID from the first column
//                     PageSwitcher.switchToAuditDetails(idAudit,"adminDashboard");
                    }
                });

                return viewDetailsButton;
            }
        });

        // Re-add the "View Details" column to the table
        auditsTable.addColumn(viewDetailsColumn);

        // Move the "View Details" column back to its original position (index 10)
        auditsTable.moveColumn(auditsTable.getColumnCount() - 1, 10);

        // Repaint the table to reflect the changes
        auditsTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the AuditsTab
        JFrame frame = new JFrame("Audits Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the AuditsTab panel to the frame
        AuditsTab auditsTab = new AuditsTab();
        frame.add(auditsTab);

        // Display the frame
        frame.setVisible(true);
    }
}