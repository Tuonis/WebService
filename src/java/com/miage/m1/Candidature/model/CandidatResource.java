/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.mail.MailerBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

            if (getRequest().getAttributes().get("email") != null && getRequest().getAttributes().get("mdp") != null) {
                String mail = getRequest().getAttributes().get("email").toString();
                String mdp = getRequest().getAttributes().get("mdp").toString();
                id = candidat.getIdByMailMdp(mail, mdp);
                Candidat candi = candidat.getById(id);
                List<InfosCandidature> infos = candidat.getInfosCandidature(id);
                if (infos == null) {
                    throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
                }
                Element root = doc.createElement("infosCandidature");
                doc.appendChild(root);
                for (int i = 0; i < infos.size(); i++) {
                    Element info = doc.createElement("infoCandidature");
                    info.setAttribute("nomPromotion", infos.get(i).getNomSession());
                    info.setAttribute("dateCandidature", infos.get(i).getDateCandidature());
                    info.setAttribute("etat", infos.get(i).getEtat());
                    root.appendChild(info);
                }
                Element infoCandi = doc.createElement("infoCandidat");
                infoCandi.setAttribute("nom", candi.getNom());
                infoCandi.setAttribute("prenom", candi.getPrenom());
                infoCandi.setAttribute("telephone", candi.getTelephone());
                infoCandi.setAttribute("adresse", candi.getAdresse());
                infoCandi.setAttribute("mail", candi.getMail());
                root.appendChild(infoCandi);
                // Encodage en UTF-8
                dom.setCharacterSet(CharacterSet.UTF_8);
                resultat = dom;
            }
            if (getRequest().getAttributes().get("mail") != null) {
                Element root = doc.createElement("candidat");
                doc.appendChild(root);
                String motPasse = candidat.getMdpOubli(getRequest().getAttributes().get("mail").toString());
                root.setAttribute("mdp", motPasse);
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
        //init();
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
        //init();
        Candidat candidat = new Candidat();
        Form form = new Form(entity);
        String nom = form.getFirstValue("nom");
        String prenom = form.getFirstValue("prenom");
        String tel = form.getFirstValue("telephone");
        String mail = form.getFirstValue("mail");
        String adresse = form.getFirstValue("adresse");
        String diplome = form.getFirstValue("diplome");
        String competence = form.getFirstValue("competence");
        String situationPro = form.getFirstValue("situationPro");
        if (nom == null && prenom == null && tel == null && mail == null && adresse == null && diplome == null
                && competence == null && situationPro == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
        }
        if (nom != null) {
            
                candidat.setNom(nom);
            
        }

        if (prenom != null) {
            
                candidat.setPrenom(prenom);
            
        }

        if (tel != null) {
            
                candidat.setTelephone(tel);
            
        }

        if (mail != null) {
            
                candidat.setMail(mail);
            
        }

        if (adresse != null) {
            
                candidat.setAdresse(adresse);
            
        }

        if (diplome != null) {
            
                candidat.setDiplome(diplome);
            
        }

        if (competence != null) {
            
                candidat.setCompetence(competence);
            
        }

        if (situationPro != null) {
            
                candidat.setSituationPro(situationPro);
            
        }
        candidat.insert();
        /*try {
            
            setStatus(Status.SUCCESS_NO_CONTENT);
            
            //On va générer un mot de passe aléatoire qui sera envoyé par mail
            //Il sera ensuite demandé sur la page de vérification pour confirmer la candidature
            String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-@#&'(!?)$%?:;/.?,";
            Random rand = new Random();
            String secret ="";
            for (int i=0; i<6; i++)
                {
                 secret+=(alphabet.charAt(rand.nextInt(alphabet.length())));
                }
            //TODO enregistrer le mot de passe dans la base de donné (c'est juste un champ de vérification temporaire)
            
            //On prépare l'envoie du mail
            String url="http://localhost:8080/Inscription/";
            String destinataire=mail;
            String sujet="Confirmation d'inscription";
            String contenu="Merci de vous être inscrit. <br>"+
                    "Voici vos identifiants choisis :<br>"
                    + "Nom : "+nom+"<br>"
                    + "Prenom : "+prenom+"<br>"
                    + "Pour finaliser votre incription vous devez <a ref="+url+">cliquer sur le lien suivant</a> : "
                    + "Vous aurez besoin de saisir le mot de passe suivant : "+secret;
                    
            MailerBean.sendMail(destinataire, sujet, contenu);
            
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }*/
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
