package io.github.tdminhnhat.entity;

import io.github.tdminhnhat.enums.TypeDatabase;
import lombok.Getter;
import lombok.Setter;

/**
 * This class contain the database information for connecting and generating.
 * @author Nhat Truong
 * @version 1.1.0
 * @since 0.0.1-beta
 */
@Getter @Setter
public class DatabaseInformation {

    /**
     * Host the server. Can apply for domain or ip host.
     */
    private String host;

    /**
     * Port the database server.
     */
    private Integer port;

    /**
     * Username of the database server. If you don't have one, you should create before doing something with DB System.
     */
    private String username;

    /**
     * Password of the database server. Reset the password if you don't remember.
     */
    private String password;

    /**
     * The name of the database use for connecting. You should create first in your database server.
     */
    private String databaseName;

    /**
     * Type of the database which you want to generate in database server.
     */
    private TypeDatabase typeDatabase;

    /**
     * The package contains the classes use for generating.
     */
    private String packageTarget;

    public DatabaseInformation(String host, Integer port, String username, String password, String databaseName, TypeDatabase typeDatabase, String packageTarget) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.typeDatabase = typeDatabase;
        this.packageTarget = packageTarget;
    }
}
