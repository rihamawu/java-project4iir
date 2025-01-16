package view.pages.AuditorDashboard;

import model.audit.Audit;
import utils.TableConverterUtility;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class AuditsAuditorTab extends JPanel {

    private List<Audit> data;
    private JButton viewDetailsButton;
    private DefaultTableModel model;
    private JTable auditsTable;

    public AuditsAuditorTab() {
        this.data = ControllersGetter.auditsController.getAllAudits(); // Get all audits
        System.out.println("Audits data loaded: " + data);
        setUpUi();
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define column names (without "Actions" column)
        String[] columnNames = {"IdAudit", "DateDebut", "ExpDate", "Subject", "Status", "takeCertificate", "IdAuditor", "IdOrganization", "IdSystemManagement", "View Details"};

        // Convert data to table format
        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "View Details" column is editable
                return column == 9;
            }
        };

        // Create the table
        auditsTable = new JTable(model);
        auditsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        auditsTable.setRowHeight(30);
        auditsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        auditsTable.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        auditsTable.getTableHeader().setForeground(Color.WHITE);
        auditsTable.setFillsViewportHeight(true);

        // Add "View Details" button to the "View Details" column
        TableColumn viewDetailsColumn = auditsTable.getColumnModel().getColumn(9);
        viewDetailsColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                viewDetailsButton = new JButton("View Details");
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
//                        PageSwitcher.switchToAuditDetails(idAudit,"auditorDashboard");
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

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.auditsController.getAllAudits();
        System.out.println("Refreshed data: " + data);

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
                    "View Details" // Placeholder for the view details button
            };
            model.addRow(rowData);
        }

        // Remove the "View Details" column if it exists
        TableColumn viewDetailsColumn = auditsTable.getColumnModel().getColumn(9);
        auditsTable.removeColumn(viewDetailsColumn);

        // Recreate the "View Details" column with a new renderer and editor
        viewDetailsColumn = new TableColumn(9);
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
//                        PageSwitcher.switchToAuditDetails(idAudit,"auditorDashboard");
                    }
                });

                return viewDetailsButton;
            }
        });

        // Re-add the "View Details" column to the table
        auditsTable.addColumn(viewDetailsColumn);

        // Move the "View Details" column back to its original position (index 9)
        auditsTable.moveColumn(auditsTable.getColumnCount() - 1, 9);

        // Repaint the table to reflect the changes
        auditsTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the AuditsAuditorTab
        JFrame frame = new JFrame("Audits Management (Auditor View)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the AuditsAuditorTab panel to the frame
        AuditsAuditorTab auditsAuditorTab = new AuditsAuditorTab();
        frame.add(auditsAuditorTab);

        // Display the frame
        frame.setVisible(true);
    }
}