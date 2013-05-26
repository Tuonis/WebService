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
     
     @Put
    public Representation doPut(Representation entity) throws SQLException {
    
      Form form = new Form(entity);
        String nom=form.getFirstValue("nom");
       
        Candidat candi = candidat.getByNom(nom);
        if (candi == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        String mdp = form.getFirstValue("mdp");
        System.out.println("test de mdp dans inscription ressource : "+mdp);
        String mdp2=candi.getMdp();
        System.out.println("test de mdp2 dans inscription ressource"+mdp2);
        if (mdp.equals(mdp2)){
            candi.setActif(true);
            System.out.println("le statut va etre mis en actif");
        }
        
        
        try {
            candi.update();
            setStatus(Status.SUCCESS_NO_CONTENT);
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new ResourceException(Status.CLIENT_ERROR_CONFLICT, "nomEnDoublon");

        }
        return null;
     }
}
