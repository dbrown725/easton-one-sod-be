package sod.eastonone.music.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import sod.eastonone.music.auth.models.SecurityProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Autowired
    SecurityProperties secProps;

    @Primary
    @Bean
    public void firebaseInit() throws IOException{
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("firebase_config.json").getInputStream();
        } catch (IOException e3) {
            e3.printStackTrace();
            throw e3;
        }
        try {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
