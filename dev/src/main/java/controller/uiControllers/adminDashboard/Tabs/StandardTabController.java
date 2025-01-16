package controller.uiControllers.adminDashboard.Tabs;

import model.SystemManagement.Standard.Standard;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import utils.interfaces.objectConverter.StandardConverter;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.StandardTab;

import javax.swing.*;

public class StandardTabController {
    private StandardTab view;
    private PopUpDialog createStandardForm;
    private PopUpDialog editStandardForm;
    private String[] columnNames = StandardTab.getColumnNamesCreateEdit();
    private SaveUtil<Standard> saveUtil = new SaveUtil(new StandardConverter());

    public StandardTabController(StandardTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateStandardButtonEvent();
    }

    private void addCreateStandardButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createStandardForm = new PopUpDialog("Create Standard", columnNames, saveCreateStandardIFormEventHandler);
        });
    }

    private IPopUpDialog saveEditStandardIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Standard standard = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = standard.getIdOrganization(); // Get the organization ID
                String idManagementSystem = standard.getIdManagementSystem(); // Get the management system ID
                ControllersGetter.organizationsController.editSystemManagementStandardById(idOrg, idManagementSystem, formDialog.getIdOrg(), standard);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editStandardForm,
                        "Standard updated successfully!",
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
                    editStandardForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IPopUpDialog saveCreateStandardIFormEventHandler = (formDialog) -> {
        System.out.println("StandardTabController saveCreateStandardIFormEventHandler");
        try {
            if (formDialog.validateForm()) {
                Standard standard = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = standard.getIdOrganization(); // Get the organization ID
                String idManagementSystem = standard.getIdManagementSystem(); // Get the management system ID
                ControllersGetter.organizationsController.createSystemManagementStandard(idOrg, idManagementSystem, standard);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Standard added successfully!",
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
            String[] columnNames = StandardTab.getColumnNamesCreateEdit();
            editStandardForm = new PopUpDialog("Edit", columnNames, view.getRowData(), saveEditStandardIFormEventHandler, view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            try {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Standard?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    String idOrg = buttonsActionsView.getRowData()[0].toString(); // Get the organization ID
                    String idManagementSystem = buttonsActionsView.getRowData()[1].toString(); // Get the management system ID
                    ControllersGetter.organizationsController.deleteSystemManagementStandardById(idOrg, idManagementSystem, buttonsActionsView.getId());
                    view.refreshTable();
                    System.out.println("Deleting Standard");
                } else {
                    System.out.println("Deleting operation canceled.");
                }
            } catch (Exception e) {
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