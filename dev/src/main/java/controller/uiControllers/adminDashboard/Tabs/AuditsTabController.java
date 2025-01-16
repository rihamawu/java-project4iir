package controller.uiControllers.adminDashboard.Tabs;

import model.audit.Audit;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.AuditsTab;

import javax.swing.*;

public class AuditsTabController {
    private AuditsTab view;
    private PopUpDialog createAuditForm;
    private PopUpDialog editAuditForm;
    private String[] columnNames = AuditsTab.getColumnNamesCreateEdit();
    private SaveUtil<Audit> saveUtil = new SaveUtil(()->{
        return new Audit();
    });

    public AuditsTabController(AuditsTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateAuditButtonEvent();
    }

    private void addCreateAuditButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createAuditForm = new PopUpDialog("Create Audit", columnNames, saveCreateAuditIFormEventHandler);
        });
    }
    private  void addViewDetailsButtonEvent() {
        view.getViewDetailsButton().addActionListener(ActionEvent -> {

        });
    }

    private IPopUpDialog saveEditAuditIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Audit audit = saveUtil.saveFormData(formDialog.getFormData());
                ControllersGetter.auditsController.editAudit(formDialog.getId(), audit);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editAuditForm,
                        "Audit updated successfully!",
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
                    editAuditForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IPopUpDialog saveCreateAuditIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Audit audit = saveUtil.saveFormData(formDialog.getFormData());
                ControllersGetter.auditsController.createAudit(audit);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Audit added successfully!",
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
            String[] columnNames = AuditsTab.getColumnNamesCreateEdit();
            editAuditForm = new PopUpDialog("Edit", columnNames, view.getRowData(), saveEditAuditIFormEventHandler, view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            try {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Audit?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    ControllersGetter.auditsController.deleteAudit(buttonsActionsView.getId());
                    view.refreshTable();
                    System.out.println("Deleting Audit");
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