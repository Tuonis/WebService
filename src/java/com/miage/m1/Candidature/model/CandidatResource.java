/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Candidat;
import com.miage.m1.Candidature.model.beans.InfosCandidature;
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

    protected boolean isAuthorized() {
        String email = getRequest().getChallengeResponse().getIdentifier();
        String mdp = new String(getRequest().getChallengeResponse().getSecret());
        Candidat candi = null;
        try {
            candi = candidat.getIdByMailMdp(email, mdp);
            if (!candi.isActif()) {
                throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
            }
        } catch (SQLException sqlExc) {
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }

        return (candi != null);
    }

    @Get("xml")
    public Representation doGet() throws IOException {
        //init();
        if (isAuthorized()) {
            try {

                DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
                // Generer un DOM representant la ressource
                Document doc = dom.getDocument();

                if (getRequest().getAttributes().get("email") != null) {
                    String mail = getRequest().getAttributes().get("email").toString();
                    Candidat candi = candidat.getByMail(mail);
                    List<InfosCandidature> infos = candidat.getInfosCandidature(candi.getId());
                    if (infos == null) {
                        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
                    }
                    Element root = doc.createElement("infosCandidature");
                    doc.appendChild(root);
                    for (int i = 0; i < infos.size(); i++) {
                        Element info = doc.createElement("infoCandidature");
                        info.setAttribute("nomPromotion", infos.get(i).getNomPromotion());
                        info.setAttribute("dateCandidature", infos.get(i).getDateCandidature());
                        info.setAttribute("etat", infos.get(i).getEtat());
                        root.appendChild(info);
                    }
                    Element infoCandi = doc.createElement("infoCandidat");
                    infoCandi.setAttribute("id", String.valueOf(candi.getId()));
                    infoCandi.setAttribute("nom", candi.getNom());
                    infoCandi.setAttribute("prenom", candi.getPrenom());
                    infoCandi.setAttribute("telephone", candi.getTelephone());
                    infoCandi.setAttribute("adresse", candi.getAdresse());
                    infoCandi.setAttribute("mail", candi.getMail());
                    infoCandi.setAttribute("diplomes", candi.getDiplome());
                    infoCandi.setAttribute("competences", candi.getCompetence());
                    infoCandi.setAttribute("situation", candi.getSituationPro());
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

                    //Envoie du mail qui contient le mot de passe :
                    String destinataire = (String) getRequest().getAttributes().get("mail");
                    String sujet = "Récupération mot de passe";
                    String contenu = "Votre mot de passe est le suivant : " + motPasse;


                    MailerBean.sendMail(destinataire, sujet, contenu);


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //HashMap m=new HashMap(); 
            //m.put("dom", resultat);
            //getRequest().setAttributes(m);
        } else {
            throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        return resultat;
    }

    @Put
    public Representation doPut(Representation entity) throws SQLException {
        //init();
        if (isAuthorized()) {
            Form form = new Form(entity);
            int id = Integer.parseInt(form.getFirstValue("id"));
            Candidat candi = candidat.getById(id);
            if (candi == null) {
                throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
            }

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

                candi.setNom(nom);

            }

            if (prenom != null) {

                candi.setPrenom(prenom);

            }

            if (tel != null) {

                candi.setTelephone(tel);

            }

            if (mail != null) {

                candi.setMail(mail);

            }

            if (adresse != null) {

                candi.setAdresse(adresse);

            }

            if (diplome != null) {

                candi.setDiplome(diplome);

            }

            if (competence != null) {

                candi.setCompetence(competence);

            }

            if (situationPro != null) {

                candi.setSituationPro(situationPro);

            }

            try {
                candi.update();
                setStatus(Status.SUCCESS_NO_CONTENT);
            } catch (SQLException exc) {
                exc.printStackTrace();
                throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

            }
        } else {
            throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        return null;
    }

    @Post
    public Representation doPost(Representation entity) throws SQLException {
        //init();
        
            String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYX0123456789";
            Candidat candidat = new Candidat();
            Form form = new Form(entity);
            String nom = form.getFirstValue("nom");
            String prenom = form.getFirstValue("prenom");
            String tel = form.getFirstValue("tel");
            String mail = form.getFirstValue("mail");
            String adresse = form.getFirstValue("adresse");
            String diplome = form.getFirstValue("diplome");
            String competence = form.getFirstValue("competence");
            String situationPro = form.getFirstValue("situationPro");
            if (nom == null && prenom == null && tel == null && mail == null && adresse == null && diplome == null
                    && competence == null && situationPro == null) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "pasDeParametre");
            }

            if (nom == null || prenom == null || mail == null) {
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
            candidat.setActif(false);
            String mdp = nom.charAt(0) + "" + prenom.charAt(0) + "_";
            Random rnd = new Random();
            for (int i = 0; i < 7; i++) {
                int n = rnd.nextInt(alphabet.length());
                mdp += alphabet.charAt(n);
            }
            candidat.setMdp(mdp);

            candidat.insert();
            // try {

            setStatus(Status.SUCCESS_NO_CONTENT);

            //On va générer un mot de passe aléatoire qui sera envoyé par mail
            //Il sera ensuite demandé sur la page de vérification pour confirmer la candidature
            /*String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-@#&'(!?)$%?:;/.?,";
             Random rand = new Random();
             String secret ="";
             for (int i=0; i<6; i++)
             {
             secret+=(alphabet.charAt(rand.nextInt(alphabet.length())));
             }
             //TODO enregistrer le mot de passe dans la base de donné (c'est juste un champ de vérification temporaire)
             */
            //On prépare l'envoie du mail
            String url = "http://localhost:8080/WA_cmsl/confirmationInscription.jsp?name=" + nom;
            String destinataire = mail;
            String sujet = "Confirmation d'inscription";
            String contenu = "Merci de vous être inscrit. <br/>"
                    + "Voici vos identifiants choisis :<br/>"
                    + "Nom : " + nom + "<br/>"
                    + "Prenom : " + prenom + "<br/>"
                    + "Pour finaliser votre incription vous devez <a href=" + url + ">cliquer sur le lien suivant</a> : <br/>"
                    + "Vous aurez besoin de saisir le mot de passe suivant : " + mdp;

            MailerBean.sendMail(destinataire, sujet, contenu);

            /*} catch (SQLException exc) {
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
