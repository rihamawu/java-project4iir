package controller.uiControllers.adminDashboard.Tabs;

import model.SystemManagement.ManagementSystem;
import utils.ControllersGetter;
import utils.interfaces.IPopUpDialog;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.ManagementSystemManagementTab;

import javax.swing.*;

public class ManagementSystemManagementTabController {
    private ManagementSystemManagementTab view;

    public ManagementSystemManagementTabController(ManagementSystemManagementTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Add action listener to the create button
        view.getCreateButton().addActionListener(e -> {
            System.out.println("Create new management system");
            PopUpDialog createDialog = new PopUpDialog("Create Management System", ManagementSystemManagementTab.getColumnNamesCreateEdit(), popUpDialog);
        });
    }

    IPopUpDialog popUpDialog = (formDialog) -> {
        if (formDialog.getTitle().equals("Create Management System")) {
            handleCreateManagementSystem(formDialog);
        } else if (formDialog.getTitle().equals("Edit Management System")) {
            handleEditManagementSystem(formDialog);
        }
    };

    private void handleCreateManagementSystem(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Create a new management system
            ManagementSystem managementSystem = new ManagementSystem(
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("Description"),
                    popUpDialog.getFormData().get("Certificate")
            );
            try {
                ControllersGetter.organizationsController.addManagementSystemToOrganization(managementSystem.getIdOrganization(), managementSystem);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Management System created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleEditManagementSystem(ManagementSystemManagementTab.ButtonEditor editor) {
        PopUpDialog editDialog = new PopUpDialog(
                "Edit Management System",
                ManagementSystemManagementTab.getColumnNamesCreateEdit(),
                editor.getRowData(),
                popUpDialog,
                editor.getIdOrg(), // Pass the organization ID
                editor.getIdManagementSystem() // Pass the management system ID
        );
    }

    private void handleEditManagementSystem(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Update the management system
            ManagementSystem managementSystem = new ManagementSystem(
                    popUpDialog.getIdManagementSystem(), // Management System ID
                    popUpDialog.getFormData().get("IdOrganization"), // Organization ID
                    popUpDialog.getFormData().get("Description"),
                    popUpDialog.getFormData().get("Certificate")
            );
            System.out.println("Edit Management System: " + managementSystem);
            try {
                ControllersGetter.organizationsController.editManagementSystemInOrganization(managementSystem.getIdOrganization(), managementSystem.getIdManagementSystem(), managementSystem);

                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Management System updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteManagementSystem(ManagementSystemManagementTab.ButtonEditor editor) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this Management System?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Delete the management system using both organization ID and management system ID
            try {
                ControllersGetter.organizationsController.deleteManagementSystemFromOrganization(editor.getIdOrg(), editor.getIdManagementSystem());
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            view.refreshTable();
        }
    }
}