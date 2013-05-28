/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model.beans;

import com.miage.m1.Candidature.model.Database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuonis
 */
public class Promotion {

    private int id;
    private String nom;
    private Date dateDeb;
    private Date dateFin;
    private String periode;
    private Date dateDebInscription;
    private Date dateFinInscription;

    public Promotion(int id, String nom, Date dateDeb, Date dateFin, String periode, Date dateDebInscription, Date dabeFinInscription ) {
        this.id = id;
        this.nom = nom;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.periode = periode;
        this.dateDebInscription=dateDebInscription;
        this.dateFinInscription=dateFinInscription;
    }

    public Promotion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateDebInscription() {
        return dateDebInscription;
    }

    public void setDateDebInscription(Date dateDebInscription) {
        this.dateDebInscription = dateDebInscription;
    }

    public Date getDateFinInscription() {
        return dateFinInscription;
    }

    public void setDateFinInscription(Date dateFinInscription) {
        this.dateFinInscription = dateFinInscription;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Promotion other = (Promotion) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
            return false;
        }
        if ((this.dateDeb == null) ? (other.dateDeb != null) : !this.dateDeb.equals(other.dateDeb)) {
            return false;
        }
        if ((this.dateFin == null) ? (other.dateFin != null) : !this.dateFin.equals(other.dateFin)) {
            return false;
        }
        if ((this.periode == null) ? (other.periode != null) : !this.periode.equals(other.periode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", nom=" + nom + ", dateDeb=" + dateDeb + ", dateFin=" + dateFin + ", periode=" + periode + ", dateDebInscription=" + dateDebInscription + ", dateFinInscription=" + dateFinInscription + '}';
    }

    

    public static Promotion getById(int id) throws SQLException {
        Promotion promotion = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM promotion WHERE idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            promotion = new Promotion(rs.getInt("idPromotion"), rs.getString("nom"), rs.getDate("dateDeb"), rs.getDate("dateFin"), rs.getString("periode"), rs.getDate("dateDebInscription"), rs.getDate("dateFinInscription"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return promotion;
    }
    /**
     * 
     * @param nom de la promotion
     * @return id de la promotion
     * @throws SQLException 
     */
    public static int getIdByNom(String nom) throws SQLException {

        int id = 0;
        Connection connection = Database.getConnection();
        String sql = "SELECT idPromotion FROM promotion WHERE nom=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            id = rs.getInt("idPromotion");
        }
        rs.close();
        stmt.close();
        connection.close();

        return id;

    }

    public static List<Promotion> getPromotions() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Promotion> promotions = new ArrayList<Promotion>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM promotion";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Promotion promotion = new Promotion(rs.getInt("idPromotion"), rs.getString("nom"), rs.getDate("dateDeb"), rs.getDate("dateFin"), rs.getString("periode"), rs.getDate("dateDebInscription"), rs.getDate("dateFinInscription"));
                promotions.add(promotion);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            try {
                Database.close(rs);
                Database.close(ps);
                Database.close(connexion);
            } catch (SQLException ex) {
                Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return promotions;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        try {
            // Inserer le produit
            String sql = "INSERT INTO promotion(nom, dateDeb, dateFin, periode, dateDebInscription, dateFinInscription) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setDate(2, dateDeb);
            stmt.setDate(3, dateFin);
            stmt.setString(4, periode);
            stmt.setDate(5, dateDebInscription);
            stmt.setDate(6, dateFinInscription);
            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id
            Statement maxStmt = connection.createStatement();
            ResultSet rs = maxStmt.executeQuery("SELECT MAX(no_produit) AS id FROM promotion");
            rs.next();
            id = rs.getInt("id");
            rs.close();
            maxStmt.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw exc;
        } finally {
            connection.close();
        }
    }

    public void delete() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM promotion WHERE idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE promotion SET nom=?, dateDeb=?, dateFin=?, periode=?, dateDebInscription=?, dateFinInscription=? WHERE idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        stmt.setDate(2, dateDeb);
        stmt.setDate(3, dateFin);
        stmt.setString(4, periode);
        stmt.setDate(5,dateDebInscription);
        stmt.setDate(6,dateFinInscription);
        stmt.setInt(7, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
