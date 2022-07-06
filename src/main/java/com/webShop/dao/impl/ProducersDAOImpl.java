package com.webShop.dao.impl;

import com.task1.subtask2.ArrayList;
import com.webShop.dao.ProducersDAO;
import com.webShop.entity.Producer;
import com.webShop.util.SQLCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProducersDAOImpl implements ProducersDAO {
    private static final Logger LOG = LogManager.getLogger(ProducersDAOImpl.class);

    @Override
    public List<Producer> getAllProducers(Connection connection) {
        List<Producer> producers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLCommands.GET_ALL_PRODUCERS)) {
            while (resultSet.next()) {
                producers.add(unmap(resultSet));
            }
        } catch (SQLException exception) {
            LOG.trace("Cannot get all producers");
        }
        return producers;
    }

    private Producer unmap(ResultSet resultSet) throws SQLException {
        return new Producer(resultSet.getString(1));
    }
}
