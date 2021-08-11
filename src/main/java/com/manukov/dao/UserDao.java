package com.manukov.dao;

import com.manukov.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserDao {

    User findByUsername(String username);

    List<User> getUsers();
    boolean addUser(User user, String[] ids);
    User findById(long id);
    boolean updateUser(User user, String[] roles);
    boolean deleteUser(long id);
}
