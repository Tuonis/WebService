/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.miage.m1.Candidature.model.Candidat;
import com.miage.m1.Candidature.model.Promotion;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kentish
 */
public class TestPromotion {

    private Promotion promo;

    @Before
    public void setUp() {
    }

    /*@Test
     public void testInsert() {
     try {
     promo=new Promotion();
     promo.setNom("Gestion de projet informatique 2013");
     promo.setDateDeb("10/09/2013");
     promo.setDateFin("01/12/2013");
     promo.setPeriode("01/07/2013-01/09/2013");
     promo.insert();
     } catch (SQLException ex) {
     Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
    @Test
    public void testGetById() {
        try {
            promo = new Promotion();
            Promotion p1 = promo.getById(2);
            assertEquals("Gestion de projet informatique 2013", p1.getNom());
            assertEquals("10/09/2013", p1.getDateDeb());
            assertEquals("1/12/2013", p1.getDateFin());
            assertEquals("01/07/2013-01/09/2013", p1.getPeriode());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetByNom() {
        try {
            promo = new Promotion();
            Promotion p1 = promo.getByNom("Gestion de projet informatique 2013");
            assertEquals("Gestion de projet informatique 2013", p1.getNom());
            assertEquals("10/09/2013", p1.getDateDeb());
            assertEquals("1/12/2013", p1.getDateFin());
            assertEquals("01/07/2013-01/09/2013", p1.getPeriode());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetCandidats() {
        promo = new Promotion();
        List<Promotion> p1 = promo.getPromotions();
        assertEquals(2, p1.size());
    }
    
    @Test
    public void testUpdate() {
        try {
            promo = new Promotion();
            Promotion p1=promo.getByNom("Gestion de projet web");
            p1.setNom("Gestion de projet web avancee");
            p1.update();
            Promotion p2=promo.getByNom("Rooney jr");
             
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
