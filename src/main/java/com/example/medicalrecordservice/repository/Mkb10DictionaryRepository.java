package com.example.medicalrecordservice.repository;

import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mkb10DictionaryRepository extends JpaRepository<Mkb10DiseaseInfoEntity, String> {
}
