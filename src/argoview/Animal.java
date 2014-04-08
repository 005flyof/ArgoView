/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argoview;

import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

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
    public Animal ( String nom, String nomFichier ) {
        this.nom = nom;
        this.setNomFichier(nomFichier);
    }
    
    /*
        Déclaration des variables
    */
    private String nom = new String();
    private String nomFichier = new String();
    private String url = new String();
    private ArrayList positions = new ArrayList();
    private final char separateur = ' ';
    private JTable tableau = new JTable();

    /**
     * Permet de lire les données contenus dans "Positions/%nomFichier.txt"
     */
    public void lireDonnees() {
        int cpt = 0;    // Variable permettant de ne pas enregistrer les deux premières lignes du fichier de position
        try {
            // On ouvre le fichier
            InputStream inStream = new FileInputStream(nomFichier);
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
            JOptionPane.showMessageDialog(tableau,
                    "Impossible d'ouvrir le fichier :\n\tErreur d'entée-sortie",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
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
     * Permet de télécharger le fichier de positions
     */
    public void telechargerFichier() {
        try {
            // On crée les variables pour télécharger le fichier
            URL adresse = new URL("http://" + url);
            URLConnection connexion = adresse.openConnection();
            int taille = connexion.getContentLength();

            // On crée un flux d’entrée pour le fichier
            InputStream brut = connexion.getInputStream();

            // Mettre ce flux d’entrée en cache (pour un meilleur transfert, plus sûr et plus régulier).
            InputStream entree = new BufferedInputStream(brut);

            // Créer une matrice (un tableau) de bytes pour stocker tous les octets du fichier
            byte[] donnees = new byte[taille];

            // Pour l’instant aucun octet n’a encore été lu
            int octetsLus = 0;

            // Octets de déplacement, et octets déjà lus.
            int deplacement = 0; float alreadyRead = 0;

            // Boucle permettant de parcourir tous les octets du fichier à lire
            while(deplacement < taille)
            {
                // utilisation de la methode "read" de la classe InputStream
                octetsLus = entree.read(donnees, deplacement, donnees.length-deplacement);

                // Petit calcul: mise à jour du nombre total d’octets lus par ajout au nombre d’octets lus au cours des précédents passages au nombre d’octets lus pendant ce passage
                alreadyRead = alreadyRead + octetsLus;

                // -1 marque par convention la fin d’un fichier, double opérateur "égale": = =
                if(octetsLus == -1) break;

                // se cadrer à un endroit précis du fichier pour lire les octets suivants, c’est le déplacement
                deplacement += octetsLus;
            }
            // fermer le flux d’entrée.
            entree.close();

            // Récupérer le nom du fichier
            String fichier = adresse.getFile();
            fichier = fichier.substring(fichier.lastIndexOf('/') + 1);

            // Créer un fichier sur le DD afin d’y copier le contenu du fichier téléchargé (par un flux de sortie).
            FileOutputStream fichierSortie = new FileOutputStream(nomFichier);

            // copier…
            fichierSortie.write(donnees);

            // vider puis fermer le flux de sortie
            fichierSortie.flush(); fichierSortie.close();
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(tableau,
                    "URL de téléchargement mal formée !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(tableau,
                    "Impossible de télécharger le fichier :\n\tErreur d'entée-sortie",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return Renvoie le nom du fichier de position à lire
     */
    public String getNomFichier() {
        return nomFichier;
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
    public void setNomFichier(String nomFichier) {
        this.nomFichier = "Positions/" + nomFichier + ".txt";
        this.url = "argonautica.jason.oceanobs.com/documents/argonautica/2013-2014/" + nomFichier + ".txt";
    }

    /**
     * @return Renvoie un tableau dynamique des positions successives
     */
    public ArrayList getPositions() {
        return positions;
    }

    /**
     * @return Renvoie le tableau contenant les positions
     */
    public JTable getTableau() {
        return tableau;
    }
}
