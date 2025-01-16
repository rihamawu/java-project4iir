package view;

import javax.swing.*;
import java.awt.*;

public class ButtonsActions extends DefaultCellEditor {
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private JTable table;
    private int currentRow;
    private Object[] rowData;
    private String id;

    public Object[] getRowData() {
        return rowData;
    }


    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
    public String getId() {
        return id;
    }

    public ButtonsActions(JCheckBox checkBox, JTable table ){
        super(checkBox);
        this.table = table;


        // Create Edit button
        editButton = new JButton("Edit");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        editButton.setBackground(new Color(52, 152, 219)); // Blue color
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Create Delete button
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setBackground(new Color(231, 76, 60)); // Red color
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add action listeners


        // Create a panel to hold the buttons
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBackground(new Color(236, 240, 241)); // Match the background color
        panel.add(editButton);
        panel.add(deleteButton);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row; // Store the current row index

        // Save the row data to the instance variable

        rowData = new Object[table.getColumnCount()-1];
        for (int col = 0; col < table.getColumnCount()-1; col++) {
            rowData[col] = table.getModel().getValueAt(row, col+1);
        }
        id= (String)table.getModel().getValueAt(row, 0);

        System.out.println("Row data saved for row: " + row);
        return panel; // Return the panel containing the buttons
    }

    @Override
    public Object getCellEditorValue() {
        return ""; // Return an empty string (no value is edited)
    }

    @Override
    public boolean stopCellEditing() {
        // Stop editing when a button is clicked
        return super.stopCellEditing();
    }
}
