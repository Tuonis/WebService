/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.model.beans;

import com.miage.m1.Candidature.model.Database;
import com.miage.m1.Candidature.model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe permet de representer un candidat avec tous les parametres le representant
 * 
 * @author Tuonis Home
 */
public class Candidat {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private String adresse;
    private String mdp;
    private String diplome;
    private String competence;
    private String situationPro;
    private String motivation;
    private boolean actif;

    public Candidat(int id, String nom, String prenom, String telephone, String mail, String adresse, String mdp, String diplome, String competence, String situationPro, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = mdp;
        this.diplome = diplome;
        this.competence = competence;
        this.situationPro = situationPro;
        this.actif = actif;
    }

    public Candidat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
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

    public String getTelephone() {
        return telephone;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getSituationPro() {
        return situationPro;
    }

    public void setSituationPro(String situationPro) {
        this.situationPro = situationPro;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + (this.nom != null ? this.nom.hashCode() : 0);
        hash = 17 * hash + (this.prenom != null ? this.prenom.hashCode() : 0);
        hash = 17 * hash + (this.telephone != null ? this.telephone.hashCode() : 0);
        hash = 17 * hash + (this.mail != null ? this.mail.hashCode() : 0);
        hash = 17 * hash + (this.adresse != null ? this.adresse.hashCode() : 0);
        hash = 17 * hash + (this.mdp != null ? this.mdp.hashCode() : 0);
        hash = 17 * hash + (this.diplome != null ? this.diplome.hashCode() : 0);
        hash = 17 * hash + (this.competence != null ? this.competence.hashCode() : 0);
        hash = 17 * hash + (this.situationPro != null ? this.situationPro.hashCode() : 0);
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
        final Candidat other = (Candidat) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
            return false;
        }
        if ((this.prenom == null) ? (other.prenom != null) : !this.prenom.equals(other.prenom)) {
            return false;
        }
        if ((this.telephone == null) ? (other.telephone != null) : !this.telephone.equals(other.telephone)) {
            return false;
        }
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        if ((this.adresse == null) ? (other.adresse != null) : !this.adresse.equals(other.adresse)) {
            return false;
        }
        if ((this.mdp == null) ? (other.mdp != null) : !this.mdp.equals(other.mdp)) {
            return false;
        }
        if ((this.diplome == null) ? (other.diplome != null) : !this.diplome.equals(other.diplome)) {
            return false;
        }
        if ((this.competence == null) ? (other.competence != null) : !this.competence.equals(other.competence)) {
            return false;
        }
        if ((this.situationPro == null) ? (other.situationPro != null) : !this.situationPro.equals(other.situationPro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Candidat{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", mail=" + mail + ", adresse=" + adresse + ", diplomes=" + diplome + ", competences=" + competence + ", situationPro=" + situationPro + '}';
    }
    /**
     * Renvoie le candidat de la base de donnée associé à l'id passé en paramètre
     * 
     * @param id
     * @return candidat
     * @throws SQLException 
     */
    public static Candidat getById(int id) throws SQLException {
        Candidat candidat = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM candidat WHERE idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("diplomes"), rs.getString("competences"), rs.getString("situationprofessionnelle"), rs.getBoolean("actif"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return candidat;
    }
    /**
     * Renvoie le candidat de la base de donnée associé au mail passé en paramètre
     * 
     * @param mail
     * @return
     * @throws SQLException 
     */
    public static Candidat getByMail(String mail) throws SQLException {
        Candidat candidat = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM candidat WHERE mail=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, mail);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("diplomes"), rs.getString("competences"), rs.getString("situationprofessionnelle"), rs.getBoolean("actif"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return candidat;
    }
    /**
     * Renvoie le mot de passe de la base de donnée associé au mail passé en paramètre
     * 
     * @param email
     * @return String mot de passe
     */
    public static String getMdpOubli(String email) {
        String mail = "";
        Connection connection;
        try {
            connection = Database.getConnection();
            String sql = "SELECT mdp from candidat WHERE mail=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mail = rs.getString("mdp");
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Candidat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mail;
    }
    /**
     * Renvoie le candidat de la base de donnée associé à l'email et 
     * le mot de passe donné en paramètre
     * 
     * @param email
     * @param mdp
     * @return candidat
     * @throws SQLException 
     */
    public static Candidat getIdByMailMdp(String email, String mdp) throws SQLException {
        Candidat candidat = null;
        Connection connection;
        try {
            connection = Database.getConnection();
            String sql = "SELECT * from candidat WHERE mail=? and mdp=? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, mdp);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("diplomes"), rs.getString("competences"), rs.getString("situationprofessionnelle"), rs.getBoolean("actif"));
        
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            int n = ex.getErrorCode();
            throw ex;
        }

        return candidat;
    }
    /**
     * Renvoie le candidat de la base de donnée associé au nom passé en paramètre
     * 
     * @param nom
     * @return candidat
     * @throws SQLException 
     */
    public static Candidat getByNom(String nom) throws SQLException {
        Candidat candidat = null;
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM candidat WHERE nom=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("diplomes"), rs.getString("competences"), rs.getString("situationprofessionnelle"), rs.getBoolean("actif"));
        }
        rs.close();
        stmt.close();
        connection.close();

        return candidat;
    }
    /**
     * 
     * @param id du candidat 
     * @return la liste des candidatures d'un candidat
     * @throws SQLException 
     */
    public static List<InfosCandidature> getInfosCandidature(int id) throws SQLException {
        List<InfosCandidature> infos = new ArrayList<InfosCandidature>();
        Connection connection = Database.getConnection();
        String sql = "SELECT nom, dateCandidature, etat FROM candidature, promotion, etat WHERE Candidat_idCandidat =? AND Etat_idEtat = idEtat AND idPromotion = Promotion_idPromotion";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            InfosCandidature info = new InfosCandidature();
            info.setNomPromotion(rs.getString("nom"));
            info.setDateCandidature(rs.getString("dateCandidature"));
            info.setEtat(rs.getString("etat"));
            infos.add(info);
        }
        rs.close();
        stmt.close();
        connection.close();

        return infos;
    }
    /**
     * 
     * @return la liste des candidats
     */
    public static List<Candidat> getCandidats() {

        Connection connexion = null;
        Statement ps = null;
        ResultSet rs = null;
        List<Candidat> candidats = new ArrayList<Candidat>();
        try {
            connexion = Database.getConnection();

            String sql = "SELECT * FROM candidat";
            ps = connexion.createStatement();
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Candidat candidat = new Candidat(rs.getInt("idCandidat"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("mail"), rs.getString("adresse"), rs.getString("mdp"), rs.getString("diplomes"), rs.getString("competences"), rs.getString("situationprofessionnelle"), rs.getBoolean("actif"));
                candidats.add(candidat);
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
        return candidats;
    }
    /**
     * Ajoute un candidat à la base de donnée
     * @throws SQLException 
     */
    public void insert() throws SQLException {
        Connection connection = Database.getConnection();
        try {
            // Inserer le produit
            String sql = "INSERT INTO candidat(nom, prenom, telephone, mail, adresse, mdp, diplomes, competences, situationprofessionnelle, actif) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, telephone);
            stmt.setString(4, mail);
            stmt.setString(5, adresse);
            stmt.setString(6, mdp);
            stmt.setString(7, diplome);
            stmt.setString(8, competence);
            stmt.setString(9, situationPro);
            stmt.setBoolean(10, actif);
            stmt.executeUpdate();
            stmt.close();
            // Recuperer le id
            Statement maxStmt = connection.createStatement();
            ResultSet rs = maxStmt.executeQuery("SELECT MAX(idCandidat) AS id FROM candidat");
            rs.next();
            id = rs.getInt("id");
            rs.close();
            maxStmt.close();
            // Valider
            //connection.commit();
        } catch (SQLException exc) {
            //connection.rollback();
            exc.printStackTrace();
            throw exc;
        } finally {
            connection.close();
        }
    }
    /**
     * Supprime un candidat de la base de donnée
     * @throws SQLException 
     */
    public void delete() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM candidat WHERE idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    /**
     * Met à jour un candidat dans la base de donnée
     * @throws SQLException 
     */
    public void update() throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "UPDATE candidat SET nom=?, prenom=?, telephone=?, mail=?, adresse=?, mdp=?, diplomes=?, competences=?, situationprofessionnelle=?, actif=? WHERE idCandidat=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nom);
        stmt.setString(2, prenom);
        stmt.setString(3, telephone);
        stmt.setString(4, mail);
        stmt.setString(5, adresse);
        stmt.setString(6, mdp);
        stmt.setString(7, diplome);
        stmt.setString(8, competence);
        stmt.setString(9, situationPro);
        stmt.setBoolean(10, actif);
        stmt.setInt(11, id);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
