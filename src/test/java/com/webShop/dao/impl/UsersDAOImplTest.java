//package com.webShop.dao.impl;
//
//import com.task1.subtask2.ArrayList;
//import com.webShop.dao.UsersDAO;
//import com.webShop.entity.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UsersDAOImplTest {
//    private static UsersDAO dao;
//
//
//    @Test
//    public void shouldReturnExistingUserWhenGetUserByLoginMethodCalled() {
//        List<User> users = new ArrayList<>();
//        User user1 = new User("dmytro", "Dmytro", "Sihov",
//                "abrakadabra", "Dmytro_Sihov@epam.com", false);
//        User user2 = new User("vasya", "Vas", "Vass",
//                "123456", "Vasya@epam.com", true);
//        users.add(user1);
//        users.add(user2);
//        dao = new UsersDAOImpl(users);
//
//        User actual = dao.getUserByLogin("vasya").get();
//        Assertions.assertEquals(user2, actual);
//    }
//
//    @Test
//    public void shouldReturnEmptyOptionalWhenGetUserByLoginMethodCalledIfUserDoesNotExist() {
//        dao = new UsersDAOImpl(new ArrayList<>());
//
//        Optional<User> actual = dao.getUserByLogin("vasya");
//        assertFalse(actual.isPresent());
//    }
//}