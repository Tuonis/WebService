/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.miage.m1.Candidature.model.Candidat;
import com.miage.m1.Candidature.model.Candidature;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kentish
 */
public class TestCandidature {

    private Candidature candidature;

    @Before
    public void setUp() {
    }

    /*
    @Test
    public void testInsert() {
        try {
            candidature = new Candidature();
            candidature.setIdCandidat(9);
            candidature.setIdEtat(3);
            candidature.setIdPromotion(2);
            candidature.setMotivation("J'adoreee");
            candidature.setDateCandidature("11/07/2013");
            candidature.insert();
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Test
    public void testGetCandidature() {
        try {
            candidature = new Candidature();
            Candidature c1=candidature.getCandidature(9,2);
            assertEquals("11/07/2013",c1.getDateCandidature());
            assertEquals(9,c1.getIdCandidat());
            assertEquals(2,c1.getIdPromotion());
            assertEquals(3,c1.getIdEtat());
            assertEquals("J'adoreee",c1.getMotivation());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetByIdCandidat() {
        try {
            candidature = new Candidature();
            List<Candidature> c1=candidature.getByIdCandidat(9);
            assertEquals("11/07/2013",c1.get(0).getDateCandidature());
            assertEquals(9,c1.get(0).getIdCandidat());
            assertEquals(2,c1.get(0).getIdPromotion());
            assertEquals(3,c1.get(0).getIdEtat());
            assertEquals("J'adoreee",c1.get(0).getMotivation());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
   /* public void testGetByIdEtat() {
        try {
            candidature = new Candidature();
            List<Candidature> c1=candidature.getByIdEtat(3);
            assertEquals("11/07/2013",c1.get(0).getDateCandidature());
            assertEquals(9,c1.get(0).getIdCandidat());
            assertEquals(2,c1.get(0).getIdPromotion());
            assertEquals(3,c1.get(0).getIdEtat());
            assertEquals("J'adoreee",c1.get(0).getMotivation());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetByIdPromotion() {
        try {
            candidature = new Candidature();
            List<Candidature> c1=candidature.getByIdPromotion(2);
            assertEquals("11/07/2013",c1.get(0).getDateCandidature());
            assertEquals(9,c1.get(0).getIdCandidat());
            assertEquals(2,c1.get(0).getIdPromotion());
            assertEquals(3,c1.get(0).getIdEtat());
            assertEquals("J'adoreee",c1.get(0).getMotivation());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @Test
    public void testGetCandidatures() {
            candidature = new Candidature();
            List<Candidature> c1=candidature.getCandidatures();
            assertEquals("11/07/2013",c1.get(0).getDateCandidature());
            assertEquals(9,c1.get(0).getIdCandidat());
            assertEquals(2,c1.get(0).getIdPromotion());
            assertEquals(3,c1.get(0).getIdEtat());
            assertEquals("J'adoreee",c1.get(0).getMotivation());
    }
    
    
    @Test
    public void testUpdate() {
        try {
            candidature = new Candidature();
            List<Candidature> c1=candidature.getByIdEtat(4);
            c1.get(0).setIdEtat(5);
            c1.get(0).update();
            List<Candidature> c2=candidature.getByIdEtat(5);
             
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    

    @After
    public void tearDown() {
        //suppression des donnees manipulees dans cette classe de test
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
