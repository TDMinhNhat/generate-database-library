package io.github.tdminhnhat.entity;

import io.github.tdminhnhat.enums.TypeDatabase;

public class DatabaseInformation {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String databaseName;
    private TypeDatabase typeDatabase;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public TypeDatabase getTypeDatabase() {
        return typeDatabase;
    }

    public void setTypeDatabase(TypeDatabase typeDatabase) {
        this.typeDatabase = typeDatabase;
    }

    public String getPackageTarget() {
        return packageTarget;
    }

    public void setPackageTarget(String packageTarget) {
        this.packageTarget = packageTarget;
    }
}
