/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.image;

/**
 *
 * @author Tuonis Home
 */
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Redimensionner une image
 */
public class ImageRedim {

    public static BufferedImage scale(BufferedImage bImage, double factor) {
        int destWidth=(int) (300);
        int destHeight=(int) (bImage.getHeight() * factor);
        //créer l'image de destination
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        //dessiner l'image de destination
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();

        return bImageNew;
    }
    
    /*
     * Modifie la taille de l'image à 300 en largeur
     */
    public static BufferedImage scale(BufferedImage bImage) {
        int destWidth=(int) (300);
        int destHeight=(int) (bImage.getHeight());
        //créer l'image de destination
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bImageNew = configuration.createCompatibleImage(destWidth, destHeight);
        Graphics2D graphics = bImageNew.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        //dessiner l'image de destination
        graphics.drawImage(bImage, 0, 0, destWidth, destHeight, 0, 0, bImage.getWidth(), bImage.getHeight(), null);
        graphics.dispose();

        return bImageNew;
    }

        
    public static void main(String[] args) {
        try {
            //Exemple pour agrandir
            File file=new File("F:\\Anima\\Portrait\\Warrior\\317631.jpg");
           
            long tailleKo =file.length()/1024;
           
           
            System.out.println("Taille de l'image en Ko : "+tailleKo);
            
            BufferedImage img = ImageIO.read(new File("F:\\Anima\\Portrait\\Warrior\\317631.jpg"));
            BufferedImage imgnew=scale(img, 10);
            
            //Image test=imgnew.getScaledInstance(100, 100, 100);
            //BufferedImage imgtest=(BufferedImage) test;
            ImageIO.write(imgnew, "jpg", new File("F:\\Anima\\Portrait\\Warrior\\jgrand.jpg"));
           
            //Exemple de reduction
            BufferedImage imag = ImageIO.read(new File("F:\\Anima\\Portrait\\Warrior\\317631.jpg"));
            BufferedImage imagnew=scale(img, 0.5);
            ImageIO.write(imagnew, "png", new File("F:\\Anima\\Portrait\\Warrior\\jpetit.png"));
        } catch (IOException ex) {
            Logger.getLogger(ImageRedim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}