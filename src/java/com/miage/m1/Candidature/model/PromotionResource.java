/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Promotion;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class PromotionResource extends ServerResource {

    /**
     * Nom de la promotion correspondante
     */
    int id;
    /**
     * La promotion correspondante
     */
    Promotion promotion;
    List<Promotion> promotions;
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
        promotions = promotion.getPromotions();
        if (promotions == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("promotions");
        doc.appendChild(root);
        for (int i = 0; i < promotions.size(); i++) {
            Element promo = doc.createElement("promotion");
            promo.setAttribute("id", String.valueOf(promotions.get(i).getId()));
            promo.setAttribute("nom", promotions.get(i).getNom());
            promo.setAttribute("dateDeb", promotions.get(i).getDateDeb().toString());
            promo.setAttribute("dateFin", promotions.get(i).getDateFin().toString());
            promo.setAttribute("periode", promotions.get(i).getPeriode());
            promo.setAttribute("dateDebInscription", promotions.get(i).getDateDebInscription().toString());
            promo.setAttribute("dateFinInscription", promotions.get(i).getDateFinInscription().toString());
            root.appendChild(promo);
        }
        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        promotion = promotion.getById(id);
        if (promotion == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String tmpdateDeb = form.getFirstValue("dateDeb");
        Date dateDeb = Date.valueOf(tmpdateDeb);
        String tmpdateFin = form.getFirstValue("dateFin");
        Date dateFin = Date.valueOf(tmpdateFin);
        String periode = form.getFirstValue("periode");
        if (nom == null && dateDeb == null && dateFin == null && periode == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                promotion.setNom(nom);
            }
        }

        if (dateDeb != null) {
            
                promotion.setDateDeb(dateDeb);
            
        }

        if (dateFin != null) {
           
                promotion.setDateFin(dateFin);
            
        }

        if (periode != null) {
            if (periode.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "periodeVide");
            } else {
                promotion.setPeriode(periode);
            }
        }

        try {
            promotion.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }

    @Delete
    public Representation doDelete() throws SQLException {
        init();
        if (getStatus() == Status.SUCCESS_OK) {
            // Traiter cas de produit inexistant (404) : a faire
            promotion.delete();
            setStatus(Status.SUCCESS_NO_CONTENT);
        }
        return resultat;
    }
    
    @Post
    public Representation doPost(Representation entity) throws SQLException {
        init();
        promotion = promotion.getById(id);
        if (promotion == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String tmpdateDeb = form.getFirstValue("dateDeb");
        Date dateDeb = Date.valueOf(tmpdateDeb);
        String tmpdateFin = form.getFirstValue("dateFin");
        Date dateFin = Date.valueOf(tmpdateFin);
        String periode = form.getFirstValue("periode");
        if (nom == null && dateDeb == null && dateFin == null && periode == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                promotion.setNom(nom);
            }
        }

        if (dateDeb != null) {
          
                promotion.setDateDeb(dateDeb);
            
        }

        if (dateFin != null) {
           
                promotion.setDateFin(dateFin);
            
        }

        if (periode != null) {
            if (periode.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "periodeVide");
            } else {
                promotion.setPeriode(periode);
            }
        }

        try {
            promotion.insert();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }
}
