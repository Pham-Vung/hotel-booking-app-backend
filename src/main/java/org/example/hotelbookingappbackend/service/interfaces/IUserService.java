package org.example.hotelbookingappbackend.service.interfaces;

import org.example.hotelbookingappbackend.entity.User;

import java.util.List;

public interface IUserService {
    User resgisterUser(User user);
    List<User> getAllUsers();
    void deleteUser(String email);
    User getUser(String email);
}
