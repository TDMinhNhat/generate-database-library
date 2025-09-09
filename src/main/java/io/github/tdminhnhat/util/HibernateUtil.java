package io.github.tdminhnhat.util;

import io.github.tdminhnhat.entity.DatabaseInformation;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;

public class HibernateUtil {

    public static SessionFactory getSessionFactory(DatabaseInformation databaseInformation, List<Class<?>> classes) {
        Configuration configuration = new Configuration();

        // Configuration JPA Hibernate properties
        Properties properties = new Properties();
        properties.setProperty(Environment.JAKARTA_JDBC_DRIVER, databaseInformation.getTypeDatabase().getDriver());
        properties.setProperty(Environment.JAKARTA_JDBC_URL, databaseInformation.getTypeDatabase().getUrl()
                .replace("{HOST}", databaseInformation.getHost())
                .replace("{PORT}", databaseInformation.getPort() + "")
                .replace("{DATABASE_NAME}", databaseInformation.getDatabaseName())
        );
        properties.setProperty(Environment.JAKARTA_JDBC_USER, databaseInformation.getUsername());
        properties.setProperty(Environment.JAKARTA_JDBC_PASSWORD, databaseInformation.getPassword());
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty(Environment.HBM2DDL_AUTO, "create");
        configuration.setProperties(properties);

        // Configuration Annotated Classes to generate
        classes.forEach(configuration::addAnnotatedClass);

        // Configuration Service Registry
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
