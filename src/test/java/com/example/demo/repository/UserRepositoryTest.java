package com.example.demo.repository;

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
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Doit sauvegarder et retrouver un utilisateur")
    void saveAndFind() {
        User user = new User("Ahmed", "ahmed@email.com");
        userRepository.save(user);

        Optional<User> found = userRepository.findById(user.getId()); // utilise l’ID généré
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("Ahmed");
    }

    @Test
    @DisplayName("Doit retourner tous les utilisateurs")
    void findAll() {
        userRepository.save(new User("Ahmed", "ahmed@email.com"));
        userRepository.save(new User("Fatma", "fatma@email.com"));

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Doit supprimer un utilisateur")
    void deleteUser() {
        User user = userRepository.save(new User("Ahmed", "ahmed@email.com"));
        userRepository.deleteById(user.getId());

        assertThat(userRepository.existsById(user.getId())).isFalse();
    }

    @Test
    @DisplayName("Doit mettre à jour un utilisateur")
    void updateUser() {
        User user = userRepository.save(new User("Ahmed", "ahmed@email.com"));
        user.setUsername("Ahmed Updated");
        userRepository.save(user);

        Optional<User> updated = userRepository.findById(user.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getUsername()).isEqualTo("Ahmed Updated");
    }
}
