package com.example.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("Tests du contrôleur UserController")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IUserService IUserService;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "Ahmed", "ahmed@email.com");
    }

    @Test
    @DisplayName("POST /api/users - Créer un utilisateur")
    void createUser_Success() throws Exception {
        when(IUserService.saveUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ahmed"))
                .andExpect(jsonPath("$.email").value("ahmed@email.com"));
    }
    @Test
    @DisplayName("GET /api/users - Liste des utilisateurs")
    void getAllUsers_Success() throws Exception {
        List<User> users = Arrays.asList(
                user,
                new User(2, "Fatma", "fatma@email.com")
        );
        when(IUserService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("Ahmed"));
    }
    @Test
    @DisplayName("GET /api/users/{id} - Utilisateur trouvé")
    void getUserById_Found() throws Exception {
        when(IUserService.getUserById(1)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ahmed"));
    }
    @Test
    @DisplayName("GET /api/users/{id} - Utilisateur non trouvé")
    void getUserById_NotFound() throws Exception {
        when(IUserService.getUserById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("PUT /api/users/{id} - Mise à jour réussie")
    void updateUser_Success() throws Exception {
        User updated = new User(1, "Ahmed Updated", "new@email.com");
        when(IUserService.updateUser(eq(1), any(User.class))).thenReturn(updated);
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ahmed Updated"));
    }
    @Test
    @DisplayName("DELETE /api/users/{id} - Suppression")
    void deleteUser_Success() throws Exception {
        doNothing().when(IUserService).deleteUser(1);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}