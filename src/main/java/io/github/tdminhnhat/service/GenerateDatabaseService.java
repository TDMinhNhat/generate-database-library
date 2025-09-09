package io.github.tdminhnhat.service;

import io.github.tdminhnhat.entity.DatabaseInformation;
import io.github.tdminhnhat.enums.TypeDatabase;
import io.github.tdminhnhat.gui.HomeApplicationGUI;
import io.github.tdminhnhat.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public static boolean exportEntities(String path, List<Class<?>> classes) {
        System.out.println((long) classes.size());
        for(Class<?> clazz : classes) {
            String resourceName = clazz.getName().replace(".", "/") + ".class";
            URL classFileUrl = clazz.getClassLoader().getResource(resourceName);

            if(classFileUrl == null) {
                throw new RuntimeException("Can't find the locate class");
            }

            try {
                InputStream in = classFileUrl.openStream();
                Path ouputPath = Paths.get(path, clazz.getSimpleName() + ".class");
                Files.copy(in, ouputPath, StandardCopyOption.REPLACE_EXISTING);
                in.close();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
