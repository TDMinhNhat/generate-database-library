package io.github.tdminhnhat.gui;

import io.github.tdminhnhat.service.TopicService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class ClassContentPanel extends JPanel {

    private final JTextArea textArea = new JTextArea();

    public ClassContentPanel(JTable table) {
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBackground(Color.white);
        textArea.setCaretPosition(0);
        add(scrollPane, BorderLayout.CENTER);

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
                new Thread(() -> {
                    try {
                        textArea.setText(TopicService.getContentClass(getPackage, getClassName));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Show Detail Class", JOptionPane.ERROR_MESSAGE);
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

}
