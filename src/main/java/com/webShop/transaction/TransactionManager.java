package com.webShop.transaction;

import java.sql.SQLException;

public interface TransactionManager {
    <T> T doInTransaction(Operation<T> operation);
}
