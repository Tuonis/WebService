/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Kentish
 */
public class CandidatsResource extends ServerResource {

    /**
     * Id du candidat correspondant
     */
    int id;
    /**
     * Le candidat correspondant
     */
    Candidat candidat;
    List<Candidat> candidats;
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    /* protected void init() {
     try {
     id = Integer.parseInt(idAttribute);
     if (id <= 0) {
     throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotPositiveInteger");
     }
     } catch (NumberFormatException exc) {
     // Indiquer que la requete est mal formee
     throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotInteger");
     }
     }*/
    @Get("xml")
    public Representation doGet() throws IOException {
        candidats = candidat.getCandidats();
        if (candidats == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("candidats");
        doc.appendChild(root);
        for (int i = 0; i < candidats.size(); i++) {
            Element candi = doc.createElement("candidat");
            Element id = doc.createElement("id");
            Element nom = doc.createElement("nom");
            Element prenom = doc.createElement("prenom");
            Element adresse = doc.createElement("adresse");
            Element situation = doc.createElement("situation");
            Element tel = doc.createElement("tel");
            id.setTextContent(String.valueOf(candidats.get(i).getId()));
            nom.setTextContent(candidats.get(i).getNom());
            prenom.setTextContent(candidats.get(i).getPrenom());
            adresse.setTextContent(candidats.get(i).getAdresse());
            situation.setTextContent(candidats.get(i).getSituation());
            tel.setTextContent(candidats.get(i).getTelephone());
            candi.appendChild(id);
            candi.appendChild(nom);
            candi.appendChild(prenom);
            candi.appendChild(adresse);
            candi.appendChild(situation);
            candi.appendChild(tel);
            root.appendChild(candi);
        }
        
        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
        
    }
}
