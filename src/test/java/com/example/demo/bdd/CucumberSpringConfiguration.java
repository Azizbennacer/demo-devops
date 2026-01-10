package com.example.demo.bdd;

import com.example.demo.DemoDevopsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@SpringBootTest(classes = DemoDevopsApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // ← AJOUTE CETTE LIGNE
public class CucumberSpringConfiguration {
    // Vide ou avec @BeforeAll/@AfterAll
}
