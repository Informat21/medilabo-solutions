package com.medilabo_solutions.notes_service.controller;

import com.medilabo_solutions.notes_service.model.Note;
import com.medilabo_solutions.notes_service.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @PostMapping
    public Note addNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Ajouter une note via formulaire
//    @PostMapping
//    public Note addNote(@RequestParam String patientId,
//                        @RequestParam String content) {
//        Note note = new Note();
//        note.setPatientId(patientId);
//        note.setContent(content);
//        return noteRepository.save(note);
//    }

    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatient(@PathVariable String patientId) {
        return noteRepository.findByPatientId(patientId);
    }
}