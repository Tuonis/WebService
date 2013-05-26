/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import com.miage.m1.Candidature.model.beans.Candidat;
import com.miage.m1.Candidature.model.beans.Candidature;
import com.miage.m1.Candidature.model.beans.InfosCandidature;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kentish
 */
public class test {
    public static void main(String[] args){
     List<InfosCandidature> infos=Candidature.getCandidatures();
     System.out.println(infos.size());
     
     Candidat candidat=new Candidat();
     candidat.setNom("test3");
     candidat.setPrenom("gnbin");
     candidat.setMail("tuonis01@gmail.com");
     candidat.setActif(false);
        try {
            candidat.insert();
            candidat.setActif(true);
            candidat.update();
            
        } catch (SQLException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
             
       
}
    

