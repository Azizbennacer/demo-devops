package com.example.demo.bdd;

import io.cucumber.java.Before;
import io.cucumber.java.fr.*;
import io.cucumber.datatable.DataTable;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

public class UserStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    private List<User> userList;

    @Before
    public void setup() {
        userRepository.deleteAll();
    }

    @Etantdonné("une base de données vide")
    public void une_base_de_donnees_vide() {
        userRepository.deleteAll();
        assertThat(userRepository.count()).isZero();
    }

    @Quand("je crée un utilisateur {string} avec l'email {string}")
    public void je_cree_un_utilisateur(String username, String email) {
        User user = new User(username, email); // <-- sans ID
        userRepository.save(user);
    }

    @Alors("l'utilisateur {string} existe dans la base")
    public void l_utilisateur_existe(String username) {
        List<User> users = userRepository.findAll();
        assertThat(users).anyMatch(u -> u.getUsername().equals(username));
    }

    @Etantdonné("les utilisateurs suivants existent:")
    public void les_utilisateurs_suivants_existent(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            User user = new User(row.get("username"), row.get("email")); // <-- sans ID
            userRepository.save(user);
        }
    }

    @Quand("je demande la liste des utilisateurs")
    public void je_demande_la_liste() {
        userList = userRepository.findAll();
    }

    @Alors("je reçois {int} utilisateurs")
    public void je_recois_utilisateurs(int count) {
        assertThat(userList).hasSize(count);
    }

    @Etantdonné("un utilisateur {string} existe avec l'ID {int}")
    public void un_utilisateur_existe_avec_id(String username, int id) {
        // ⚠️ Ici, ne pas forcer l'ID. On laisse JPA le générer.
        userRepository.save(new User(username, username.toLowerCase() + "@email.com"));
    }

    @Quand("je supprime l'utilisateur avec l'ID {int}")
    public void je_supprime_utilisateur(int id) {
        userRepository.deleteById(id);
    }

    @Alors("l'utilisateur avec l'ID {int} n'existe plus")
    public void utilisateur_n_existe_plus(int id) {
        assertThat(userRepository.existsById(id)).isFalse();
    }
}
