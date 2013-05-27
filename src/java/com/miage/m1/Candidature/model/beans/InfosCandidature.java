/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model.beans;

/**
 *
 * @author Kentish
 */
public class InfosCandidature {

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    private String nomPromotion;
    private String dateCandidature;
    private String etat;
    private String motivation;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private String telephone;
    private int idCandidat;
    private int idEtat;
    private int idPromotion;

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNomPromotion() {
        return nomPromotion;
    }

    public void setNomPromotion(String nomSession) {
        this.nomPromotion = nomSession;
    }

    public String getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(String dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
