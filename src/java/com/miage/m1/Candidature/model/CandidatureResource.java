/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Kentish
 */
public class CandidatureResource extends ServerResource {
    
    /**
     * Id de la candidature correspondante
     */
    int idCandidat=0;
    /**
     * Id de la candidature correspondante
     */
    int idPromotion=0;
    
    int idEtat=0;
    /**
     * La candidature correspondante
     */
    List<Candidature> candidatures;
    
    Candidature candidature;
    
    Candidat candidat;
    
    Promotion promo;
    
    Etat etat;
    
    
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    protected void init() {
        String idCandi=null;
        String idPromo=null;
        String idEt=null;
        if(getRequest().getAttributes().get("idCandidat")!=null){
            idCandi = getRequest().getAttributes().get("idCandidat").toString();
            idCandidat = Integer.parseInt(idCandi);
        }
        if(getRequest().getAttributes().get("idPromotion")!=null){
            idPromo = getRequest().getAttributes().get("idPromotion").toString();
            idPromotion = Integer.parseInt(idPromo);
        }  
        if(getRequest().getAttributes().get("idEtat")!=null){
            idEt = getRequest().getAttributes().get("idEtat").toString();
            idEtat = Integer.parseInt(idEt);
        }   
    }
    
    @Get("xml")
    public Representation doGet() throws SQLException, IOException {
        init();
        if(idCandidat!=0 && idPromotion==0 && idEtat==0){
            candidatures = candidature.getByIdCandidat((candidat.getById(idCandidat).getId()));
        }
        
         if(idCandidat==0 && idPromotion!=0 && idEtat==0){
            candidatures = candidature.getByIdPromotion((promo.getById(idPromotion).getId()));
        }
         if(idCandidat==0 && idPromotion==0 && idEtat!=0){
            candidatures = candidature.getByIdEtat((etat.getById(idEtat).getId()));
        }
        if (candidatures == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();
        Element root = doc.createElement("candidatures");
        doc.appendChild(root);
        for (int i = 0; i < candidatures.size(); i++) {
            Element candidature = doc.createElement("candidature");
            Element candidat = doc.createElement("idCandidat");
            Element promo = doc.createElement("idPromotion");
            Element etat = doc.createElement("idEtat");
            Element date = doc.createElement("dateCandidature");
            Element motivation = doc.createElement("motivation");
            candidat.setTextContent(String.valueOf(candidatures.get(i).getIdCandidat()));
            etat.setTextContent(String.valueOf(candidatures.get(i).getIdEtat()));
            promo.setTextContent(String.valueOf(candidatures.get(i).getIdPromotion()));
            date.setTextContent(candidatures.get(i).getDateCandidature());
            motivation.setTextContent(candidatures.get(i).getMotivation());
            candidature.appendChild(candidat);
            candidature.appendChild(promo);
            candidature.appendChild(etat);
            candidature.appendChild(date);
            candidature.appendChild(motivation);
            root.appendChild(candidature);
        }

        // Encodage en UTF-8
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }
    
    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        candidature = candidature.getCandidature(idCandidat,idPromotion);
        if (candidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        Integer idEtat = Integer.parseInt(form.getFirstValue("idEtat"));
        String motivation = form.getFirstValue("motivation");
        String dateCandidature = form.getFirstValue("dateCandidature");
        if (idEtat == null && motivation == null && dateCandidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (idEtat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idEtatVide");
        }else {
                candidature.setIdEtat(idEtat);
        }

        if (motivation != null) {
            if (motivation.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "motivationVide");
            } else {
                candidature.setMotivation(motivation);
            }
        }

        if (dateCandidature != null) {
            if (dateCandidature.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "dateCandidatureVide");
            } else {
                candidature.setDateCandidature(dateCandidature);
            }
        }

        try {
            candidature.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }
    
}
