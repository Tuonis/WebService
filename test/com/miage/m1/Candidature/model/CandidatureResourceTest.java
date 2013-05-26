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
import org.restlet.representation.Representation;

/**
 *
 * @author Chanthavone
 */
public class CandidatureResourceTest {
    
//    public CandidatureResourceTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of init method, of class CandidatureResource.
//     */
//    @Test
//    public void testInit() {
//        System.out.println("init");
//        CandidatureResource instance = new CandidatureResource();
//        instance.init();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of doGet method, of class CandidatureResource.
//     */
//    @Test
//    public void testDoGet() throws Exception {
//        System.out.println("doGet");
//        CandidatureResource instance = new CandidatureResource();
//        Representation expResult = null;
//        Representation result = instance.doGet();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of doPut method, of class CandidatureResource.
//     */
//    @Test
//    public void testDoPut() throws Exception {
//        System.out.println("doPut");
//        Representation entity = null;
//        CandidatureResource instance = new CandidatureResource();
//        Representation expResult = null;
//        Representation result = instance.doPut(entity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of doPost method, of class CandidatureResource.
     */
    @Test
    public void testDoPost() throws Exception {
        System.out.println("doPost");
        Representation entity = null;
        CandidatureResource instance = new CandidatureResource();
        Representation expResult = null;
        Representation result = instance.doPost(entity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
