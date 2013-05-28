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
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuonis Home
 */
public class Candidature {

    private int idCandidat;
    private int idEtat;
    private int idPromotion;
    private String motivation;
    private Date dateCandidature;

    public Candidature(int idCandidat, int idEtat, int idPromotion, String motivation, Date dateCandidature) {
        this.idCandidat = idCandidat;
        this.idEtat = idEtat;
        this.idPromotion = idPromotion;
        this.motivation = motivation;
        this.dateCandidature = dateCandidature;
    }

    public Candidature() {
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public Date getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idCandidat;
        hash = 89 * hash + this.idEtat;
        hash = 89 * hash + this.idPromotion;
        return hash;
    }

    @Override
    public String toString() {
        return "Candidature{" + "idCandidat=" + idCandidat + ", idEtat=" + idEtat + ", idPromotion=" + idPromotion + ", motivation=" + motivation + ", dateCandidature=" + dateCandidature + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Candidature other = (Candidature) obj;
        if (this.idCandidat != other.idCandidat) {
            return false;
        }
        if (this.idEtat != other.idEtat) {
            return false;
        }
        if (this.idPromotion != other.idPromotion) {
            return false;
        }
        if ((this.motivation == null) ? (other.motivation != null) : !this.motivation.equals(other.motivation)) {
            return false;
        }
        if ((this.dateCandidature == null) ? (other.dateCandidature != null) : !this.dateCandidature.equals(other.dateCandidature)) {
            return false;
        }
        return true;
    }

    public static Candidature getCandidature(int idCandidat, int idPromotion) throws SQLException {

        Candidature candidature = null;
        Connection connection = Database.getConnection();
        PreparedStatement select = null;
        String sql = "SELECT * FROM candidature WHERE Candidat_idCandidat=? and Promotion_idPromotion=?";
        select = connection.prepareStatement(sql);
        select.setInt(1, idCandidat);
        select.setInt(2, idPromotion);
        ResultSet rs = select.executeQuery();
        if (rs.next()) {
            candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getDate("dateCandidature"));
        }
        rs.close();
        select.close();
        connection.close();


        return candidature;

    }

    public static List<Candidature> getByIdCandidat(int id) throws SQLException {

        List<Candidature> candidatures = new ArrayList<Candidature>();
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM candidature WHERE Candidat_idCandidat=?";
        PreparedStatement select = connection.prepareStatement(sql);
        select.setInt(1, id);
        ResultSet rs = select.executeQuery();;
        while (rs.next()) {
            Candidature candidature = new Candidature(rs.getInt("Candidat_idCandidat"), rs.getInt("Etat_idEtat"), rs.getInt("Promotion_idPromotion"), rs.getString("motivation"), rs.getDate("dateCandidature"));
            candidatures.add(candidature);
        }
        rs.close();
        select.close();
        connection.close();


        return candidatures;

    }

    public static List<InfosCandidature> getByIdEtat(int id) throws SQLException {

        List<InfosCandidature> infos = new ArrayList<InfosCandidature>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT c.idCandidat, p.idPromotion, idEtat, motivation, p.nom, etat, dateCandidature, c.nom, c.telephone, c.prenom, c.mail, c.adresse FROM candidat c, candidature, etat, promotion p WHERE idEtat =? AND Promotion_idPromotion = idPromotion AND Etat_idEtat = idEtat AND Candidat_idCandidat = idCandidat";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                InfosCandidature info = new InfosCandidature();
                info.setMotivation(rs.getString("motivation"));
                info.setEtat(rs.getString("etat"));
                info.setDateCandidature(rs.getString("dateCandidature"));
                info.setNom(rs.getString("c.nom"));
                info.setPrenom(rs.getString("prenom"));
                info.setMail(rs.getString("mail"));
                info.setAdresse(rs.getString("adresse"));
                info.setTelephone(rs.getString("telephone"));
                info.setNomPromotion(rs.getString("p.nom"));
                info.setIdCandidat(Integer.valueOf(rs.getString("c.idCandidat")));
                info.setIdPromotion(Integer.valueOf(rs.getString("p.idPromotion")));
                info.setIdEtat(Integer.valueOf(rs.getString("idEtat")));
                infos.add(info);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Candidature.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Database.close(rs);
                Database.close(stmt);
                Database.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return infos;

    }

    public static List<InfosCandidature> getByIdPromotion(int id) {

        List<InfosCandidature> infos = new ArrayList<InfosCandidature>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            String sql = "SELECT c.idCandidat, p.idPromotion, idEtat, motivation, p.nom, etat, dateCandidature, c.nom, c.telephone, c.prenom, c.mail, c.adresse FROM candidat c, candidature, etat, promotion p WHERE idPromotion =? AND Promotion_idPromotion = idPromotion AND Etat_idEtat = idEtat AND Candidat_idCandidat = idCandidat";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                InfosCandidature info = new InfosCandidature();
                info.setMotivation(rs.getString("motivation"));
                info.setEtat(rs.getString("etat"));
                info.setDateCandidature(rs.getString("dateCandidature"));
                info.setNom(rs.getString("c.nom"));
                info.setPrenom(rs.getString("prenom"));
                info.setMail(rs.getString("mail"));
                info.setAdresse(rs.getString("adresse"));
                info.setTelephone(rs.getString("telephone"));
                info.setNomPromotion(rs.getString("p.nom"));
                info.setIdCandidat(Integer.valueOf(rs.getString("c.idCandidat")));
                info.setIdPromotion(Integer.valueOf(rs.getString("p.idPromotion")));
                info.setIdEtat(Integer.valueOf(rs.getString("idEtat")));
                infos.add(info);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Candidature.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Database.close(rs);
                Database.close(stmt);
                Database.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return infos;

    }

    public static List<InfosCandidature> getCandidatures() {

        List<InfosCandidature> infos = new ArrayList<InfosCandidature>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT c.idCandidat, p.idPromotion, idEtat, motivation, p.nom, etat, dateCandidature, c.nom, c.telephone, c.prenom, c.mail, c.adresse FROM candidat c, candidature, etat, promotion p WHERE Promotion_idPromotion = idPromotion AND Etat_idEtat = idEtat AND Candidat_idCandidat = idCandidat");
            while (rs.next()) {
                InfosCandidature info = new InfosCandidature();
                info.setMotivation(rs.getString("motivation"));
                info.setEtat(rs.getString("etat"));
                info.setDateCandidature(rs.getString("dateCandidature"));
                info.setNom(rs.getString("c.nom"));
                info.setPrenom(rs.getString("prenom"));
                info.setMail(rs.getString("mail"));
                info.setAdresse(rs.getString("adresse"));
                info.setTelephone(rs.getString("telephone"));
                info.setNomPromotion(rs.getString("p.nom"));
                info.setIdCandidat(Integer.valueOf(rs.getString("c.idCandidat")));
                info.setIdPromotion(Integer.valueOf(rs.getString("p.idPromotion")));
                info.setIdEtat(Integer.valueOf(rs.getString("idEtat")));
                infos.add(info);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Candidature.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Database.close(rs);
                Database.close(stmt);
                Database.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return infos;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        // Commencer une transaction
        try {
            // Inserer le produit
            String sql = "INSERT INTO candidature(Candidat_idCandidat, Etat_idEtat, Promotion_idPromotion, motivation,dateCandidature) VALUES(?, ?, ?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCandidat);
            stmt.setInt(2, idEtat);
            stmt.setInt(3, idPromotion);
            stmt.setString(4, motivation);
            stmt.setDate(5, dateCandidature);

            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id

            // Valider
        } catch (SQLException exc) {
            //connection.rollback();
            exc.printStackTrace();
            throw exc;
        } finally {
            connection.close();
        }
    }

    public void delete() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM candidature WHERE Candidat_idCandidat=? and Promotion_idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCandidat);
        stmt.setInt(2, idPromotion);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "update candidature set motivation=?, Etat_idEtat=?, dateCandidature=? where Candidat_idCandidat=? and Promotion_idPromotion=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, motivation);
        stmt.setInt(2, idEtat);
        stmt.setDate(3, dateCandidature);
        stmt.setInt(4, idCandidat);
        stmt.setInt(5, idPromotion);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
