/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Etat;
import com.miage.m1.Candidature.model.beans.Promotion;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Kentish
 */
public class EtatResource extends ServerResource {
    
   /**
     * Nom de la promotion correspondante
     */
    int id;
    /**
     * La promotion correspondante
     */
    Etat etat;
    
    List<Etat> etats;
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    protected void init() {
        String idAttribute = getRequest().getAttributes().get("idPromotion").toString();
        try {
            id = Integer.parseInt(idAttribute);
            if (id <= 0) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotPositiveInteger");
            }
        } catch (NumberFormatException exc) {
            // Indiquer que la requete est mal formee
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idNotInteger");
        }
    }

    @Get("xml")
    public Representation doGet() throws SQLException, IOException {
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        etats = etat.getEtats();
        if (etats == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("etats");
        doc.appendChild(root);
        for (int i = 0; i < etats.size(); i++) {
            Element etat = doc.createElement("etat");
            etat.setAttribute("id", String.valueOf(etats.get(i).getId()));
            etat.setAttribute("etat", etats.get(i).getEtat());
            root.appendChild(etat);
        }
        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }
    
}
