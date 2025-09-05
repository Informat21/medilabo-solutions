package com.medilabo_solutions.front.controller;

import com.medilabo_solutions.front.model.Patient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password"); // credentials du gateway
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Patient[]> response = restTemplate.exchange(
                gatewayUrl + "/patients",
                HttpMethod.GET,
                entity,
                Patient[].class
        );
        model.addAttribute("patients", response.getBody());

        return "patients"; // fichier patients.html
    }
}
