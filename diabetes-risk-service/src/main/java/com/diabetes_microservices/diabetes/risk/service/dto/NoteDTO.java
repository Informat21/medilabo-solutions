package com.diabetes_microservices.diabetes.risk.service.dto;

import lombok.Data;

@Data
public class NoteDTO {
    private String id;
    private String patientId;
    private String content;
}
