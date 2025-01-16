package controller.Pages.adminDashboard.Tabs;

import model.managementSystem.Standard;
import utils.globalControllersGetter;
import utils.IPopUpDialog;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.StandardTab;

import javax.swing.*;
import java.util.ArrayList;

public class StandardTabController {
    private StandardTab view;

    public StandardTabController(StandardTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Add action listener to the create button
        view.getCreateButton().addActionListener(e -> {
            System.out.println("Create new standard");
            PopUpDialog createDialog = new PopUpDialog("Create Standard", StandardTab.getColumnNamesCreateEdit(), popUpDialog);
        });
    }

    IPopUpDialog popUpDialog = (formDialog) -> {
        if (formDialog.getTitle().equals("Create Standard")) {
            handleCreateStandard(formDialog);
        } else if (formDialog.getTitle().equals("Edit Standard")) {
            handleEditStandard(formDialog);
        }
    };

    private void handleCreateStandard(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Create a new standard
            Standard standard = new Standard(
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("IdManagementSystem"),
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description"),
                    popUpDialog.getFormData().get("Reference"),
                    new ArrayList<>() // Empty list of clauses
            );

            try {
                globalControllersGetter.organizationsController.createSystemManagementStandard(standard.getIdOrganization(), standard.getIdManagementSystem(), standard);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Standard created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleEditStandard(StandardTab.ButtonEditor editor) {
        PopUpDialog editDialog = new PopUpDialog("Edit Standard", StandardTab.getColumnNamesCreateEdit(), editor.getRowData(), popUpDialog, editor.getIdStandard());
    }

    private void handleEditStandard(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Update the standard
            Standard standard = new Standard(
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("IdManagementSystem"),
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description"),
                    popUpDialog.getFormData().get("Reference"),
                    new ArrayList<>() // Empty list of clauses
            );

            try {
                globalControllersGetter.organizationsController.editSystemManagementStandardById(standard.getIdOrganization(), standard.getIdManagementSystem(), popUpDialog.getId(), standard);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Standard updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteStandard(StandardTab.ButtonEditor editor) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this Standard?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Delete the standard
            try {
                globalControllersGetter.organizationsController.deleteSystemManagementStandardById(editor.getIdOrg(), editor.getIdManagementSystem(), editor.getIdStandard());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            view.refreshTable();
        }
    }
}