package com.medilabo_solutions.notes_service.service;

import com.medilabo_solutions.notes_service.model.Note;
import com.medilabo_solutions.notes_service.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository repository;
    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getNotesByPatient(String patientId) {
        log.info("🔍 Recherche des notes pour patient ID={}", patientId);
        return repository.findByPatientId(patientId);
    }

    public Note addNote(Note note) {
        log.info("📝 Ajout d'une note");
        // ❌ NE PAS générer manuellement l'ID
        return repository.save(note);
    }

    public Note updateNote(String id, Note updatedNote) {
        return repository.findById(id)
                .map(existing -> {
                    log.info("✏️ Mise à jour de la note ID={} - avant: '{}'", id, existing.getContent());

                    if (updatedNote.getContent() != null) {
                        existing.setContent(updatedNote.getContent());
                    }
                    if (updatedNote.getPatientId().isEmpty()) {
                        existing.setPatientId(updatedNote.getPatientId());
                    }

                    Note saved = repository.save(existing);
                    log.info("✅ Note mise à jour - après: '{}'", saved.getContent());
                    return saved;
                })
                .orElse(null);
    }

    public boolean deleteNote(String id) {
        return repository.findById(id).map(note -> {
            repository.delete(note);
            log.info("🗑️ Note ID={} supprimée avec succès", id);
            return true;
        }).orElseGet(() -> {
            log.warn("❌ Note ID={} introuvable", id);
            return false;
        });
    }
}