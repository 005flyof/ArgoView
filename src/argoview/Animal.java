/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argoview;

import java.util.ArrayList;

/**
 * Objet permettant de gérer les différents animaux
 * @author Florent Fayollas
 */
public class Animal {
    
    /**
     * Constructeur de l'objet Animal
     * @param nom         Nom de l'animal
     * @param nomFichier  Nom de fichier à ouvrir pour lire les positions
     * @param url         Adresse de téléchargement du fichier de position
     */
    public Animal ( String nom, String nomFichier, String url ) {
        this.nom = nom;
        this.nomFichier = nomFichier;
        this.url = url;
        
        lireDonnees();
    }
    
    /*
        Déclaration des variables
    */
    private String nom = new String();
    private String nomFichier = new String();
    private String url = new String();
    private ArrayList positions = new ArrayList();

    /**
     * Permet de lire les données contenus dans "Positions/%nomFichier.txt"
     */
    private void lireDonnees() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return Renvoie le nom de l'animal
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom Nom de l'animal
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return Renvoie l'URL de téléchargement des données
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url URL de téléchargement des données
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Renvoie un tableau dynamique des positions successives
     */
    public ArrayList getPositions() {
        return positions;
    }

    /**
     * @param positions Tableau dynamique des positions sucessives
     */
    public void setPositions(ArrayList positions) {
        this.positions = positions;
    }

    /**
     * @return Renvoie le nom du fichier de position à lire
     */
    public String getNomFichier() {
        return nomFichier;
    }

    /**
     * @param nomFichier Nom du fichier de position à lire
     */
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
}
