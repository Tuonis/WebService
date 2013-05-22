/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * @author Tuonis Home
 */
public class CandidatResource extends ServerResource {

    /**
     * Id du candidat correspondant
     */
    int id;
    /**
     * Le candidat correspondant
     */
    Candidat candidat;
    
    InfosCandidature infos;
    /**
     * Representation retournée
     */
    Representation resultat;
    /**
     * Erreurs possibles
     */
    List<String> erreurs;

    protected void init() {
        String idAttribute = getRequest().getAttributes().get("idCandidat").toString();
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
    public Representation doGet() throws IOException {
        //init();
        try {
            DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
            // Generer un DOM representant la ressource
            Document doc = dom.getDocument();
            
            if (getRequest().getAttributes().get("email") != null && getRequest().getAttributes().get("mdp")!=null) {
                String mail=getRequest().getAttributes().get("email").toString();
                String mdp=getRequest().getAttributes().get("mdp").toString();
                id = candidat.getIdByMailMdp(mail,mdp);
                Candidat candi=candidat.getById(id);
                List<InfosCandidature> infos = candidat.getInfosCandidature(id);
                if (infos == null) {
                    throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
                }
                Element root = doc.createElement("infosCandidature");
                doc.appendChild(root);
                for(int i=0;i<infos.size();i++){
                    Element info = doc.createElement("infoCandidature");
                    Element nomPromo = doc.createElement("nomPromotion");
                    Element date = doc.createElement("dateCandidature");
                    Element etat = doc.createElement("etat");
                    nomPromo.setTextContent(infos.get(i).getNomSession());
                    date.setTextContent(infos.get(i).getDateCandidature());
                    etat.setTextContent(infos.get(i).getEtat());
                    info.appendChild(nomPromo);
                    info.appendChild(date);
                    info.appendChild(etat);
                    root.appendChild(info);
                }
                Element infoCandi = doc.createElement("infoCandidat");
                Element nom = doc.createElement("nom");
                Element prenom = doc.createElement("prenom");
                Element tel = doc.createElement("telephone");
                Element adresse = doc.createElement("adresse");
                Element email = doc.createElement("mail");
                nom.setTextContent(candi.getNom());
                prenom.setTextContent(candi.getPrenom());
                tel.setTextContent(candi.getTelephone());
                adresse.setTextContent(candi.getAdresse());
                email.setTextContent(candi.getMail());
                infoCandi.appendChild(nom);
                infoCandi.appendChild(prenom);
                infoCandi.appendChild(tel);
                infoCandi.appendChild(adresse);
                infoCandi.appendChild(email);
                root.appendChild(infoCandi);
                // Encodage en UTF-8
                dom.setCharacterSet(CharacterSet.UTF_8);
                resultat = dom;
            }
            if (getRequest().getAttributes().get("mail") != null) {
                Element root = doc.createElement("candidat");
                doc.appendChild(root);
                String motPasse = candidat.getMdpOubli(getRequest().getAttributes().get("mail").toString());
                Element mdp = doc.createElement("mdp");
                mdp.setTextContent(motPasse);
                root.appendChild(mdp);
                dom.setCharacterSet(CharacterSet.UTF_8);
                resultat = dom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //HashMap m=new HashMap(); 
        //m.put("dom", resultat);
        //getRequest().setAttributes(m);
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        init();
        candidat = candidat.getById(id);
        if (candidat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String prenom = form.getFirstValue("prenom");
        String tel = form.getFirstValue("telephone");
        String mail = form.getFirstValue("mail");
        String adresse = form.getFirstValue("adresse");
        String situation = form.getFirstValue("situation");
        if (nom == null && prenom == null && tel == null && mail == null && adresse == null && situation == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                candidat.setNom(nom);
            }
        }

        if (prenom != null) {
            if (prenom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "prenomVide");
            } else {
                candidat.setPrenom(prenom);
            }
        }

        if (tel != null) {
            if (tel.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "telephoneVide");
            } else {
                candidat.setTelephone(tel);
            }
        }

        if (mail != null) {
            if (mail.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "mailVide");
            } else {
                candidat.setMail(mail);
            }
        }

        if (adresse != null) {
            if (adresse.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "adresseVide");
            } else {
                candidat.setAdresse(adresse);
            }
        }

        if (situation != null) {
            if (situation.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "situationVide");
            } else {
                candidat.setSituation(situation);
            }
        }

        try {
            candidat.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
    }

    @Post
    public Representation doPost(Representation entity) throws SQLException {
        init();
        candidat = candidat.getById(id);
        if (candidat == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String prenom = form.getFirstValue("prenom");
        String tel = form.getFirstValue("telephone");
        String mail = form.getFirstValue("mail");
        String adresse = form.getFirstValue("adresse");
        String situation = form.getFirstValue("situation");
        if (nom == null && prenom == null && tel == null && mail == null && adresse == null && situation == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            if (nom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "nomVide");
            } else {
                candidat.setNom(nom);
            }
        }

        if (prenom != null) {
            if (prenom.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "prenomVide");
            } else {
                candidat.setPrenom(prenom);
            }
        }

        if (tel != null) {
            if (tel.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "telephoneVide");
            } else {
                candidat.setTelephone(tel);
            }
        }

        if (mail != null) {
            if (mail.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "mailVide");
            } else {
                candidat.setMail(mail);
            }
        }

        if (adresse != null) {
            if (adresse.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "adresseVide");
            } else {
                candidat.setAdresse(adresse);
            }
        }

        if (situation != null) {
            if (situation.matches("^\\s*$")) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "situationVide");
            } else {
                candidat.setSituation(situation);
            }
        }

        try {
            candidat.insert();
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
            candidat.delete();
            setStatus(Status.SUCCESS_NO_CONTENT);
        }
        return resultat;
    }
}
