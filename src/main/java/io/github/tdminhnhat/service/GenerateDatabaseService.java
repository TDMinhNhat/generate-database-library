package io.github.tdminhnhat.service;

import io.github.tdminhnhat.entity.DatabaseInformation;
import io.github.tdminhnhat.enums.TypeDatabase;
import io.github.tdminhnhat.gui.HomeApplicationGUI;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * Provide some functionalities to generate database.
 *
 * @author Nhat Truong
 * @since 0.0.1-beta
 */
public class GenerateDatabaseService {

    /**
     * Show the main GUI of the application.
     * @since v0.0.1-beta
     */
    public static void showGUI() {
        SwingUtilities.invokeLater(HomeApplicationGUI::new);
    }

    /**
     * Testing the connection to database. If successfully, the function will return <b>true</b>
     * else it's return <b>false</b> if there are some errors when trying to connect. Make sure you
     * have to create database first and test this connect later.
     * @param databaseInformation {@link DatabaseInformation}
     * @return {@link Boolean} check the connection between the app with database server. <b>True</b> -
     * your app connects to database system successfully else <b>False</b> for failing to connect
     * @since 0.0.1-beta
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

    /**
     * For generating tables, columns into your database server. To make sure generating successfully, you might have to
     * check the connection before you call this function.
     *
     * @param databaseInformation the database information for being set up to connect and generate
     * @param classes list of the classes mapping to table in database system
     * @return {@link EntityManager}
     * @since 0.0.1-beta
     */
    public static EntityManager generateDatabase(DatabaseInformation databaseInformation, List<Class<?>> classes) {
        return HibernateUtil.getSessionFactory(databaseInformation, classes).createEntityManager();
    }
}
