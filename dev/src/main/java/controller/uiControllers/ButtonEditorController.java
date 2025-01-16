package controller.uiControllers;

import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IPopUpDialog;
import view.pages.AdminDashboard.AuditorManagementTab;
import view.components.ButtonsActions;
import view.pages.AdminDashboard.PopUpDialog;

public class ButtonEditorController {
    private ButtonsActions view;
    private PopUpDialog createAuditorForm;

    private IPopUpDialog iFormDialogEditEventHandler;
    private IPopUpDialog iFormDialogDeleteEventHandler;
    private IButtonEditorEventsHandler iButtonEditorEditEventHandler;
    public ButtonEditorController(ButtonsActions view, IButtonEditorEventsHandler iButtonEditorEventsHandler) {
        this.view = view;
        this.iButtonEditorEditEventHandler = iButtonEditorEventsHandler;

        controllers();
    }
    public void controllers(){
        addEditAuditorButtonEvent();
        addDeleteAuditorButtonEvent();
    }

    private void addEditAuditorButtonEvent() {

        view.getEditButton().addActionListener(ActionEvent -> {
           String[] columnNames = AuditorManagementTab.getColumnNamesCreateEdit();
//            createAuditorForm =  new FormDialog(" Edit",columnNames,view.getRowData(), iFormDialogEditEventHandler);

            iButtonEditorEditEventHandler.editObjectEventHandler(view);

        });
    }
    private void addDeleteAuditorButtonEvent() {


        view.getDeleteButton().addActionListener(ActionEvent -> {
            String[]   columnNames = AuditorManagementTab.getColumnNamesCreateEdit();

//            JFrame parentFrame = new JFrame("Edit auditor");
//            Object[] Column = view.getRowData();

            iButtonEditorEditEventHandler.deleteObjectEventHandler(view);

        });
    }
}
