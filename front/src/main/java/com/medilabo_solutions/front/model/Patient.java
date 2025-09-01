package com.medilabo_solutions.front.model;


import java.time.LocalDate;

public class Patient {

    private String id;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String genre;
    private String adresse;
    private String telephone;

    // --- Getters et Setters ---
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
