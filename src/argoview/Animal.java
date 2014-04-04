/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argoview;

import java.util.ArrayList;
import java.io.*;

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
    private final char separateur = ' ';

    /**
     * Permet de lire les données contenus dans "Positions/%nomFichier.txt"
     */
    private void lireDonnees() {
        int cpt = 0;
        try {
            // On ouvre le fichier
            InputStream inStream = new FileInputStream("Positions\\" + nomFichier + ".txt");
            InputStreamReader inStreamRead = new InputStreamReader(inStream);
            BufferedReader buffer = new BufferedReader(inStreamRead);
            String ligne;
            
            // Tant que l'on peut lire une ligne
            while ( (ligne = buffer.readLine()) != null ) {
                int i = 0;
                
                // Numéro de balise
                String numBalise = "";
                while (ligne.charAt(i) != this.separateur) {
                    numBalise += ligne.charAt(i);
                    i++;
                }
                // On passe aux données suivantes
                i = donneeSuivante(ligne, i);
                
                // Précision
                String precision = "";
                while (ligne.charAt(i) != ' ') {
                    precision += ligne.charAt(i);
                    i++;
                }
                // On passe aux données suivantes
                i = donneeSuivante(ligne, i);
                
                // Date
                String date = "";
                while (ligne.charAt(i) != ' ') {
                    date += ligne.charAt(i);
                    i++;
                }
                // On passe aux données suivantes
                i = donneeSuivante(ligne, i);
                
                // Heure
                String heure = "";
                while (ligne.charAt(i) != ' ') {
                    heure += ligne.charAt(i);
                    i++;
                }
                // On passe aux données suivantes
                i = donneeSuivante(ligne, i);
                
                // Latitude
                String latitude = "";
                while (ligne.charAt(i) != ' ') {
                    latitude += ligne.charAt(i);
                    i++;
                }
                // On passe aux données suivantes
                i = donneeSuivante(ligne, i);
                
                // Longitude
                String longitude = "";
                while (ligne.charAt(i) != ' ') {
                    longitude += ligne.charAt(i);
                    i++;
                }
                
                // On traite les données
                if (cpt != 0 && cpt != 1)
                    positions.add(new DonneeArgos(
                            numBalise, precision, date, heure, latitude, longitude));
                System.out.println(numBalise);
                cpt++;
                
            }
            // On ferme le fichier
            buffer.close();
        } catch (Exception e) {
            // S'il y a eu une erreur, on l'affiche
            System.out.println( e.toString() );
        }
    }
    
    /**
     * Permet de trouver l'id du premier caractère des données suivantes
     * @param chaine    Chaîne à parcourir
     * @param i         ID de début de parcours
     * @return ID du premier caractère des données suivantes
     */
    private int donneeSuivante( String chaine, int i ) {
        while (chaine.charAt(i) == this.separateur)
            i++;
        return i;
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
