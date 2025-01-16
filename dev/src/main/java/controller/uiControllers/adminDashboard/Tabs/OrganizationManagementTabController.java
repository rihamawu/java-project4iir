package controller.uiControllers.adminDashboard.Tabs;

import model.Organization.Organization;
import utils.ControllersGetter;
import utils.interfaces.IPopUpDialog;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.OrganizationManagementTab;

import javax.swing.*;

public class OrganizationManagementTabController {
    private OrganizationManagementTab view;

    public OrganizationManagementTabController(OrganizationManagementTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Add action listener to the create button
        view.getCreateButton().addActionListener(e -> {
            System.out.println("Create new organization");
            PopUpDialog createDialog = new PopUpDialog("Create Organization", new String[]{"Name", "Description"}, popUpDialog);

        });
    }
    IPopUpDialog popUpDialog = (formDialog)-> {
        if (formDialog.getTitle().equals("Create Organization")) {
            handleCreateOrganization(formDialog);
        } else if (formDialog.getTitle().equals("Edit Organization")) {
            handleEditOrganization(formDialog);
        }
    };

    private void handleCreateOrganization(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Create a new organization
            Organization organization = new Organization(
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description")
            );
            ControllersGetter.organizationsController.createOrganization(organization);
            view.refreshTable();
            JOptionPane.showMessageDialog(popUpDialog, "Organization created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            popUpDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleEditOrganization(OrganizationManagementTab.ButtonEditor editor) {
        PopUpDialog editDialog = new PopUpDialog("Edit Organization", new String[]{"Name", "Description"}, editor.getRowData(), popUpDialog, editor.getId());

    }

    private void handleEditOrganization(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Update the organization
            Organization organization = new Organization(
                    popUpDialog.getId(),
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description")
            );
            ControllersGetter.organizationsController.editOrganization(popUpDialog.getId(), organization);
            view.refreshTable();
            JOptionPane.showMessageDialog(popUpDialog, "Organization updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            popUpDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteOrganization(OrganizationManagementTab.ButtonEditor editor) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this Organization?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Delete the organization
            ControllersGetter.organizationsController.deleteOrganization(editor.getId());
            view.refreshTable();
        }
    }
}