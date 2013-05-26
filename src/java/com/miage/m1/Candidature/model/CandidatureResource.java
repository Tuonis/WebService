/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Etat;
import com.miage.m1.Candidature.model.beans.Candidat;
import com.miage.m1.Candidature.model.beans.InfosCandidature;
import com.miage.m1.Candidature.model.beans.Promotion;
import com.miage.m1.Candidature.model.beans.Candidature;
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
import org.restlet.resource.Post;
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

        if (getRequest().getAttributes().get("promotion") != null) {
            String nomPromo = getRequest().getAttributes().get("promotion").toString();
            int id = promo.getByNom(nomPromo);
            List<InfosCandidature> infos = candidature.getByIdPromotion(id);
            if (infos == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            Element root = doc.createElement("infosCandidature");
            doc.appendChild(root);
            for (int i = 0; i < infos.size(); i++) {
                Element info = doc.createElement("infoCandidature");
                info.setAttribute("dateCandidature", infos.get(i).getDateCandidature());
                info.setAttribute("promo", nomPromo);
                info.setAttribute("etat", infos.get(i).getEtat());
                info.setAttribute("nom", infos.get(i).getNom());
                info.setAttribute("prenom", infos.get(i).getPrenom());
                info.setAttribute("telephone", infos.get(i).getTelephone());
                info.setAttribute("adresse", infos.get(i).getAdresse());
                info.setAttribute("mail", infos.get(i).getMail());
                info.setAttribute("idCandidat", String.valueOf(infos.get(i).getIdCandidat()));
                info.setAttribute("idEtat", String.valueOf(infos.get(i).getIdEtat()));
                info.setAttribute("idPromotion", String.valueOf(infos.get(i).getIdPromotion()));
                root.appendChild(info);
            }
        }
        
        if (getRequest().getAttributes().get("etat") != null) {
            String eta = getRequest().getAttributes().get("etat").toString();
            int id = etat.getByNom(eta);
            List<InfosCandidature> infos = candidature.getByIdEtat(id);
            if (infos == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            Element root = doc.createElement("infosCandidature");
            doc.appendChild(root);
            for (int i = 0; i < infos.size(); i++) {
                Element info = doc.createElement("infoCandidature");
                info.setAttribute("dateCandidature", infos.get(i).getDateCandidature());
                info.setAttribute("etat", infos.get(i).getEtat());
                info.setAttribute("nom", infos.get(i).getNom());
                info.setAttribute("prenom", infos.get(i).getPrenom());
                info.setAttribute("telephone", infos.get(i).getTelephone());
                info.setAttribute("adresse", infos.get(i).getAdresse());
                info.setAttribute("mail", infos.get(i).getMail());
                info.setAttribute("promo", infos.get(i).getNomPromotion());
                info.setAttribute("idCandidat", String.valueOf(infos.get(i).getIdCandidat()));
                info.setAttribute("idEtat", String.valueOf(infos.get(i).getIdEtat()));
                info.setAttribute("idPromotion", String.valueOf(infos.get(i).getIdPromotion()));
                root.appendChild(info);
            }
        }
        
        if (getRequest().getAttributes().get("etat") == null && getRequest().getAttributes().get("promotion") == null) {
            List<InfosCandidature> infos = candidature.getCandidatures();
            if (infos == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }
            Element root = doc.createElement("infosCandidature");
            doc.appendChild(root);
            for (int i = 0; i < infos.size(); i++) {
                Element info = doc.createElement("infoCandidature");
                info.setAttribute("dateCandidature", infos.get(i).getDateCandidature());
                info.setAttribute("etat", infos.get(i).getEtat());
                info.setAttribute("nom", infos.get(i).getNom());
                info.setAttribute("prenom", infos.get(i).getPrenom());
                info.setAttribute("telephone", infos.get(i).getTelephone());
                info.setAttribute("adresse", infos.get(i).getAdresse());
                info.setAttribute("mail", infos.get(i).getMail());
                info.setAttribute("promo", infos.get(i).getNomPromotion());
                info.setAttribute("idCandidat", String.valueOf(infos.get(i).getIdCandidat()));
                info.setAttribute("idEtat", String.valueOf(infos.get(i).getIdEtat()));
                info.setAttribute("idPromotion", String.valueOf(infos.get(i).getIdPromotion()));
                root.appendChild(info);
            }
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
    
    @Post
    public Representation doPost(Representation entity) throws SQLException {
        Candidature candidature = new Candidature();
        Form form = new Form(entity);
        String idPromo = form.getFirstValue("idPromo");
        String idCandidat = form.getFirstValue("idCandidat");
        String idEtat = form.getFirstValue("idEtat");
        String motivation = form.getFirstValue("motivation");
        String dateCandidature = form.getFirstValue("dateCandidature");
        if (idEtat == null && motivation == null && dateCandidature == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (idEtat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idEtatVide");
        } else {
            candidature.setIdEtat(Integer.parseInt(idEtat));
        }
        
        if (idPromo == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idPromoVide");
        } else {
            candidature.setIdPromotion(Integer.parseInt(idPromo));
        }
        
        if (idCandidat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "idCandidatVide");
        } else {
            candidature.setIdCandidat(Integer.parseInt(idCandidat));
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
            candidature.insert();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }
}
