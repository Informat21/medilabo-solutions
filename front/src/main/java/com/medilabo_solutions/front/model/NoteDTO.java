package com.medilabo_solutions.front.model;

public class NoteDTO {
    private String id;
    private String patientId;
    private String content;

    // getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
