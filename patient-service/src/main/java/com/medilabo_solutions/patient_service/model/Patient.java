package com.medilabo_solutions.patient_service.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String adresse;
    private String telephone;
}

