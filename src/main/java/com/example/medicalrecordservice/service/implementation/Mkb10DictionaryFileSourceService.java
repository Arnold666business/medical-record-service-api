package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import com.example.medicalrecordservice.repository.Mkb10DictionaryRepository;
import com.example.medicalrecordservice.service.AbstractMkb10DictionaryService;
import com.example.medicalrecordservice.utility.mkb10.compare.FileCompare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class Mkb10DictionaryFileSourceService extends AbstractMkb10DictionaryService {
    private final Logger logger = LoggerFactory.getLogger(Mkb10DictionaryFileSourceService.class);
    private final FileCompare fileCompare;

    public Mkb10DictionaryFileSourceService(Mkb10DictionaryRepository mkb10DictionaryRepository, FileCompare fileCompare){
        super(mkb10DictionaryRepository);
        this.fileCompare = fileCompare;
    }

    @Override
    @Transactional
    public void refresh(){
        fileCompare.downloadNewFileAndReplaceHash();
        if(fileCompare.isChanged()){
            return;
        }

        Map<String, List<Mkb10DiseaseInfoEntity>> resultEntitiesMap = fileCompare
                .getMapOfChanges(mkb10DictionaryRepository.findAll());
        List<Mkb10DiseaseInfoEntity> entitiesToDelete = resultEntitiesMap.get("toDelete");
        if(!entitiesToDelete.isEmpty()){
            mkb10DictionaryRepository.deleteAll(entitiesToDelete);
        }
        List<Mkb10DiseaseInfoEntity> entitiesToUpdate = resultEntitiesMap.get("toUpdate");
        if(!entitiesToUpdate.isEmpty()){
            mkb10DictionaryRepository.saveAll(entitiesToUpdate);
        }
    }
}
