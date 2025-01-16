package controller.uiControllers.adminDashboard.Tabs;

import model.Responsible;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import utils.interfaces.objectConverter.ResponsibleConverter;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.ResponsibleTab;

import javax.swing.*;

public class ResponsibleTabController {
    private ResponsibleTab view;
    private PopUpDialog createResponsibleForm;
    private PopUpDialog editResponsibleForm;
    private String[] columnNames = ResponsibleTab.getColumnNamesCreateEdit();
    private SaveUtil<Responsible> saveUtil = new SaveUtil(new ResponsibleConverter());

    public ResponsibleTabController(ResponsibleTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateResponsibleButtonEvent();
    }

    private void addCreateResponsibleButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createResponsibleForm = new PopUpDialog("Create Responsible", columnNames, saveCreateResponsibleIFormEventHandler);
        });
    }

    private IPopUpDialog saveEditResponsibleIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Responsible responsible = saveUtil.saveFormData(formDialog.getFormData());
                ControllersGetter.responsiblesController.editResponsible(formDialog.getId(), responsible);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editResponsibleForm,
                        "Responsible updated successfully!",
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
                    editResponsibleForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IPopUpDialog saveCreateResponsibleIFormEventHandler = (formDialog) -> {
        System.out.println("ResponsibleTabController saveCreateResponsibleIFormEventHandler");
        try {
            if (formDialog.validateForm()) {
                Responsible responsible = saveUtil.saveFormData(formDialog.getFormData());
                ControllersGetter.responsiblesController.createResponsible(responsible);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Responsible added successfully!",
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
            String[] columnNames = ResponsibleTab.getColumnNamesCreateEdit();
            editResponsibleForm = new PopUpDialog("Edit", columnNames, view.getRowData(), saveEditResponsibleIFormEventHandler, view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete this Responsible?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (response == JOptionPane.YES_OPTION) {
                ControllersGetter.responsiblesController.deleteResponsible(buttonsActionsView.getId());
                view.refreshTable();
                System.out.println("Deleting Responsible");
            } else {
                System.out.println("Deleting operation canceled.");
            }
        }
    };

    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }
}