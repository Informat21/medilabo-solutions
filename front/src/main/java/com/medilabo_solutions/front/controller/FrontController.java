package com.medilabo_solutions.front.controller;

import com.medilabo_solutions.front.model.NoteDTO;
import com.medilabo_solutions.front.model.Patient;
import com.medilabo_solutions.front.model.PatientWithNotesDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FrontController {

    private final RestTemplate restTemplate;

    public FrontController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/front/patients")
    public String showPatients (Model model) {
        // 1️⃣ Récupérer tous les patients depuis patient-service
        Patient[] patients = restTemplate.getForObject("http://localhost:8081/patients", Patient[].class);

        List<PatientWithNotesDTO> patientsWithNotes = new ArrayList<>();

        if (patients != null) {
            for (Patient p : patients) {
                PatientWithNotesDTO dto = new PatientWithNotesDTO();
                dto.setId(p.getId());
                dto.setPrenom(p.getPrenom());
                dto.setNom(p.getNom());
                dto.setDateNaissance(p.getDateNaissance());
                dto.setGenre(p.getGenre());
                dto.setAdresse(p.getAdresse());
                dto.setTelephone(p.getTelephone());


                // 2️⃣ Récupérer les notes du patient depuis notes-service
                List<NoteDTO> notes = restTemplate.exchange(
                        "http://localhost:8083/notes/patient/" + p.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<NoteDTO>>() {}
                ).getBody();

                dto.setNotes(notes != null ? notes : new ArrayList<>());
                patientsWithNotes.add(dto);
            }
        }
        // 3️⃣ Passer la liste au template Thymeleaf
        model.addAttribute("patients", patientsWithNotes);

        // Retourne le nom du fichier HTML sans .html
        return "patients";

    }
    @PostMapping("/front/patients/{id}/notes")
    public String addNote(@PathVariable String id,
                          @RequestParam String content) {
        // Créer un objet NoteDTO
        NoteDTO newNote = new NoteDTO();
        newNote.setPatientId(id);
        newNote.setContent(content);

        // Envoyer au notes-service
        restTemplate.postForObject(
                "http://localhost:8083/notes",
                newNote,
                NoteDTO.class
        );

        // Après ajout, on recharge la page des patients
        return "redirect:/front/patients/" + id;
    }
    @GetMapping("/front/patients/{id}")
    public String showPatientDetails(@PathVariable String id, Model model) {
        // Récupérer le patient
        Patient patient = restTemplate.getForObject("http://localhost:8081/patients/" + id, Patient.class);

        // Récupérer ses notes
        List<NoteDTO> notes = restTemplate.exchange(
                "http://localhost:8083/notes/patient/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NoteDTO>>() {}
        ).getBody();

        // 🔹 3. Récupérer le niveau de risque depuis diabetes-risk-service
        String riskLevel = restTemplate.getForObject("http://localhost:8084/risk/patient/" + id, String.class);


        // Préparer le DTO
        PatientWithNotesDTO dto = new PatientWithNotesDTO();
        dto.setId(patient.getId());
        dto.setPrenom(patient.getPrenom());
        dto.setNom(patient.getNom());
        dto.setDateNaissance(patient.getDateNaissance());
        dto.setGenre(patient.getGenre());
        dto.setAdresse(patient.getAdresse());
        dto.setTelephone(patient.getTelephone());
        dto.setNotes(notes);

        model.addAttribute("patient", dto);
        model.addAttribute("riskLevel", riskLevel);

        return "patient-details"; // une nouvelle page patient-details.html
    }


}
