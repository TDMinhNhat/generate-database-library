package dev.tdminhnhat;

import dev.tdminhnhat.gui.HomeApplicationGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeApplicationGUI::new);
    }
}