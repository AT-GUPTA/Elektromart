package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FileService {

    private final ResourceLoader resourceLoader;
    private final Path externalFilePath;

    public FileService(ResourceLoader resourceLoader, @Value("${file.userdata.path}") String filepath) {
        this.resourceLoader = resourceLoader;
        this.externalFilePath = Paths.get(filepath).toAbsolutePath();
        initializeExternalFile();
    }

    private void initializeExternalFile() {
        try {
            if (Files.notExists(externalFilePath)) {
                // Copying the template file from the resources directory
                Path resourcePath = Paths.get(resourceLoader.getResource("classpath:users.json").getURI());
                Files.copy(resourcePath, externalFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize external users file", e);
        }
    }

    public void writeUserToFile(User newUser, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Reading the current array of users from the file
            List<Map<String, String>> users;
            if (Files.exists(externalFilePath)) {
                users = mapper.readValue(Files.readString(externalFilePath), new TypeReference<>() {});
            } else {
                users = new ArrayList<>();
            }

            // Adding the new user
            Map<String, String> userData = new HashMap<>();
            userData.put("username", newUser.getUsername());
            userData.put("email", newUser.getEmail());
            userData.put("password", password); // WARNING: NOT SECURE

            users.add(userData);

            // Writing the updated user list back to the file
            Files.writeString(externalFilePath, mapper.writeValueAsString(users), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        } catch (IOException e) {
            log.error("writeUserToFile: Failed to append user details to JSON file.", e);
        }
    }
}
