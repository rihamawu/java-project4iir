package utils.interfaces;

import view.components.ButtonsActions;

public interface IButtonEditorEventsHandler {
    void  editObjectEventHandler(ButtonsActions view);

    void  deleteObjectEventHandler(ButtonsActions view);
}
