package io.github.tdminhnhat.gui;

import javax.swing.*;
import java.awt.*;

public class ShowDetailClassGUI extends JFrame {

    private final JPanel pnListEntitiesPanel = new ListEntitiesPanel();
    private final JPanel pnClassContentPanel = new ClassContentPanel(ListEntitiesPanel.getTable());

    public ShowDetailClassGUI() throws HeadlessException {
        this.setTitle("View Detail Classes");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1400, 800);

        pnListEntitiesPanel.setPreferredSize(new Dimension(800, 800));

        this.add(pnListEntitiesPanel, BorderLayout.WEST);
        this.add(pnClassContentPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
