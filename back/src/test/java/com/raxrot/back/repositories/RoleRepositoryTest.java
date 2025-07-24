package com.raxrot.back.repositories;

import com.raxrot.back.models.AppRole;
import com.raxrot.back.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void saveRole() {
        Role role = new Role(AppRole.ROLE_USER);
        Role saved = roleRepository.save(role);
        assertThat(saved.getRoleId()).isNotNull();
        assertThat(saved.getRoleName()).isEqualTo(AppRole.ROLE_USER);
    }

    @Test
    void findByRoleName() {
        roleRepository.save(new Role(AppRole.ROLE_ADMIN));
        Optional<Role> result = roleRepository.findByRoleName(AppRole.ROLE_ADMIN);
        assertThat(result).isPresent();
    }

    @Test
    void roleNotFound() {
        Optional<Role> result = roleRepository.findByRoleName(AppRole.ROLE_ADMIN);
        assertThat(result).isNotPresent();
    }
}