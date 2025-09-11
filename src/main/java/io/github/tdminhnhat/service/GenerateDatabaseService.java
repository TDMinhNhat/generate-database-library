package io.github.tdminhnhat.service;

import io.github.classgraph.*;
import io.github.tdminhnhat.entity.DatabaseInformation;
import io.github.tdminhnhat.enums.TypeDatabase;
import io.github.tdminhnhat.gui.HomeApplicationGUI;
import jakarta.persistence.EntityManager;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;

import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

/**
 * Provide some functionalities to generate database.
 *
 * @author Nhat Truong
 * @since 0.0.1-beta
 */
public class GenerateDatabaseService {

    /**
     * Show the main GUI of the application.
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
     */
    public static EntityManager generateDatabase(DatabaseInformation databaseInformation, List<Class<?>> classes) {
        return HibernateUtil.getSessionFactory(databaseInformation, classes).createEntityManager();
    }

    /**
     * Export classes into your directory folder. Can use all those classes in your personal projects. Remember change your package name, path each classes after export
     * successfully
     *
     * @param pathSave the directory folder which all the classes will be exported in there.
     * @param packageScanning point the directory package to scan, can be scanned and exported the classes inside the sub-package.
     * @return {@link String} the message of the export class functionality, if the message is <b>null</b>, it has exported the classes successfully and else you will get a message error for failing export.
     */
    public static String exportClass(String pathSave, String packageScanning) {
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .acceptPackages(packageScanning)
                .scan()) {

            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Resource resource = classInfo.getResource();
                if (resource == null) continue;

                // Export superclass if exists
                if (classInfo.getSuperclass() != null) {
                    exportSubClass(pathSave, classInfo.getSuperclass());
                }

                // Export this class
                extracted(pathSave, classInfo, resource);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return null; // success
    }

    private static void exportSubClass(String pathSave, ClassInfo getSuperClass) throws Exception {
        Resource resource = getSuperClass.getResource();
        if (resource == null) return;

        extracted(pathSave, getSuperClass, resource);
    }

    private static void extracted(String pathSave, ClassInfo classInfo, Resource resource) throws IOException {
        // 1. Save class bytes temporarily
        String className = classInfo.getSimpleName();
        File tempClassFile = File.createTempFile(className, ".class");
        try (
                InputStream is = resource.open();
                OutputStream os = new FileOutputStream(tempClassFile)
        ) {
            is.transferTo(os);
        }

        // 2. Decompile with CFR
        StringBuilder javaOutput = new StringBuilder();
        OutputSinkFactory sinkFactory = getOutputSinkFactory(javaOutput);

        Map<String, String> options = new HashMap<>() {{
            put("comments", "false");
            put("silent", "true");
        }};

        CfrDriver driver = new CfrDriver.Builder()
                .withOutputSink(sinkFactory)
                .withOptions(options)
                .build();

        driver.analyse(Collections.singletonList(tempClassFile.getAbsolutePath()));
        tempClassFile.delete();

        // 3. Clean and save the .java file
        String cleanedJava = cleanJavaOutput(javaOutput.toString());
        File outputJavaFile = new File(pathSave, className + ".java");
        outputJavaFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputJavaFile)) {
            writer.write(cleanedJava);
        }
    }

    private static OutputSinkFactory getOutputSinkFactory(StringBuilder javaOutput) {
        return new OutputSinkFactory() {
            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                if (sinkType == SinkType.JAVA) {
                    return List.of(SinkClass.STRING);
                }
                return Collections.emptyList(); // ignore errors, progress, etc.
            }

            @Override
            public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
                return (T t) -> {
                    if (sinkType == SinkType.JAVA && sinkClass == SinkClass.STRING) {
                        javaOutput.append(t.toString()).append(System.lineSeparator());
                    }
                    // Ignore others (like SinkType.PROGRESS, SinkType.ERROR)
                };
            }
        };
    }

    private static String cleanJavaOutput(String code) {
        StringBuilder cleaned = new StringBuilder();
        String[] lines = code.split("\n");

        boolean inBlockComment = false;

        for (String line : lines) {
            String trimmed = line.trim();

            // Remove package declaration
            if (trimmed.startsWith("package ")) continue;

            // Skip block comments
            if (trimmed.startsWith("/*")) {
                inBlockComment = true;
                continue;
            }
            if (trimmed.endsWith("*/")) {
                inBlockComment = false;
                continue;
            }
            if (inBlockComment) continue;

            // Skip single-line comments
            if (trimmed.startsWith("//")) continue;

            // Skip any leftover CFR version/comment lines
            if (trimmed.contains("Decompiled with CFR")) continue;

            cleaned.append(line).append(System.lineSeparator());
        }

        return cleaned.toString();
    }
}
