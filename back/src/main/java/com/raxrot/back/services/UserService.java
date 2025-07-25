package com.raxrot.back.services;

import com.raxrot.back.dtos.UserDTO;
import com.raxrot.back.models.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}
