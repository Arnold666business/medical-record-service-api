package com.example.medicalrecordservice.utility.mkb10.compare;


import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import com.example.medicalrecordservice.utility.mkb10.parser.Mkb10FileParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Log4j2
public class FileCompare {
    private final Mkb10FileParser fileParser;

    private final Logger logger = LoggerFactory.getLogger(FileCompare.class);

    public void downloadNewFileAndReplaceHash(){
        fileParser.replaceHashWithCurrentFileHash();
        fileParser.downloadFile();
    }

    public boolean isChanged(){
        return Objects.equals(fileParser.getCurrentFileHash().trim().toString(), fileParser.getOldFileHash().trim().toString());
    }

    /**
     * Returns a map with changes in the MKB-10 data file.
     *
     * Map keys:
     * - "toDelete": list of {@code Mkb10DiseaseInfoEntity} entities to delete from the database.
     * - "toUpdate": list of {@code Mkb10DiseaseInfoEntity} entities to update in the database.
     *
     * @return Map, where the keys are the strings "toDelete" and "toUpdate", and the values are lists of entities to delete and update, respectively.
     */
    public Map<String, List<Mkb10DiseaseInfoEntity>> getMapOfChanges(List<Mkb10DiseaseInfoEntity> currentList){
        List<Mkb10DiseaseInfoEntity> newList = fileParser.parseFromFile();
        Map<String, Mkb10DiseaseInfoEntity> oldMap = currentList.stream()
                .collect(Collectors.toMap(Mkb10DiseaseInfoEntity::getCode, entity -> entity));

        List<Mkb10DiseaseInfoEntity> toDelete = new ArrayList<>();
        List<Mkb10DiseaseInfoEntity> toUpdate = new ArrayList<>();

        for(Mkb10DiseaseInfoEntity newEntity: newList){
            Mkb10DiseaseInfoEntity entity = oldMap.get(newEntity.getCode());
            if(entity == null || !newEntity.getDiseaseName().equals(entity.getDiseaseName())){
                toUpdate.add(newEntity);
            }
            oldMap.remove(newEntity.getCode());
        }

        toDelete.addAll(oldMap.values());
        return Map.of("toDelete", toDelete, "toUpdate", toUpdate);
    }
}
