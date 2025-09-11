package io.github.tdminhnhat.gui;

import io.github.tdminhnhat.entity.EntityInformation;
import io.github.tdminhnhat.service.TopicService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class ShowDetailClassGUI extends JFrame {

    private static final String[] columnNames = {"ID", "Entity Name", "Supper Entity", "Package Name", "Count Field", "Is Entity", "Count Foreign Key"};
    private static final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(tableModel);
    private final JScrollPane spTable = new JScrollPane(table);
    private JPanel pnClassContentPanel = new ClassContentPanel();

    public ShowDetailClassGUI(String username, String topic) throws HeadlessException {
        this.setTitle("View Detail Classes");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1400, 800);
        spTable.setPreferredSize(new Dimension(800, 800));
        this.add(spTable, BorderLayout.WEST);
        this.add(pnClassContentPanel, BorderLayout.CENTER);
        this.setVisible(true);

        addDataToTable(username, topic);
        defaultSetting();
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String getPackage = table.getValueAt(
                        table.getSelectedRow(),
                        3
                ).toString();

                String getClassName = table.getValueAt(
                        table.getSelectedRow(),
                        1
                ).toString();
                table.setEnabled(false);
                new Thread(() -> {
                    try {
                        ClassContentPanel.setContentClass(TopicService.getContentClass(getPackage, getClassName));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Show Detail Class", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        table.setEnabled(true);
                    }
                }).start();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
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
    }


    private void addDataToTable(String username, String topic) {
        removeAllRows();

        List<EntityInformation> data = TopicService.getListClassTopic(username, topic);
        data.stream().map(entityInformation -> new String[]{
                String.valueOf(tableModel.getRowCount() + 1),
                entityInformation.getClassName(),
                entityInformation.getSubClassName(),
                entityInformation.getPackageName(),
                String.valueOf(entityInformation.getNumberOfFields()),
                entityInformation.getIsEntity().toString(),
                String.valueOf(entityInformation.getCountFK())
        }).forEach(tableModel::addRow);

        table.repaint();
    }

    private void removeAllRows() {
        if(table.getRowCount() > 0) {
            tableModel.removeRow(0);
            removeAllRows();
        }
    }
}
