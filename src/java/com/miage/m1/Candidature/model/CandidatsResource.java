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
            candi.setAttribute("id", String.valueOf(candidats.get(i).getId()));
            candi.setAttribute("nom", candidats.get(i).getNom());
            candi.setAttribute("prenom", candidats.get(i).getPrenom());
            candi.setAttribute("adresse", candidats.get(i).getAdresse());
            candi.setAttribute("tel", candidats.get(i).getTelephone());
            root.appendChild(candi);
        }
        
        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
        
    }
}
