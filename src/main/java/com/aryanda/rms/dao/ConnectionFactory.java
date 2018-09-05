package com.aryanda.rms.dao;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

// IGNORE THIS CLASS (TESTING PURPOSES)
public class ConnectionFactory {

    private static volatile ConnectionFactory connectionFactory;
    private static final String PROP_NAME_DB = "database.properties";

    public Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ClassLoader cl = DataSourceFactory.class.getClassLoader();
        Properties properties = new Properties();
        try (InputStream inputStream = cl.getResourceAsStream(PROP_NAME_DB)) {
            if (inputStream != null)
                properties.load(inputStream);
            else
                throw new IOException("Resource file not found");

            ds.setServerName(properties.getProperty("db_host"));
            ds.setPortNumber(Integer.parseInt(properties.getProperty("db_port")));
            ds.setUser(properties.getProperty("db_user"));
            ds.setPassword(properties.getProperty("db_pass"));
            ds.setDatabaseName(properties.getProperty("db_name"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ds.getConnection();
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            synchronized (ConnectionFactory.class) {
                if (connectionFactory == null)
                    connectionFactory = new ConnectionFactory();
            }
        }
        return connectionFactory;
    }

}
