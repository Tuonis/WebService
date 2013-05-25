
import com.miage.m1.Candidature.model.beans.Candidat;
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
            Candidat c=Candidat.getById(1);
            c.setCompetence("uml, java");
            c.update();
           System.out.print(c.getSituationPro());
        } catch (SQLException ex) {
            Logger.getLogger(testInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
