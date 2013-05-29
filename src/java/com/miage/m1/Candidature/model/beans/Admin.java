/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model.beans;

import com.miage.m1.Candidature.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bean représentant un admin
 * Contient toutes les méthodes pour récupérer et mettre à jour les informations 
 * d'un admin dans la base de donnée
 * 
 * @author Kentish
 */
public class Admin {

    public Admin(int idAdmin, String nom, String prenom, String mail, String mdp) {
        this.idAdmin = idAdmin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
    }
    private int idAdmin;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    /**
     * renvoie l'admin de la base de donnée associé à l'id passé en paramètre
     * 
     * @param id de l'admin
     * @return admin 
     * @throws SQLException 
     */
    public static Admin getById(int id) throws SQLException {
        Admin admin = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM admin WHERE idAdmin=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            admin = new Admin(rs.getInt("idAdmin"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getString("mdp"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return admin;
    }
    /**
     * Renvoie l'admin de la base de donnée associé à l'email passé en paramètre
     * 
     * @param mail
     * @return admin
     * @throws SQLException 
     */
    public static Admin getByMail(String mail) throws SQLException {
        Admin admin = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM admin WHERE mail=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, mail);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            admin = new Admin(rs.getInt("idAdmin"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getString("mdp"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return admin;
    }
    /**
     * Renvoie l'admin de la base de donnée associé à l'email et mot de passe
     * passé en paramètre
     * 
     * @param email
     * @param mdp
     * @return admin
     * @throws SQLException 
     */
    public static Admin getAdminByMailMdp(String email, String mdp) throws SQLException {
        Admin admin = null;
        Connection connection;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * from admin WHERE mail=? and mdp=? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, mdp);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(rs.getInt("idAdmin"), rs.getString("nom"), rs.getString("prenom"), rs.getString("mail"), rs.getString("mdp"));
        
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            int n = ex.getErrorCode();
            throw ex;
        }

        return admin;
    }
}
