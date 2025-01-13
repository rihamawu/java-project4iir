package view;

import javax.swing.*;
import java.awt.*;

public class DashboardAdmin extends JFrame {
    private JTabbedPane tabbedPane;

    public DashboardAdmin() {
        SetupUi();
    }

    public void SetupUi() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTabbedPane for the menu
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Gestion des audits", new JPanel()); // Placeholder panel
        tabbedPane.addTab("Gestion des auditor", new JPanel()); // Placeholder panel
        tabbedPane.addTab("Gestion des organisations", new JPanel()); // Placeholder panel
        tabbedPane.addTab("Gestion des systemes de gestion", new JPanel()); // Placeholder panel

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}