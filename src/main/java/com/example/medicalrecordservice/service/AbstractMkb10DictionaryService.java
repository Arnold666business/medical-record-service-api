package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import com.example.medicalrecordservice.repository.Mkb10DictionaryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractMkb10DictionaryService implements Mkb10DictionaryService {
    protected final Mkb10DictionaryRepository mkb10DictionaryRepository;

    public AbstractMkb10DictionaryService(Mkb10DictionaryRepository mkb10DictionaryRepository) {
        this.mkb10DictionaryRepository = mkb10DictionaryRepository;
    }

    @Cacheable(value = "Mkb10")
    @Transactional(readOnly = true)
    public List<Mkb10DiseaseInfoEntity> getAll() {
        return mkb10DictionaryRepository.findAll();
    }

}
