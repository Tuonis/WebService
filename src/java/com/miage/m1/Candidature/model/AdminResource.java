/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.mail.MailerBean;
import com.miage.m1.Candidature.model.beans.Admin;
import com.miage.m1.Candidature.model.beans.Candidat;
import com.miage.m1.Candidature.model.beans.InfosCandidature;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AdminResource extends ServerResource {
    
    private Admin admin;
    private Representation resultat;
    
    protected boolean isAuthorized() {
        String email = getRequest().getChallengeResponse().getIdentifier();
        String mdp = new String(getRequest().getChallengeResponse().getSecret());
        Admin admi = null;
        try {
            admi = admin.getIdByMailMdp(email, mdp);
        } catch (SQLException sqlExc) {
            
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
        }

        return (admi != null);
    }
    
    @Get("xml")
    public Representation doGet() throws IOException {
        //init();
       if(isAuthorized()){
            try {
            DomRepresentation dom = new DomRepresentation(MediaType.TEXT_XML);
            // Generer un DOM representant la ressource
            Document doc = dom.getDocument();
            String mail = getRequest().getAttributes().get("email").toString();
            Admin ad = admin.getByMail(mail);
            Element root = doc.createElement("admins");
            doc.appendChild(root);
            Element info = doc.createElement("admin");
            info.setAttribute("id", String.valueOf(ad.getIdAdmin()));
            info.setAttribute("nom", ad.getNom());
            info.setAttribute("prenom", ad.getPrenom());
            info.setAttribute("mail", ad.getMail());
            root.appendChild(info);
            // Encodage en UTF-8
            dom.setCharacterSet(CharacterSet.UTF_8);
            resultat = dom;
            } catch (SQLException ex) {
                Logger.getLogger(AdminResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           throw new ResourceException(Status.CLIENT_ERROR_UNAUTHORIZED);
       }
        return resultat;
    }
    
}
