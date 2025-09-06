package dev.tdminhnhat;

import dev.tdminhnhat.gui.HomeApplicationGUI;
import dev.tdminhnhat.service.ApplicationService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new ApplicationService().showGUI();
    }
}