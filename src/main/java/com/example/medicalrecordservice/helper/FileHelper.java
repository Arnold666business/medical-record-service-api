package com.example.medicalrecordservice.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class FileHelper {
    private final static Logger logger = LoggerFactory.getLogger(FileHelper.class);

    private FileHelper() {
    }

    public static void downloadFile(String sourceUrl, Path savePath) {
        URL url = null;
        try {
            url = new URL(sourceUrl);
        } catch (MalformedURLException exception) {
            logger.error("Error loading file:" + exception.getMessage());
        }

        try (InputStream inputStream = new BufferedInputStream(url.openStream()); FileOutputStream fileOS = new FileOutputStream(savePath.toFile())) {
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            logger.debug("The file has been successfully uploaded to the directory: " + savePath.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Error loading file: " + e.getMessage());
        }
    }

}
