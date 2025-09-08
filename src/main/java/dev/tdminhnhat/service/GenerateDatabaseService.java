package dev.tdminhnhat.service;

import dev.tdminhnhat.entity.DatabaseInformation;
import dev.tdminhnhat.enums.TypeDatabase;
import dev.tdminhnhat.gui.HomeApplicationGUI;
import dev.tdminhnhat.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class GenerateDatabaseService {

    /**
     * Show the main GUI of the application.
     */
    public static void showGUI() {
        SwingUtilities.invokeLater(HomeApplicationGUI::new);
    }

    /**
     * Testing the connection to database. If successfully, the function will return <b>true</b>
     * else it's return <b>false</b> if there are some errors when trying to connect.
     * @param databaseInformation {@link DatabaseInformation}
     * @return boolean
     */
    public static boolean testConnection(DatabaseInformation databaseInformation) {
        try {
            TypeDatabase typeDatabase = databaseInformation.getTypeDatabase();
            Connection connection = DriverManager.getConnection(
                    typeDatabase.getUrl().replace("{HOST}", databaseInformation.getHost())
                            .replace("{PORT}", databaseInformation.getPort().toString())
                            .replace("{DATABASE_NAME}", databaseInformation.getDatabaseName())
                    , databaseInformation.getUsername(), databaseInformation.getPassword());
            connection.beginRequest();
            connection.close();
            return true;
        } catch (Exception ex) {
            if(ex.getMessage().contains("Unknown database")) {
                JOptionPane.showMessageDialog(null, "The database name " + databaseInformation.getDatabaseName() + " wasn't found! Please creat a new one and run again.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            } else JOptionPane.showMessageDialog(null, ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static EntityManager generateDatabase(DatabaseInformation databaseInformation, List<Class<?>> classes) {
        return HibernateUtil.getSessionFactory(databaseInformation, classes).createEntityManager();
    }

    public static void exportEntities() {

    }
}
