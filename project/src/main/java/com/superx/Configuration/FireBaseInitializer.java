package com.superx.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.superx.Dao.CouponInfoDAO;

public class FireBaseInitializer {

    static {
        try {
            initializerFireBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to initialize firebase
    private static void initializerFireBase() throws IOException {
        FileInputStream serviceAccount_Cpm = new FileInputStream("src/main/resources/firebasePrivateKey.json");

        FirebaseOptions options_Cpm = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount_Cpm))
                .build();

        FirebaseApp.initializeApp(options_Cpm);

        Firestore db = FirestoreClient.getFirestore();

        CouponInfoDAO.couponDatabase = db;
    }

}
