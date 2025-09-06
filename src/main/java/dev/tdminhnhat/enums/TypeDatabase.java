package dev.tdminhnhat.enums;

public enum TypeDatabase {

    SQL_SERVER("jdbc:sqlserver://{HOST}:{PORT};databaseName={DATABASE_NAME}", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "localhost", 1433),
    MARIADB("jdbc:mariadb://{HOST}:{PORT}/{DATABASE_NAME}", "org.mariadb.jdbc.Driver", "localhost", 3306),
    MYSQL("jdbc:mysql://{HOST}:{PORT}/{DATABASE_NAME}", "com.mysql.cj.jdbc.Driver", "localhost", 3306),
    POSTGRESQL("jdbc:postgresql://{HOST}:{PORT}/{DATABASE_NAME}", "org.postgresql.Driver", "localhost", 5432),
    ORACLE("jdbc:oracle:thin:@{HOST}:{PORT}:{DATABASE_NAME}", "oracle.jdbc.OracleDriver", "localhost", 1521)
    ;

    private final String url;
    private final String driver;
    private final String host;
    private final Integer port;

    TypeDatabase(String url, String driver, String host, Integer port) {
        this.url = url;
        this.driver = driver;
        this.host = host;
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
