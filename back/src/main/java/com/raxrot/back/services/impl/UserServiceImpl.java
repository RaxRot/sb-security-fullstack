package com.raxrot.back.services.impl;

import com.raxrot.back.dtos.UserDTO;
import com.raxrot.back.exceptions.ApiException;
import com.raxrot.back.models.AppRole;
import com.raxrot.back.models.Role;
import com.raxrot.back.models.User;
import com.raxrot.back.repositories.RoleRepository;
import com.raxrot.back.repositories.UserRepository;
import com.raxrot.back.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found"));

        AppRole appRole;
        try {
             appRole = AppRole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new ApiException("Invalid role name");
        }

        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new ApiException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
       List<User>users = userRepository.findAll();
       List<UserDTO> userDTOs = users.stream()
               .map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
       return userDTOs;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApiException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public User findByUsername(String username) {
        User user=userRepository.findByUserName(username).orElseThrow(() -> new ApiException("User not found"));
        return user;
    }
}
