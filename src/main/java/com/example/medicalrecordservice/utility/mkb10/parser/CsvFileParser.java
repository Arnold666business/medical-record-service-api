package com.example.medicalrecordservice.utility.mkb10.parser;

import com.example.medicalrecordservice.helper.Mkb10DictionaryStaticHashHelper;
import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class CsvFileParser implements Mkb10FileParser{
    @Value("${csv_file.path_to_save_directory}")
    private String pathToDirectoryWithJsonAndCsvFile;

    @Value("${csv_file.source_path}")
    private String source;

    @Value("${csv_file.file_name}")
    private String csvFileName;

    @Value("${csv_file.json_hash_holder_file_path}")
    private String pathToJsonHashHolder;

    private final static Logger logger = LoggerFactory.getLogger(CsvFileParser.class);
    @Override
    public List<Mkb10DiseaseInfoEntity> parseFromFile() {
        List<Mkb10DiseaseInfoEntity> result = new ArrayList<>();
        String pathToFile = pathToDirectoryWithJsonAndCsvFile + "\\" + csvFileName;
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("\",\"");

                for (int i = 0; i < elements.length; i++) {
                    elements[i] = elements[i].replace("\"", "").trim();
                }

                if (elements.length > 3 && !elements[2].isEmpty()) {
                    Mkb10DiseaseInfoEntity entity = new Mkb10DiseaseInfoEntity();
                    entity.setCode(elements[2]);
                    entity.setDiseaseName(elements[3]);
                    result.add(entity);
                }
            }
        } catch (IOException exception) {
            logger.error("Error reading file or file does not exist");
        }
        return result;
    }

    @Override
    public void downloadFile(){
        URL url = null;
        try {
            url = new URL(source);
        } catch (MalformedURLException exception){
            logger.error("Error loading file:" + exception.getMessage());
        }

        Path savePath = Paths.get(pathToDirectoryWithJsonAndCsvFile, csvFileName);

        try (InputStream inputStream = new BufferedInputStream(url.openStream());
             FileOutputStream fileOS = new FileOutputStream(savePath.toFile())) {
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            logger.debug("The file has been successfully uploaded to the directory: " + savePath.toAbsolutePath());
        }
        catch (IOException e) {
            logger.error("Error loading file: " + e.getMessage());
        }
    }

    @Override
    public String getCurrentFileHash(){
        logger.info("Calling Calculate current hash file method");

        return Mkb10DictionaryStaticHashHelper.calculateFileHash(pathToDirectoryWithJsonAndCsvFile + "\\" + csvFileName);
    }

    @Override
    public void replaceHashWithCurrentFileHash(){
        String hashValue = this.getCurrentFileHash();

        logger.info("Calling replace file hash to {} value", hashValue);

        Mkb10DictionaryStaticHashHelper.putHashToJsonFile(pathToJsonHashHolder, hashValue);
    }

    @Override
    public String getOldFileHash(){
        return Mkb10DictionaryStaticHashHelper.readJsonHashValue(pathToJsonHashHolder);
    }
}
