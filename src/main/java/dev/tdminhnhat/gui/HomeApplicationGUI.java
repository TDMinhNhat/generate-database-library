package dev.tdminhnhat.gui;

import javax.swing.*;
import java.awt.*;

public class HomeApplicationGUI extends JFrame {

    private final JPanel userControlPanel = new UserControlPanel();
    private final JPanel listEntitiesPanel = new ListEntitiesPanel();

    public HomeApplicationGUI() throws HeadlessException {
        this.setLayout(new BorderLayout());
        this.setTitle("Generate Database GUI Application");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.add(userControlPanel, BorderLayout.NORTH);
        this.add(listEntitiesPanel, BorderLayout.CENTER);
    }
}
