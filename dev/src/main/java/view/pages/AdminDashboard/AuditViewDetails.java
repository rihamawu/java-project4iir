package view.pages.AdminDashboard;

import model.Accounts.Account;
import model.audit.Audit;
import model.audit.RequirementStat;
import model.audit.StandardStat;
import model.Organization.Organization;
import model.SystemManagement.ManagementSystem;
import utils.ControllersGetter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AuditViewDetails extends JPanel {

    private Audit audit;
    private Organization organization;
    private Account auditor;
    private ManagementSystem managementSystem;
    private String getBackPage;

    public AuditViewDetails() {
        // Default constructor
    }

    public AuditViewDetails(String idAudit, String getBackPage) {
        loadAuditDetails(idAudit, getBackPage);
    }

    public void loadAuditDetails(String idAudit, String getBackPage) {
        try {
            this.getBackPage = getBackPage;
            // Fetch audit details and related entities
            fetchData(idAudit);
            // Set up the UI
            setUpUi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading audit details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchData(String idAudit) throws Exception {
        // Fetch audit details
        audit = ControllersGetter.auditsController.getAuditById(idAudit);
        if (audit == null) {
            throw new Exception("Audit not found with ID: " + idAudit);
        }

        // Fetch related entities
        organization = ControllersGetter.organizationsController.getOrganizationById(audit.getIdOrganization());
        if (organization == null) {
            throw new Exception("Organization not found with ID: " + audit.getIdOrganization());
        }

        // Fetch auditor details with try-catch
        try {
            auditor = ControllersGetter.accountsController.getAuditorById(audit.getIdAuditor());
        } catch (Exception e) {
            throw new Exception("Error fetching auditor: " + e.getMessage());
        }

        managementSystem = ControllersGetter.organizationsController.getSystemManagementById(
                audit.getIdOrganization(), audit.getIdSystemManagement());
        if (managementSystem == null) {
            throw new Exception("Management System not found with ID: " + audit.getIdSystemManagement());
        }
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241)); // Light gray background

        // Create a main panel to hold all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Apply border to mainPanel

        // Add audit details
        addSection(mainPanel, "Audit Details", new String[][]{
                {"ID", audit.getIdAudit()},
                {"Start Date", audit.getDateDebut()},
                {"Expiration Date", audit.getExpDate()},
                {"Subject", audit.getSubject()},
                {"Status", audit.getStatus()},
                {"Take Certificate", String.valueOf(audit.isTakeCertificate())},
                {"Is Pass", audit.getIsPass()}
        });

        // Add final report details
        addSection(mainPanel, "Final Report", new String[][]{
                {"Report ID", audit.getFinalReport().getIdRapport()},
                {"Report URL", audit.getFinalReport().getUrl()}
        });

        // Add organization details
        addSection(mainPanel, "Organization", new String[][]{
                {"ID", organization.getIdOrganization()},
                {"Name", organization.getName()},
                {"Description", organization.getDescription()}
        });

        // Add auditor details
        addSection(mainPanel, "Auditor", new String[][]{
                {"ID", auditor.getIdAccount()},
                {"Name", auditor.getFirstName() + " " + auditor.getLastName()},
                {"Email", auditor.getEmail()}
        });

        // Add management system details
        addSection(mainPanel, "Management System", new String[][]{
                {"ID", managementSystem.getIdManagementSystem()},
                {"Description", managementSystem.getDescription()},
                {"Certificate", managementSystem.getCertificate()}
        });

        // Add requirements statistics table
        if (audit.getRequirementsStat() != null && !audit.getRequirementsStat().isEmpty()) {
            addRequirementStatTable(mainPanel, "Requirements Statistics", audit.getRequirementsStat());
        }

        // Add standards statistics table
        if (audit.getStandardsStat() != null && !audit.getStandardsStat().isEmpty()) {
            addStandardStatTable(mainPanel, "Standards Statistics", audit.getStandardsStat());
        }

        // Add the main panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Add a "Back" button at the bottom
        addBackButton();
    }

    private void addBackButton() {
        // Create a "Back" button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(52, 152, 219)); // Blue color
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener to the button
        backButton.addActionListener(e -> {
            // Switch back to the main page using PageSwitcher
//            PageSwitcher.switchPage(getBackPage);
        });

        // Create a panel to hold the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(236, 240, 241)); // Light gray background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the main panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addSection(JPanel parent, String title, String[][] data) {
        // Create a section panel
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add section title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94)); // Dark blue text
        sectionPanel.add(titleLabel);

        // Add section data
        for (String[] row : data) {
            JLabel label = new JLabel(row[0] + ": " + row[1]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(new Color(44, 62, 80)); // Dark blue text
            sectionPanel.add(label);
        }

        // Add the section panel to the parent panel
        parent.add(sectionPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between sections
    }

    private void addRequirementStatTable(JPanel parent, String title, List<RequirementStat> requirementsStat) {
        // Create a section panel
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add section title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94)); // Dark blue text
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Status");
        tableModel.addColumn("Solution");

        // Add data to the table model
        for (RequirementStat requirementStat : requirementsStat) {
            tableModel.addRow(new Object[]{
                    requirementStat.getIdRequirementStat(),
                    requirementStat.getPassStat(),
                    requirementStat.getSolution()
            });
        }

        // Create the table
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        sectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the section panel to the parent panel
        parent.add(sectionPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between sections
    }

    private void addStandardStatTable(JPanel parent, String title, List<StandardStat> standardsStat) {
        // Create a section panel
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add section title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94)); // Dark blue text
        sectionPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Status");
        tableModel.addColumn("Solution");

        // Add data to the table model
        for (StandardStat standardStat : standardsStat) {
            tableModel.addRow(new Object[]{
                    standardStat.getIdStandardStat(),
                    standardStat.getPassStat(),
                    standardStat.getSolution()
            });
        }

        // Create the table
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 73, 94)); // Dark blue header
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        sectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the section panel to the parent panel
        parent.add(sectionPanel);
        parent.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between sections
    }

    public static void main(String[] args) {
        // Example usage
        JFrame frame = new JFrame("Audit Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame

        // Create and add the AuditViewDetails panel to the frame
        AuditViewDetails auditViewDetails = new AuditViewDetails("665e8952-9b4a-4b5f-84e5-2b083b97242f", "MainPage");
        frame.add(auditViewDetails);

        // Display the frame
        frame.setVisible(true);
    }
}