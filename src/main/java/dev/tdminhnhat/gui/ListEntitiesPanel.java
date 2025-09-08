package dev.tdminhnhat.gui;

import dev.tdminhnhat.entity.EntityInformation;
import dev.tdminhnhat.service.TopicService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class ListEntitiesPanel extends JPanel {

    private static final String[] columnNames = {"ID", "Entity Name", "Supper Entity", "Package Name", "Count Field", "Is Entity", "Count Foreign Key", "Actions"};
    private static final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private static final JTable table = new JTable(tableModel);
    public ListEntitiesPanel() {
        this.setLayout(new BorderLayout());

        defaultSetting();

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void defaultSetting() {
        DefaultTableCellRenderer globalRenderColumn = new DefaultTableCellRenderer();
        globalRenderColumn.setHorizontalAlignment(JLabel.CENTER);

        table.setRowHeight(30);

        table.getColumn("ID").setMaxWidth(30);
        table.getColumn("ID").setCellRenderer(globalRenderColumn);
        table.getColumn("ID").setResizable(false);

        table.getColumn("Entity Name").setMaxWidth(125);
        table.getColumn("Entity Name").setMinWidth(125);

        table.getColumn("Supper Entity").setMaxWidth(125);
        table.getColumn("Supper Entity").setMinWidth(125);

        table.getColumn("Count Field").setCellRenderer(globalRenderColumn);
        table.getColumn("Count Field").setMaxWidth(100);
        table.getColumn("Count Field").setResizable(false);

        table.getColumn("Is Entity").setCellRenderer(globalRenderColumn);
        table.getColumn("Is Entity").setMaxWidth(100);
        table.getColumn("Is Entity").setResizable(false);

        table.getColumn("Count Foreign Key").setCellRenderer(globalRenderColumn);
        table.getColumn("Count Foreign Key").setMinWidth(125);
        table.getColumn("Count Foreign Key").setMaxWidth(125);
        table.getColumn("Count Foreign Key").setResizable(false);

//        table.getColumn("Actions").setCellRenderer(viewRenderColumn);
        table.getColumn("Actions").setMaxWidth(75);
        table.getColumn("Actions").setResizable(false);
    }

    public static void addDataToTable(String username, String topic) {
        removeAllRows();

        List<EntityInformation> data = TopicService.getListClassTopic(username, topic);
        data.stream().map(entityInformation -> new String[]{
                String.valueOf(tableModel.getRowCount() + 1),
                entityInformation.getClassName(),
                entityInformation.getSubClassName(),
                entityInformation.getPackageName(),
                String.valueOf(entityInformation.getNumberOfFields()),
                entityInformation.getIsEntity().toString(),
                String.valueOf(entityInformation.getCountFK()),
                "View"
        }).forEach(tableModel::addRow);

        table.repaint();
    }

    private static void removeAllRows() {
        if(table.getRowCount() > 0) {
            tableModel.removeRow(0);
            removeAllRows();
        }
    }
}
