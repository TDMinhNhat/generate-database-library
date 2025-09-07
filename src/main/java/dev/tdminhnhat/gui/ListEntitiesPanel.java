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
    private static final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
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

        DefaultTableCellRenderer viewRenderColumn = new DefaultTableCellRenderer();


        table.setRowHeight(30);

        table.getColumn("ID").setMaxWidth(30);
        table.getColumn("ID").setCellRenderer(globalRenderColumn);
        table.getColumn("ID").setResizable(false);

        table.getColumn("Count Field").setCellRenderer(globalRenderColumn);
        table.getColumn("Count Field").setMaxWidth(100);
        table.getColumn("Count Field").setResizable(false);

        table.getColumn("Is Entity").setCellRenderer(globalRenderColumn);
        table.getColumn("Is Entity").setMaxWidth(100);
        table.getColumn("Is Entity").setResizable(false);

        table.getColumn("Count Foreign Key").setCellRenderer(globalRenderColumn);
        table.getColumn("Count Foreign Key").setPreferredWidth(75);
        table.getColumn("Count Foreign Key").setResizable(false);

        table.getColumn("Actions").setCellRenderer(viewRenderColumn);
        table.getColumn("Actions").setMaxWidth(75);
        table.getColumn("Actions").setResizable(false);
    }

    public static void addDataToTable(String username, String topic) {
        List<EntityInformation> datas = TopicService.getListClassTopic(username, topic);
        datas.stream().map(entityInformation -> new String[]{
                String.valueOf(tableModel.getRowCount() + 1),
                entityInformation.getClassName(),
                entityInformation.getSubClassName(),
                entityInformation.getPackageName(),
                String.valueOf(entityInformation.getNumberOfFields()),
                entityInformation.getIsEntity().toString(),
                String.valueOf(entityInformation.getForeignClasses().size()),
                "View"
        }).forEach(tableModel::addRow);
    }
}
