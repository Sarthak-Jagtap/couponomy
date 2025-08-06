package com.superx.Dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import java.io.FileInputStream;
import java.io.File;
import java.nio.file.Files;

public class FirebaseImage {

    private static final String BUCKET_NAME = "coupon-ff257.firebasestorage.app";

    public static String uploadImage(String localFilePath, String destinationPathInStorage) {
        try {
            File file = new File(localFilePath);

            if (!file.exists()) {
                System.err.println("File does not exist: " + localFilePath);
                return null;
            }

            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials
                            .fromStream(new FileInputStream("src/main/resources/firebasePrivateKey.json")))
                    .build()
                    .getService();

            BlobId blobId = BlobId.of(BUCKET_NAME, destinationPathInStorage);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(Files.probeContentType(file.toPath()))
                    .build();

            storage.create(blobInfo, Files.readAllBytes(file.toPath()));

            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + destinationPathInStorage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
