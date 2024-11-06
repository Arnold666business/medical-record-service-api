package com.example.medicalrecordservice.service;


import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;

import java.util.List;

public interface Mkb10DictionaryService {
    void refresh();

    List<Mkb10DiseaseInfoEntity> getAll();
}
