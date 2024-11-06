package com.example.medicalrecordservice.helper;

import com.example.medicalrecordservice.exception.common.InternalServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class Mkb10DictionaryStaticHashHelper {
    private final static Logger logger = LoggerFactory.getLogger(Mkb10DictionaryStaticHashHelper.class);
    public static String calculateFileHash(String pathToFile){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            Path filePath = Path.of(pathToFile);

            try(FileInputStream fis = new FileInputStream(filePath.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            } catch (FileNotFoundException exception){
                logger.debug("Attempt to read non-existent file");
                return "0";
            }
            catch (IOException exception){
                throw new InternalServerException(exception.getMessage());
            }

            byte[] hashBytes = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException exception){
            throw new InternalServerException(exception.getMessage());
        }

    }

    public static void putHashToJsonFile(String pathToFile, String hash){
        try {
            Path jsonFile = Path.of(pathToFile);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode;

            if (Files.exists(jsonFile)) {
                jsonNode = (ObjectNode) objectMapper.readTree(Files.newBufferedReader(jsonFile));
            } else {
                jsonNode = objectMapper.createObjectNode();
            }

            jsonNode.put("hash_of_previous_file", hash);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(Files.newBufferedWriter(jsonFile), jsonNode);
        } catch (IOException exception){
            logger.error("Error loading file: " + exception.getMessage());
        }
    }

    public static String readJsonHashValue(String pathToFile){
        try {
            Path jsonFile = Path.of(pathToFile);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = (ObjectNode) objectMapper.readTree(Files.newBufferedReader(jsonFile));
            return jsonNode.get("hash_of_previous_file").asText();
        } catch (IOException exception){
            throw new InternalServerException(exception.getMessage());
        }
    }
}
