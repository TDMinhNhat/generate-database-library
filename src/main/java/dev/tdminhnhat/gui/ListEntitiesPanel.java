package dev.tdminhnhat.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ListEntitiesPanel extends JPanel {

    private final String[] columnNames = {"ID", "Table Name", "Entity Name", "Number of Fields", "Number of Columns", "Package Name", "Actions"};

    public ListEntitiesPanel() {
        this.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}
