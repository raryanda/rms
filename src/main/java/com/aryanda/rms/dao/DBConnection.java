package com.aryanda.rms.dao;

import java.sql.Connection;
import java.sql.SQLException;

// IGNORE THIS CLASS (TESTING PURPOSES)
public class DBConnection {
    private Connection conn;

    public DBConnection() throws SQLException {
        this.conn = ConnectionFactory.getInstance().getConnection();
    }

    public void close() {
        if (this.conn != null)
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
