package com.diabetes_microservices.diabetes.risk.service.controller;

import com.diabetes_microservices.diabetes.risk.service.dto.NoteDTO;
import com.diabetes_microservices.diabetes.risk.service.dto.PatientDTO;
import com.diabetes_microservices.diabetes.risk.service.model.RiskLevel;
import com.diabetes_microservices.diabetes.risk.service.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/risk")
public class RiskController {

    private final RestTemplate restTemplate;
    private final RiskService riskService;

    @Autowired
    public RiskController(RestTemplate restTemplate, RiskService riskService) {
        this.restTemplate = restTemplate;
        this.riskService = riskService;
    }

    @GetMapping("/patient/{id}")
    public RiskLevel assessRisk(@PathVariable Long id) {

        System.out.println( "id"+ id);
        // 1️⃣ Récupérer patient depuis patient-service
        PatientDTO patient = restTemplate.getForObject("http://patient-service:8081/patients/" + id, PatientDTO.class);
        System.out.println( "patient"+ patient);
        // 2️⃣ Récupérer notes depuis notes-service
        NoteDTO[] notesArray = restTemplate.getForObject("http://notes-service:8083/notes/patient/" + id, NoteDTO[].class);
        List<NoteDTO> notes = Arrays.asList(notesArray != null ? notesArray : new NoteDTO[0]);

        // 3️⃣ Calculer le risque
        return riskService.calculateRisk(patient, notes);
    }
}
