package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import com.example.medicalrecordservice.repository.Mkb10DictionaryRepository;
import com.example.medicalrecordservice.service.AbstractMkb10DictionaryService;
import com.example.medicalrecordservice.service.FileService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class Mkb10DictionaryFileSourceService extends AbstractMkb10DictionaryService {
    private final FileService fileService;

    public Mkb10DictionaryFileSourceService(Mkb10DictionaryRepository mkb10DictionaryRepository, FileService fileService) {
        super(mkb10DictionaryRepository);
        this.fileService = fileService;
    }

    @Override
    @Transactional
    @CacheEvict(value = "Mkb10", allEntries = true)
    public void refresh() {
        fileService.downloadNewFileFromSource();

        Map<String, List<Mkb10DiseaseInfoEntity>> resultEntitiesMap = fileService.getMapOfChanges(mkb10DictionaryRepository.findAll());
        List<Mkb10DiseaseInfoEntity> entitiesToDelete = resultEntitiesMap.get("toDelete");
        if (!entitiesToDelete.isEmpty()) {
            entitiesToDelete.stream().peek(entity -> entity.setActive(false)).toList();
            mkb10DictionaryRepository.saveAll(entitiesToDelete);
        }
        List<Mkb10DiseaseInfoEntity> entitiesToUpdate = resultEntitiesMap.get("toUpdate");
        if (!entitiesToUpdate.isEmpty()) {
            mkb10DictionaryRepository.saveAll(entitiesToUpdate);
        }
    }

}
