package com.example.medicalrecordservice.utility.mkb10.parser;

import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;

import java.util.List;

public interface Mkb10FileParser {
    List<Mkb10DiseaseInfoEntity> parseFromFile();

    void downloadFile();

    void replaceHashWithCurrentFileHash();

    String getCurrentFileHash();

    String getOldFileHash();
}
