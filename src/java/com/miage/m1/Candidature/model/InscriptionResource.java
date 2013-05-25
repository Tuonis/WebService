/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Candidat;
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
 * @author Tuonis Home
 */
public class InscriptionResource extends ServerResource{
    
    
    /**
     * Id du candidat correspondant
     */
    int id;
    /**
     * Le candidat correspondant
     */
    Candidat candidat;
    
  
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
            
            if (getRequest().getAttributes().get("nom") != null && getRequest().getAttributes().get("prenom")!=null 
                    && getRequest().getAttributes().get("mail")!=null) {
                String nom=getRequest().getAttributes().get("nom").toString();
                String prenom=getRequest().getAttributes().get("prenom").toString();
                String mail=getRequest().getAttributes().get("mail").toString();
                
                Candidat candi=candidat.getByNom(nom);
               
               
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //HashMap m=new HashMap(); 
        //m.put("dom", resultat);
        //getRequest().setAttributes(m);
        return resultat;
    }
    
}
