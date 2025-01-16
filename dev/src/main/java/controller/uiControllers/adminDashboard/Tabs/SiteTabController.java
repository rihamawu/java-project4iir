package controller.uiControllers.adminDashboard.Tabs;

import model.Organization.Site;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import utils.interfaces.objectConverter.SiteConverter;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;
import view.pages.AdminDashboard.SiteTab;

import javax.swing.*;

public class SiteTabController {
    private SiteTab view;
    private PopUpDialog createSiteForm;
    private PopUpDialog editSiteForm;
    private String[] columnNames = SiteTab.getColumnNamesCreateEdit();
    private SaveUtil<Site> saveUtil = new SaveUtil(new SiteConverter());

    public SiteTabController(SiteTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateSiteButtonEvent();
    }

    private void addCreateSiteButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createSiteForm = new PopUpDialog("Create Site", columnNames, saveCreateSiteIFormEventHandler);
        });
    }

    private IPopUpDialog saveEditSiteIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Site site = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = site.getIdOrganization(); // Get the organization ID
                ControllersGetter.organizationsController.editSiteInOrganization(idOrg, formDialog.getIdOrg(), site);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editSiteForm,
                        "Site updated successfully!",
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
                    editSiteForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IPopUpDialog saveCreateSiteIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Site site = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = site.getIdOrganization(); // Get the organization ID
                ControllersGetter.organizationsController.addSiteToOrganization(idOrg, site);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Site added successfully!",
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
            String[] columnNames = SiteTab.getColumnNamesCreateEdit();
            editSiteForm = new PopUpDialog("Edit", columnNames, view.getRowData(), saveEditSiteIFormEventHandler, view.getId());
        }

        @Override
        public void deleteObjectEventHandler(ButtonsActions buttonsActionsView) {
            try {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Site?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    String idOrg = buttonsActionsView.getRowData()[0].toString(); // Get the organization ID
                    ControllersGetter.organizationsController.deleteSiteFromOrganization(idOrg, buttonsActionsView.getId());
                    view.refreshTable();
                    System.out.println("Deleting Site");
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