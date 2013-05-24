
import com.miage.m1.Candidature.model.Candidat;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kentish
 */
public class testInsert {
    public static void main(String[] arg){
        try {
            Candidat c=new Candidat();
            c.setNom("munu");
            c.setPrenom("ken");
            c.setAdresse("yo");
            c.setCompetence("yo");
            c.setDiplome("yo");
            c.setMail("yo");
            c.setMdp("de");
            c.setSituationPro("derdf");
            c.setTelephone("dezdf");
            c.insert();
        } catch (SQLException ex) {
            Logger.getLogger(testInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
