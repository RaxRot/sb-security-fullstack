package com.raxrot.back.repositories;

import com.raxrot.back.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "{noop}pass123");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
    }

    @Test
    void saveUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getUserName()).isEqualTo("testuser");
    }

    @Test
    void findByUserName() {
        userRepository.save(user);
        Optional<User> result = userRepository.findByUserName("testuser");
        assertThat(result).isPresent();
    }

    @Test
    void existsByUserName() {
        userRepository.save(user);
        boolean exists = userRepository.existsByUserName("testuser");
        assertThat(exists).isTrue();
    }

    @Test
    void userNotExists() {
        boolean exists = userRepository.existsByUserName("unknown");
        assertThat(exists).isFalse();
    }
}