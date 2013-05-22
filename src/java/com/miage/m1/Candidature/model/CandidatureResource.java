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
    int idCandidat = 0;
    /**
     * Id de la candidature correspondante
     */
    int idPromotion = 0;
    int idEtat = 0;
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
        String idCandi = null;
        String idPromo = null;
        String idEt = null;
        if (getRequest().getAttributes().get("idCandidat") != null) {
            idCandi = getRequest().getAttributes().get("idCandidat").toString();
            idCandidat = Integer.parseInt(idCandi);
        }
        if (getRequest().getAttributes().get("idPromotion") != null) {
            idPromo = getRequest().getAttributes().get("idPromotion").toString();
            idPromotion = Integer.parseInt(idPromo);
        }
        if (getRequest().getAttributes().get("idEtat") != null) {
            idEt = getRequest().getAttributes().get("idEtat").toString();
            idEtat = Integer.parseInt(idEt);
        }
    }

    @Get("xml")
    public Representation doGet() throws SQLException, IOException {

        DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
        // Generer un DOM representant la ressource
        Document doc = dom.getDocument();

        if (getRequest().getAttributes().get("nom") != null) {
            String nomPromo = getRequest().getAttributes().get("nom").toString();
            List<InfosCandidature> infos = candidature.getByIdPromotion(promo.getByNom(nomPromo));
            if (infos == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            Element root = doc.createElement("infosCandidature");
            doc.appendChild(root);
            for (int i = 0; i < infos.size(); i++) {
                Element info = doc.createElement("infoCandidature");
                Element moti = doc.createElement("motivation");
                Element etat = doc.createElement("etat");
                Element nom = doc.createElement("nom");
                Element prenom = doc.createElement("prenom");
                Element adresse = doc.createElement("adresse");
                Element email = doc.createElement("mail");
                moti.setTextContent(infos.get(i).getMotivation());
                etat.setTextContent(infos.get(i).getEtat());
                nom.setTextContent(infos.get(i).getNom());
                prenom.setTextContent(infos.get(i).getPrenom());
                adresse.setTextContent(infos.get(i).getAdresse());
                email.setTextContent(infos.get(i).getMail());
                info.appendChild(moti);
                info.appendChild(etat);
                info.appendChild(nom);
                info.appendChild(prenom);
                info.appendChild(adresse);
                info.appendChild(email);
                root.appendChild(info);
            }
            // Encodage en UTF-8

            // Encodage en UTF-8
        }
        dom.setCharacterSet(CharacterSet.UTF_8);
        resultat = dom;
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        candidature = candidature.getCandidature(idCandidat, idPromotion);
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
        } else {
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
