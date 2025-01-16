package controller.Pages.adminDashboard.Tabs;

import model.audit.Audit;
import utils.globalControllersGetter;
import utils.IPopUpDialog;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.AuditsTab;

import javax.swing.*;

public class AuditsTabController {
    private AuditsTab view;

    public AuditsTabController(AuditsTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Add action listener to the create button
        view.getCreateButton().addActionListener(e -> {
            System.out.println("Create new audit");
            PopUpDialog createDialog = new PopUpDialog("Create Audit", AuditsTab.getColumnNamesCreateEdit(), popUpDialog);
        });
    }

    IPopUpDialog popUpDialog = (formDialog) -> {
        if (formDialog.getTitle().equals("Create Audit")) {
            handleCreateAudit(formDialog);
        } else if (formDialog.getTitle().equals("Edit Audit")) {
            handleEditAudit(formDialog);
        }
    };

    private void handleCreateAudit(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Create a new audit
            Audit audit = new Audit(
                    popUpDialog.getFormData().get("DateDebut"),
                    popUpDialog.getFormData().get("ExpDate"),
                    popUpDialog.getFormData().get("Subject"),
                    popUpDialog.getFormData().get("Status"),
                    popUpDialog.getFormData().get("IdAuditor"),
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("IdSystemManagement"),
                    null, // FinalReport
                    popUpDialog.getFormData().get("takeCertificate"),
                    "notYet", // isPass
                    null, // StandardsStat
                    null  // RequirementsStat
            );

            try {
                globalControllersGetter.auditsController.createAudit(audit);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Audit created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleEditAudit(AuditsTab.ButtonEditor editor) {
        PopUpDialog editDialog = new PopUpDialog("Edit Audit", AuditsTab.getColumnNamesCreateEdit(), editor.getRowData(), popUpDialog, editor.getIdAudit());
    }

    private void handleEditAudit(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Update the audit
            Audit audit = new Audit(
                    popUpDialog.getFormData().get("DateDebut"),
                    popUpDialog.getFormData().get("ExpDate"),
                    popUpDialog.getFormData().get("Subject"),
                    popUpDialog.getFormData().get("Status"),
                    popUpDialog.getFormData().get("IdAuditor"),
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("IdSystemManagement"),
                    null, // FinalReport
                    popUpDialog.getFormData().get("takeCertificate"),
                    "notYet", // isPass
                    null, // StandardsStat
                    null  // RequirementsStat
            );

            try {
                globalControllersGetter.auditsController.editAudit(popUpDialog.getId(), audit);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Audit updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteAudit(AuditsTab.ButtonEditor editor) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this Audit?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Delete the audit
            try {
                globalControllersGetter.auditsController.deleteAudit(editor.getIdAudit());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            view.refreshTable();
        }
    }
}