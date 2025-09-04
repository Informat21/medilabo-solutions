package com.medilabo_solutions.front.controller;

import com.medilabo_solutions.front.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class FrontController {

    private final String gatewayUrl = "http://localhost:8080";

    @GetMapping("/front/patients")
    public String getPatients(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Patient[] patients = restTemplate.getForObject(gatewayUrl +"/patients", Patient[].class);
        model.addAttribute("patients", patients);
        return "patients"; // fichier patients.html
    }
}
