package view;

import javax.swing.*;
import java.awt.*;

public class DashboardAuditor extends JFrame {
    private JTabbedPane tabbedPane;

    public DashboardAuditor() {
        SetupUi();
    }

    public void SetupUi() {
        setTitle("Auditor Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JTabbedPane for the menu
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Gestion des audit", new JPanel()); // Placeholder panel

        // Add the tabbed pane to the frame
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}