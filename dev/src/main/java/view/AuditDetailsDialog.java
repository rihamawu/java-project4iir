package view;

import model.audit.Audit;
import utils.globalControllersGetter;

import javax.swing.*;
import java.awt.*;

public class AuditDetailsDialog extends JDialog {
    private Audit audit;

    public AuditDetailsDialog(JFrame parent, String idAudit) {
        super(parent, "Audit Details", true);
        this.audit = globalControllersGetter.auditsController.getAuditById(idAudit);
        setUpUi();
    }

    private void setUpUi() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);
        this.setLocationRelativeTo(null); // Center the dialog

        // Create a panel to hold the audit details
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add audit details to the panel
        detailsPanel.add(new JLabel("ID:"));
        detailsPanel.add(new JLabel(audit.getIdAudit()));

        detailsPanel.add(new JLabel("Start Date:"));
        detailsPanel.add(new JLabel(audit.getDateDebut()));

        detailsPanel.add(new JLabel("Expiration Date:"));
        detailsPanel.add(new JLabel(audit.getExpDate()));

        detailsPanel.add(new JLabel("Subject:"));
        detailsPanel.add(new JLabel(audit.getSubject()));

        detailsPanel.add(new JLabel("Status:"));
        detailsPanel.add(new JLabel(audit.getStatus()));

        detailsPanel.add(new JLabel("Auditor ID:"));
        detailsPanel.add(new JLabel(audit.getIdAuditor()));

        detailsPanel.add(new JLabel("Organization ID:"));
        detailsPanel.add(new JLabel(audit.getIdOrganization()));

        detailsPanel.add(new JLabel("Management System ID:"));
        detailsPanel.add(new JLabel(audit.getIdSystemManagement()));

        detailsPanel.add(new JLabel("Take Certificate:"));
        detailsPanel.add(new JLabel(audit.getTakeCertificate()));

        detailsPanel.add(new JLabel("Pass Status:"));
        detailsPanel.add(new JLabel(audit.getIsPass()));

        // Add the details panel to the dialog
        this.add(detailsPanel, BorderLayout.CENTER);

        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> this.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
