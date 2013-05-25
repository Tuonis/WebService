/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.representation.Representation;

/**
 *
 * @author Chanthavone
 */
public class CandidatResourceTest {
    
    public CandidatResourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class CandidatResource.
     */
//    @Test
//    public void testInit() {
//        System.out.println("init");
//        CandidatResource instance = new CandidatResource();
//        instance.init();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of doGet method, of class CandidatResource.
     */
//    @Test
//    public void testDoGet() throws Exception {
//        System.out.println("doGet");
//        CandidatResource instance = new CandidatResource();
//        Representation expResult = null;
//        Representation result = instance.doGet();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of doPut method, of class CandidatResource.
     */
//    @Test
//    public void testDoPut() throws Exception {
//        System.out.println("doPut");
//        Representation entity = null;
//        CandidatResource instance = new CandidatResource();
//        Representation expResult = null;
//        Representation result = instance.doPut(entity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of doPost method, of class CandidatResource.
     */
    @Test
    public void testDoPost() throws Exception {
        System.out.println("doPost");
        Representation entity = null;
        CandidatResource instance = new CandidatResource();
        Representation expResult = null;
        
        Form form = new Form();
        form.encode(CharacterSet.UTF_8);
        form.add("nom", "parinaud");
        form.add("prenom", "patricia");
        form.add("telephone", "0146319670");
        form.add("mail", "pat-pa@hotmail.com");
        form.add("mdp", "fdsfds");
        form.add("adresse", "4,a venueafr");
        form.add("diplome", "gfdsgfd");
        form.add("competence", "gfdsgfds");
        form.add("situationprofessionnelle", "gfdsgfd");
        form.add("motivation", "gfdsgfds");
        Representation rep = form.getWebRepresentation();
        
        
        Representation result = instance.doPost(rep);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of doDelete method, of class CandidatResource.
     */
//    @Test
//    public void testDoDelete() throws Exception {
//        System.out.println("doDelete");
//        CandidatResource instance = new CandidatResource();
//        Representation expResult = null;
//        Representation result = instance.doDelete();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
