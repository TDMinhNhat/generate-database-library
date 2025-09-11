package io.github.tdminhnhat.gui;

import javax.swing.*;
import java.awt.*;

public class ClassContentPanel extends JPanel {

    private static final JTextArea textArea = new JTextArea();
    public ClassContentPanel() {
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textArea.setEditable(false);
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBackground(Color.white);
        textArea.setCaretPosition(0);
        textArea.setText("");
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void setContentClass(String content) {
        textArea.setText(content);
    }
}
