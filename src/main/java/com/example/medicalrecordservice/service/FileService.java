package com.example.medicalrecordservice.service;


import com.example.medicalrecordservice.helper.FileHelper;
import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final static Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${PATH_TO_SAVE_DIRECTORY}")
    private String pathToDirectoryFile;

    @Value("${SOURCE_PATH}")
    private String source;

    @Value("${FILE_NAME}")
    private String fileName;

    public void downloadNewFileFromSource() {
        FileHelper.downloadFile(source, Paths.get(pathToDirectoryFile, fileName));
    }

    /**
     * Returns a map with changes in the MKB-10 data file.
     * <p>
     * Map keys:
     * - "toDelete": list of {@code Mkb10DiseaseInfoEntity} entities to delete from the database.
     * - "toUpdate": list of {@code Mkb10DiseaseInfoEntity} entities to update in the database.
     *
     * @return Map, where the keys are the strings "toDelete" and "toUpdate", and the values are lists of entities to delete and update, respectively.
     */
    public Map<String, List<Mkb10DiseaseInfoEntity>> getMapOfChanges(List<Mkb10DiseaseInfoEntity> currentList) {
        List<Mkb10DiseaseInfoEntity> newList = parseFromFile();
        Map<String, Mkb10DiseaseInfoEntity> oldMap = currentList.stream().collect(Collectors.toMap(Mkb10DiseaseInfoEntity::getCode, entity -> entity));

        List<Mkb10DiseaseInfoEntity> toDelete = new ArrayList<>();
        List<Mkb10DiseaseInfoEntity> toUpdate = new ArrayList<>();

        for (Mkb10DiseaseInfoEntity newEntity : newList) {
            Mkb10DiseaseInfoEntity entity = oldMap.get(newEntity.getCode());
            if (entity == null || !newEntity.getDiseaseName().equals(entity.getDiseaseName())) {
                toUpdate.add(newEntity);
            }
            oldMap.remove(newEntity.getCode());
        }

        toDelete.addAll(oldMap.values());
        return Map.of("toDelete", toDelete, "toUpdate", toUpdate);
    }

    public List<Mkb10DiseaseInfoEntity> parseFromFile() {
        List<Mkb10DiseaseInfoEntity> result = new ArrayList<>();
        String pathToFile = pathToDirectoryFile + "\\" + fileName;
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

}
