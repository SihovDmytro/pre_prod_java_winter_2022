package com.webShop.transaction.impl;

import com.webShop.captcha.strategy.impl.CaptchaProviderCookieStrategyImpl;
import com.webShop.transaction.Operation;
import com.webShop.transaction.TransactionManager;
import com.webShop.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {
    private DataSource dataSource;
    private static final Logger LOG = LogManager.getLogger(TransactionManagerImpl.class);

    public TransactionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> T doInTransaction(Operation<T> operation) {
        Connection connection = null;
        Object result = null;
        try {
            connection = dataSource.getConnection();

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            result = operation.execute(connection);

            connection.commit();
        } catch (SQLException exception) {
            LOG.error("Cannot execute operation", exception);
            rollback(connection);
        } finally {
            DBUtils.closeConnection(connection);
        }
        return (T) result;
    }

    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException exception) {
            LOG.error("Cannot rollback", exception);
        }
    }
}
