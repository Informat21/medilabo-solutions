package com.medilabo_solutions.notes_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String patientId;
    private String content;
}