package com.webShop.transaction;

public interface TransactionManager {
    <T> T doInTransaction(Operation<T> operation);
}
