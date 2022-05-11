package com.webShop.dao;

import com.webShop.entity.Producer;

import java.sql.Connection;
import java.util.List;

public interface ProducersDAO {
    List<Producer> getAllProducers(Connection connection);
}
