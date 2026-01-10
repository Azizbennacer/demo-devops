// src/test/java/com/example/demo/SimpleContextTest.java
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SimpleContextTest {

    @Test
    void contextLoads() {
        // Si ce test passe, le contexte Spring démarre bien
    }
}