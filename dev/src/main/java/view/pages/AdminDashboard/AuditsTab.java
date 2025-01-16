package view.pages.AdminDashboard;

import controller.Pages.adminDashboard.Tabs.AuditsTabController;
import model.audit.Audit;
import utils.globalControllersGetter;
import view.ButtonsContainer;
import view.AuditDetailsDialog;

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
    private static String[] columnNamesCreateEdit = {"DateDebut", "ExpDate", "Subject", "Status", "takeCertificate", "IdAuditor", "IdOrganization", "IdSystemManagement"};
    private DefaultTableModel model;
    private JTable auditsTable;

    public AuditsTab() {
        this.data = globalControllersGetter.auditsController.getAllAudits(); // Get all audits
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

        // Create and return the table model
        model = new DefaultTableModel(columnNames, 0) {
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
        actionsColumn.setCellEditor(new ButtonEditor(auditsTable, auditsTabController));

        // Add "View Details" button to the "View Details" column
        TableColumn viewDetailsColumn = auditsTable.getColumnModel().getColumn(10);
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
                        // Show the AuditDetailsDialog
                        AuditDetailsDialog dialog = new AuditDetailsDialog((JFrame) SwingUtilities.getWindowAncestor(AuditsTab.this), idAudit);
                        dialog.setVisible(true);
                    }
                });

                return viewDetailsButton;
            }
        });

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(auditsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();
    }

    public void refreshTable() {
        // Fetch the latest data
        data = globalControllersGetter.auditsController.getAllAudits();

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

        // Repaint the table to reflect the changes
        auditsTable.repaint();
    }

    // Inner class for ButtonEditor
    public class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton deleteButton;
        private JTable table;
        private int currentRow;
        private Object[] rowData;
        private String idAudit; // Audit ID

        public ButtonEditor(JTable table, AuditsTabController controller) {
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
                controller.handleEditAudit(ButtonEditor.this);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleDeleteAudit(this);
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

            // Extract the audit ID from the table
            idAudit = (String) table.getModel().getValueAt(row, 0); // Audit ID is in the first column

            return panel; // Return the panel containing the buttons
        }

        @Override
        public Object getCellEditorValue() {
            return ""; // Return an empty string (no value is edited)
        }

        public Object[] getRowData() {
            return rowData;
        }

        public String getIdAudit() {
            return idAudit;
        }
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