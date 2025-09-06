package dev.tdminhnhat.service;

import dev.tdminhnhat.gui.HomeApplicationGUI;

import javax.swing.*;

public class ApplicationService {

    /**
     * Show the main GUI of the application.
     */
    public void showGUI() {
        SwingUtilities.invokeLater(HomeApplicationGUI::new);
    }
}
