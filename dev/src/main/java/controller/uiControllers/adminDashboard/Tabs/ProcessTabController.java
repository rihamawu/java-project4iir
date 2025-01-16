package controller.uiControllers.adminDashboard.Tabs;

import model.Organization.OrgProcess;
import utils.ControllersGetter;
import utils.interfaces.IPopUpDialog;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.ProcessTab;

import javax.swing.*;

public class ProcessTabController {
    private ProcessTab view;

    public ProcessTabController(ProcessTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Add action listener to the create button
        view.getCreateButton().addActionListener(e -> {
            System.out.println("Create new process");
            PopUpDialog createDialog = new PopUpDialog("Create Process", ProcessTab.getColumnNamesCreateEdit(), popUpDialog);
        });
    }

    IPopUpDialog popUpDialog = (formDialog) -> {
        if (formDialog.getTitle().equals("Create Process")) {
            handleCreateProcess(formDialog);
        } else if (formDialog.getTitle().equals("Edit Process")) {
            handleEditProcess(formDialog);
        }
    };

    private void handleCreateProcess(PopUpDialog popUpDialog) {
        if (popUpDialog.validateForm()) {
            // Create a new process
            OrgProcess process = new OrgProcess(

                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description")
            );

            try {
                ControllersGetter.organizationsController.createProcess(process.getIdOrganization(), process);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Process created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleEditProcess(ProcessTab.ButtonEditor editor) {
        PopUpDialog editDialog = new PopUpDialog("Edit Process", ProcessTab.getColumnNamesCreateEdit(), editor.getRowData(), popUpDialog, editor.getIdProc());
    }

    private void handleEditProcess(PopUpDialog popUpDialog)   {
        if (popUpDialog.validateForm()) {
            OrgProcess process = new OrgProcess(
                    popUpDialog.getFormData().get("IdOrganization"),
                    popUpDialog.getIdOrg(),
                    popUpDialog.getFormData().get("Name"),
                    popUpDialog.getFormData().get("Description")
            );


            try{
            ControllersGetter.organizationsController.editOrgProcess(process.getIdOrganization(),process.getIdOrgProcess(), process);
                view.refreshTable();
                JOptionPane.showMessageDialog(popUpDialog, "Process updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                popUpDialog.dispose();

            }

            catch (Exception e) {
                JOptionPane.showMessageDialog(popUpDialog, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popUpDialog, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteProcess(ProcessTab.ButtonEditor editor) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this Process?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Delete the process
            try {
                System.out.println("editor : " + editor.getIdOrg());
                System.out.println("editor : " + editor.getIdProc());
                ControllersGetter.organizationsController.deleteProcess(editor.getIdOrg(),editor.getIdProc());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            view.refreshTable();
        }
    }
}