package com.medilabo_solutions.patient_service.model;

import lombok.*;
        import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    private String id;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String adresse;
    private String telephone;
}

