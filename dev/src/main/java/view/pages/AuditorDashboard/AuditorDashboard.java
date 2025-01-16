package view.pages.AuditorDashboard;

import controller.Pages.AuditorDashboard.AuditorDashboardController;
import view.pages.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuditorDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton logoutButton;

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public AuditorDashboard() {
        SetupUi();
        AuditorDashboardController controller = new AuditorDashboardController(this);
    }

    public void SetupUi() {
        setTitle("Auditor Dashboard");
        setSize(800, 600); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use a BorderLayout for the main frame
        setLayout(new BorderLayout());

        // Create a header panel for the logout button and text
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94)); // Dark blue background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add a welcome text on the left
        JLabel welcomeLabel = new JLabel("Welcome, Auditor!");
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
        tabbedPane.addTab("Gestion des systemes de gestion", new SystemManagementAuditorTab());
        tabbedPane.addTab("Gestion des standard", new StandardManagementAuditorTab());
        tabbedPane.addTab("Gestion des Audit", new AuditsManagementAuditorTab());


        // Add the tabbed pane to the center of the frame
        add(tabbedPane, BorderLayout.CENTER);

        // Create a footer panel (optional)
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(52, 73, 94)); // Dark blue background
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel footerLabel = new JLabel("© 2023 Auditor Dashboard. All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);

        footerPanel.add(footerLabel);

        // Add the footer panel to the bottom of the frame
        add(footerPanel, BorderLayout.SOUTH);


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current AdminDashboard window
                dispose();

                // Open the Login window
                LoginPage login = new LoginPage();
                login.setVisible(true);
            }
        });
        // Set the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the dashboard
        SwingUtilities.invokeLater(() -> {
            new AuditorDashboard();
        });
    }
}