package com.medilabo_solutions.patient_service.repository;



import com.medilabo_solutions.patient_service.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
}
