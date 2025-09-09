package com.medilabo_solutions.notes_service.repository;

import com.medilabo_solutions.notes_service.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientId(String patientId);
}