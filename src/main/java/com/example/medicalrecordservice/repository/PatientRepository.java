package com.example.medicalrecordservice.repository;

import com.example.medicalrecordservice.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {
    boolean existsByOmsNumber(String omsNumber);
}
