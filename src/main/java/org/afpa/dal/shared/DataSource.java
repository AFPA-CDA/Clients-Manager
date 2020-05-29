package org.afpa.dal.shared;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource used for the SQL Connection
 */
public final class DataSource {
    private final static HikariConfig config = new HikariConfig("src/main/resources/org/afpa/datasource.properties");
    private final static HikariDataSource dataSource;

    static {
        dataSource = new HikariDataSource(config);
    }

    private DataSource() { }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            new ExceptionPrinter<>(e).print();
            return null;
        }
    }
}