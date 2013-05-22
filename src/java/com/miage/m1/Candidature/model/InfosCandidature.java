/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

/**
 *
 * @author Kentish
 */
public class InfosCandidature {

    private String nomSession;
    private String dateCandidature;
    private String etat;

    public InfosCandidature(String nomSession, String dateCandidature, String etat) {
        this.nomSession = nomSession;
        this.dateCandidature = dateCandidature;
        this.etat = etat;
    }

    public String getNomSession() {
        return nomSession;
    }

    public void setNomSession(String nomSession) {
        this.nomSession = nomSession;
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
