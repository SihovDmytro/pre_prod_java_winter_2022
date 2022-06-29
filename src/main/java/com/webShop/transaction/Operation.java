package com.webShop.transaction;

import java.sql.Connection;

public interface Operation<T> {
    T execute(Connection connection);
}
