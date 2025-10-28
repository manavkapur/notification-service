package com.supremesolutions.notificationservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            String base64 = System.getenv("FIREBASE_CREDENTIALS_BASE64");
            if (base64 == null || base64.isEmpty()) {
                System.err.println("⚠️ FIREBASE_CREDENTIALS_BASE64 not found, skipping Firebase init.");
                return;
            }

            byte[] decoded = java.util.Base64.getDecoder().decode(base64);
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decoded));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase initialized successfully!");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to initialize Firebase: " + e.getMessage());
        }
    }
}
