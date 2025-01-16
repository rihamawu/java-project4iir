package view.pages.AdminDashboard;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton logoutButton;

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public AdminDashboard() {
        SetupUi();
    }

    public void SetupUi() {
        setTitle("Admin Dashboard");
        setSize(800, 600); // Same size as AuditorDashboard
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use a BorderLayout for the main frame
        setLayout(new BorderLayout());

        // Create a header panel for the logout button and text
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94)); // Dark blue background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding around the header

        // Add a welcome text on the left
        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(231, 76, 60)); // Red color for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add logout button to the right side of the header panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(52, 73, 94)); // Match header background
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        // Add the header panel to the top of the frame
        add(headerPanel, BorderLayout.NORTH);

        // Create a JTabbedPane for the menu
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(236, 240, 241)); // Light gray background
        tabbedPane.setForeground(new Color(52, 73, 94)); // Dark blue text

        // Set tab placement to the LEFT
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        // Add tabs
        tabbedPane.addTab("Gestion des organisations", new OrganizationManagementTab()  );
        tabbedPane.addTab("Gestion des processus", new ProcessTab());
        tabbedPane.addTab("Gestion des systemes de gestion", new ManagementSystemManagementTab());
        tabbedPane.addTab("Gestion des standard", new StandardTab());
        tabbedPane.addTab("Gestion des audits", new AuditsTab());

        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Create a footer panel (optional)
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94)); // Dark blue background
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the footer

        JLabel footerLabel = new JLabel("© 2023 Admin Dashboard. All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);

        footerPanel.add(footerLabel);

        // Add the footer panel to the bottom of the frame
        add(footerPanel, BorderLayout.SOUTH);

        // Set the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the dashboard
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard();
        });
    }
}