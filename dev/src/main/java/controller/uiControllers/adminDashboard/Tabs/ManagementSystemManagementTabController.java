package controller.uiControllers.adminDashboard.Tabs;

import model.SystemManagement.ManagementSystem;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import utils.interfaces.objectConverter.ManagementSystemConverter;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.ManagementSystemManagementTab;

import javax.swing.*;

public class ManagementSystemManagementTabController {
    private ManagementSystemManagementTab view;
    private PopUpDialog createManagementSystemForm;
    private PopUpDialog editManagementSystemForm;
    private String[] columnNames = ManagementSystemManagementTab.getColumnNamesCreateEdit();
    private SaveUtil<ManagementSystem> saveUtil = new SaveUtil(new ManagementSystemConverter());

    public ManagementSystemManagementTabController(ManagementSystemManagementTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateManagementSystemButtonEvent();
    }

    private void addCreateManagementSystemButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createManagementSystemForm = new PopUpDialog("Create Management System", columnNames, saveCreateManagementSystemIFormEventHandler);
        });
    }

    private IPopUpDialog saveEditManagementSystemIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                ManagementSystem managementSystem = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = managementSystem.getIdOrganization(); // Get the organization ID
                System.out.println(idOrg+"\t"+formDialog.getId()+"here we go");

                ControllersGetter.organizationsController.editManagementSystemInOrganization(idOrg, formDialog.getId(), managementSystem);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editManagementSystemForm,
                        "Management System updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                formDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    editManagementSystemForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IPopUpDialog saveCreateManagementSystemIFormEventHandler = (formDialog) -> {
        System.out.println("ManagementSystemManagementTabController saveCreateManagementSystemIFormEventHandler");
        try {
            if (formDialog.validateForm()) {
                ManagementSystem managementSystem = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = managementSystem.getIdOrganization(); // Get the organization ID
                ControllersGetter.organizationsController.addManagementSystemToOrganization(idOrg, managementSystem);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Management System added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                view.refreshTable();
                formDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    formDialog,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IButtonEditorEventsHandler iButtonEditorEventsHandler = new IButtonEditorEventsHandler() {
        @Override
        public void editObjectEventHandler(ButtonsActions view) {
            String[] columnNames = ManagementSystemManagementTab.getColumnNamesCreateEdit();
            editManagementSystemForm = new PopUpDialog("Edit", columnNames, view.getRowData(), saveEditManagementSystemIFormEventHandler, view.getId());
        }
        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            try {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Management System?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    String idOrg = buttonsActionsView.getRowData()[0].toString(); // Get the organization ID
                    ControllersGetter.organizationsController.deleteManagementSystemFromOrganization(idOrg, buttonsActionsView.getId());
                    view.refreshTable();
                    System.out.println("Deleting Management System");
                } else {
                    System.out.println("Deleting operation canceled.");
                }
            }catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "An error occurred: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    };

    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }
}