package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests du repository User")
class UserRepositoryTest {
    @Autowired
    private UserRepository UserRepository;

    @BeforeEach
    void setUp() {
        UserRepository.deleteAll();
    }

    @Test
    @DisplayName("Doit sauvegarder et retrouver un utilisateur")
    void saveAndFind() {
        User user = new User(1, "Ahmed", "ahmed@email.com");
        UserRepository.save(user);
        Optional<User> found = UserRepository.findById(1);
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("Ahmed");
    }

    @Test
    @DisplayName("Doit retourner tous les utilisateurs")
    void findAll() {
        UserRepository.save(new User(1, "Ahmed", "ahmed@email.com"));
        UserRepository.save(new User(2, "Fatma", "fatma@email.com"));
        List<User> users = UserRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Doit supprimer un utilisateur")
    void deleteUser() {
        User user = UserRepository.save(new User(1, "Ahmed", "ahmed@email.com"));
        UserRepository.deleteById(user.getId());
        assertThat(UserRepository.existsById(1)).isFalse();
    }

    @Test
    @DisplayName("Doit mettre à jour un utilisateur")
    void updateUser() {
        User user = UserRepository.save(new User(1, "Ahmed", "ahmed@email.com"));
        user.setUsername("Ahmed Updated");
        UserRepository.save(user);
        Optional<User> updated = UserRepository.findById(1);
        assertThat(updated.get().getUsername()).isEqualTo("Ahmed Updated");
    }
}
