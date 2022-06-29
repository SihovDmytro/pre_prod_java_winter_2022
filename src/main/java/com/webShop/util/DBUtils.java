package com.webShop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private static final Logger LOG = LogManager.getLogger(DBUtils.class);

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            LOG.error("Cannot close connection", exception);
        }
    }
}
