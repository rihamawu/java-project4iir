package controller.uiControllers.adminDashboard.Tabs;

import model.Accounts.Account;
import utils.ControllersGetter;
import  utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import utils.interfaces.objectConverter.AccountConverter;
import view.pages.AdminDashboard.AuditorManagementTab;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;


import javax.swing.*;

import static utils.ControllersGetter.accountsController;

public class AuditorManagementTabController {
    private AuditorManagementTab view;
    private PopUpDialog createAuditorForm;
    private PopUpDialog editAuditorForm;
    private String[] columnNames = AuditorManagementTab.getColumnNamesCreateEdit();
    private SaveUtil<Account> saveUtil = new SaveUtil(new AccountConverter());




//    public IFormDialogEventHandler getSaveDeleteAuditorIFormEventHandler() {
//        return saveDeleteAuditorIFormEventHandler;
//    }
//
//
//    public IFormDialogEventHandler getSaveEditAuditorIFormEventHandler() {
//        return SaveEditAuditorIFormEventHandler;
//    }
//
//    public IFormDialogEventHandler getSaveCreateAuditorIFormEventHandler() {
//        return saveCreateAuditorIFormEventHandler;
//    }


    private IButtonEditorEventsHandler iButtonEditorEventsHandler = new IButtonEditorEventsHandler() {


        @Override
        public void editObjectEventHandler(ButtonsActions view) {
            String[] columnNames = AuditorManagementTab.getColumnNamesCreateEdit();




            editAuditorForm =  new PopUpDialog(" Edit",columnNames,view.getRowData(), SaveEditAuditorIFormEventHandler,view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            // Show a confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                    null, // No parent component
                    "Are you sure you want to delete this auditor?", // Message
                    "Confirm Delete", // Dialog title
                    JOptionPane.YES_NO_OPTION // Option type (Yes/No)
            );

            // Check the user's response
            if (response == JOptionPane.YES_OPTION) {
                accountsController.deleteAccount(buttonsActionsView.getId());
                view.refreshTable();
                System.out.println("Deleting auditor ");
            } else {
                // User clicked "No" or closed the dialog, do nothing
                System.out.println("Deleting operation canceled.");
            }
        }
    };

    private IPopUpDialog SaveEditAuditorIFormEventHandler = (formDialog)->{
        try {
            if (formDialog.validateForm()) {

                Account account = saveUtil.saveFormData(formDialog.getFormData()); // Save form data
                account.setAccountType("auditor");
                System.out.println(account);
                accountsController.editAccount(formDialog.getId(),account); // Create account using AccountsController

                // Show success message
                JOptionPane.showMessageDialog(
                        formDialog,
                        "The Auditor was updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh the table to show the new data
                view.refreshTable();
                formDialog.dispose(); // Close the form dialog
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
                    editAuditorForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };


    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }


    public AuditorManagementTabController(AuditorManagementTab view) {
        this.view = view;
        initController();

    }
    private IPopUpDialog saveCreateAuditorIFormEventHandler = (formDialog)->{
        try {
            if (formDialog.validateForm()) {
                Account account = saveUtil.saveFormData(formDialog.getFormData()); // Save form data
                account.setAccountType("auditor");
                ControllersGetter.accountsController.createAccount(account); // Create account using AccountsController

                // Show success message
                JOptionPane.showMessageDialog(
                        createAuditorForm,
                        "New Auditor added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh the table to show the new data
                view.refreshTable();

                formDialog.dispose(); // Close the form dialog
            } else {
                JOptionPane.showMessageDialog(
                        createAuditorForm,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    createAuditorForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private void initController() {
        addCreatAuditorButtonEvent();
    }
    private void addCreatAuditorButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createAuditorForm= new PopUpDialog("Create Auditor", columnNames, saveCreateAuditorIFormEventHandler);

        });
    }
}
