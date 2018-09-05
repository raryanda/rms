package com.aryanda.rms;

import com.aryanda.rms.dao.DataSourceFactory;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class DataSourceFactoryTest {

    @Test
    public void getConnection() throws SQLException {
        try (Connection conn = DataSourceFactory.getConnection()) {
            assertThat(conn.isClosed(), is(false));
        }
    }
}
