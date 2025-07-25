package com.raxrot.back.services.impl;

import com.raxrot.back.dtos.UserDTO;
import com.raxrot.back.exceptions.ApiException;
import com.raxrot.back.models.AppRole;
import com.raxrot.back.models.Role;
import com.raxrot.back.models.User;
import com.raxrot.back.repositories.RoleRepository;
import com.raxrot.back.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUserName("vlad");
        user.setEmail("vlad@email.com");

        role = new Role();
        role.setRoleName(AppRole.ROLE_USER);

        user.setRole(role);

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUserName("vlad");
        userDTO.setEmail("vlad@email.com");
    }

    @Test
    @DisplayName("Get user by ID — success")
    void getUserById_Success() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo("vlad");
        assertThat(result.getEmail()).isEqualTo("vlad@email.com");
    }

    @Test
    @DisplayName("Get user by ID — user not found")
    void getUserById_NotFound() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(ApiException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Get all users — success")
    void getAllUsers_Success() {
        List<User> users = List.of(user);
        List<UserDTO> userDTOs = List.of(userDTO);

        given(userRepository.findAll()).willReturn(users);
        given(modelMapper.map(user, UserDTO.class)).willReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserName()).isEqualTo("vlad");
    }

    @Test
    @DisplayName("Update user role — success")
    void updateUserRole_Success() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(roleRepository.findByRoleName(AppRole.ROLE_ADMIN)).willReturn(Optional.of(role));

        userService.updateUserRole(1L, "ROLE_ADMIN");

        then(userRepository).should().save(user);
    }

    @Test
    @DisplayName("Update user role — user not found")
    void updateUserRole_UserNotFound() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUserRole(1L, "ROLE_ADMIN"))
                .isInstanceOf(ApiException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Update user role — invalid role name")
    void updateUserRole_InvalidRoleName() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.updateUserRole(1L, "INVALID"))
                .isInstanceOf(ApiException.class)
                .hasMessage("Invalid role name");
    }

    @Test
    @DisplayName("Update user role — role not found in DB")
    void updateUserRole_RoleNotFound() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(roleRepository.findByRoleName(AppRole.ROLE_ADMIN)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUserRole(1L, "ROLE_ADMIN"))
                .isInstanceOf(ApiException.class)
                .hasMessage("Role not found");
    }
}