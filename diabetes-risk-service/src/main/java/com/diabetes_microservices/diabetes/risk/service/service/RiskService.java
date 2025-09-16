package com.diabetes_microservices.diabetes.risk.service.service;

import com.diabetes_microservices.diabetes.risk.service.dto.NoteDTO;
import com.diabetes_microservices.diabetes.risk.service.dto.PatientDTO;
import com.diabetes_microservices.diabetes.risk.service.model.RiskLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskService {

    private static final List<String> TRIGGERS = List.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
            "Fumeur", "Fumeuse", "Anormal", "Cholestérol",
            "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    public RiskLevel calculateRisk(PatientDTO patient, List<NoteDTO> notes) {
        int age = calculateAge(patient.getDateNaissance());
        long triggerCount = notes.stream()
                .map(NoteDTO::getContent)
                .filter(content -> content != null)
                .flatMap(content -> TRIGGERS.stream()
                        .filter(trigger -> content.toLowerCase().contains(trigger.toLowerCase())))
                .count();

        // Règles
        if (triggerCount == 0) {
            return RiskLevel.NONE;
        }

        if (triggerCount >= 2 && triggerCount <= 5 && age > 30) {
            return RiskLevel.BORDERLINE;
        }

        if (age < 30 && patient.getGenre().equalsIgnoreCase("M")) {
            if (triggerCount >= 3 && triggerCount < 5) return RiskLevel.IN_DANGER;
            if (triggerCount >= 5) return RiskLevel.EARLY_ONSET;
        }

        if (age < 30 && patient.getGenre().equalsIgnoreCase("F")) {
            if (triggerCount >= 4 && triggerCount < 7) return RiskLevel.IN_DANGER;
            if (triggerCount >= 7) return RiskLevel.EARLY_ONSET;
        }

        if (age > 30) {
            if (triggerCount == 6 || triggerCount == 7) return RiskLevel.IN_DANGER;
            if (triggerCount >= 8) return RiskLevel.EARLY_ONSET;
        }

        return RiskLevel.NONE;
    }

    private int calculateAge(String dateNaissance) {
        return Period.between(LocalDate.parse(dateNaissance), LocalDate.now()).getYears();
    }
}
