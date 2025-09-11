package io.github.tdminhnhat.gui;

import javax.swing.*;
import java.awt.*;

public class HomeApplicationGUI extends JFrame {

    private final JPanel userControlPanel = new UserControlPanel();
    private final JPanel listEntitiesPanel = new ListEntitiesPanel();

    public HomeApplicationGUI(Integer width, Integer height) throws HeadlessException {
        this.setLayout(new BorderLayout());
        this.setTitle("Generate Database GUI Application");
        this.setSize(width == null ? 1400 : width, height == null ? 600 : height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.add(userControlPanel, BorderLayout.WEST);
        this.add(listEntitiesPanel, BorderLayout.CENTER);
    }

    public HomeApplicationGUI() {
        this(1400, 600);
    }
}
