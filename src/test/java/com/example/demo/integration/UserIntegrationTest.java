package com.example.demo.integration;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Tests d'intégration User")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Scénario complet: CRUD utilisateur")
    void fullCrudScenario() throws Exception {
        // 1. CREATE
        User newUser = new User("Ahmed", "ahmed@email.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ahmed"));

        // Vérifier en base
        assertThat(userRepository.count()).isEqualTo(1);

        // Récupérer l’ID généré
        User saved = userRepository.findAll().get(0);
        int id = saved.getId();

        // 2. READ
        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ahmed@email.com"));

        // 3. UPDATE
        User updated = new User("Ahmed Updated", "new@email.com");
        mockMvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ahmed Updated"));

        // 4. DELETE
        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());
        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("Test de performance: création de 100 utilisateurs")
    void performanceTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            User user = new User("User" + i, "user" + i + "@test.com");
            userRepository.save(user); // pas de setId()
        }
        long duration = System.currentTimeMillis() - start;
        assertThat(userRepository.count()).isEqualTo(100);
        assertThat(duration).isLessThan(10000); // < 10 secondes
    }
}
