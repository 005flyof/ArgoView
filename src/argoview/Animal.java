/*
 * Copyright (C) 2014 005flyof <geek-man@users.sourceforge.net> & delta3200 <dorian31750@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package argoview;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe permettant de gérer les différents animaux
 * @author 005flyof
 */
public class Animal { 
    /*
        Déclaration des variables
    */
    private String nom = new String();
    private String nomFichier = new String();
    private String url = new String();
    private final ArrayList<DonneeArgos> positions;
    private final char separateur = ' ';
    
    
    /**
     * Constructeur de l'objet Animal
     * @param nom         Nom de l'animal (affiché)
     * @param nomFichier  Nom de fichier à ouvrir pour lire les positions
     * @deprecated Utiliser le nouveau constructeur car celui là n'est pas flexible pour un paramétrage simpl
     */
    public Animal ( String nom, String nomFichier ) {
        this.nom = nom;
        this.nomFichier = "Positions/" + nomFichier + ".txt";
        this.url = "http://argonautica.jason.oceanobs.com/documents/argonautica/2013-2014/" + nomFichier + ".txt";
        this.positions = new ArrayList();
    }
    
    
    /**
     * Constructeur de l'objet Animal
     * @param nom         Nom de l'animal (affiché)
     * @param nomFichier  Nom de fichier à ouvrir pour lire les positions
     * @param URL         URL de téléchargement du fichier de positions
     */
    public Animal ( String nom, String nomFichier, String URL ) {
        this.nom = nom;
        this.nomFichier = nomFichier;
        this.url = URL;
        this.positions = new ArrayList();
    }

    
    /**
     * Permet de lire les données contenus dans "Positions/%nomFichier%.txt"
     * @param affErr      Affiche les fenêtre d'infos d'erreur si true
     * @throws Exception  N'importe quelle erreur lors du chargement du fichier de position
     */
    public void lireDonnees( boolean affErr ) throws Exception {
        try {
            chargerFichier();
        }
        // S'il y a eu une erreur, on l'affiche
        catch (IOException e) {
            if (affErr)
                JOptionPane.showMessageDialog(null,
                        "Impossible d'ouvrir le fichier : " + nomFichier
                                + "\n\tErreur d'entée-sortie :\n" + e.toString(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println( e.toString() );
            throw e;
        } catch (IndexOutOfBoundsException e) {
            if (affErr)
                JOptionPane.showMessageDialog(null,
                        "Impossible de charger le fichier : " + nomFichier
                                + "\n\tLe fichier n'est pas conforme :\n" + e.toString(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println( e.toString() );
            throw e;
        }
    }

    /**
     * Permet de charger le fichier de positionnement
     * @throws Exception  N'importe quelle erreur lors du chargement du fichier de position
     */
    private void chargerFichier() throws Exception {
        // On ouvre le fichier
        InputStream flux = new FileInputStream(nomFichier);
        InputStreamReader fluxEntree = new InputStreamReader(flux);
        BufferedReader buffer = new BufferedReader(fluxEntree);
        String ligne;

        // Variable permettant de ne pas enregistrer les deux premières lignes du fichier de position
        int cpt = 0;

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
                this.positions.add(
                        new DonneeArgos(
                                numBalise, precision,
                                date, heure,
                                latitude, longitude));
            cpt++;

        }
        // On ferme le fichier
        buffer.close();
        fluxEntree.close();
        flux.close();
    }
    
    
    /**
     * Permet de trouver l'id du premier caractère des données suivantes
     * @param chaine    Chaîne à parcourir
     * @param i         ID de début de parcours
     * @return          ID du premier caractère des données suivantes
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
            URL adresse = new URL(url);
            URLConnection connexion = adresse.openConnection();
            int taille = connexion.getContentLength();

            // On crée un flux d’entrée pour lire le fichier
            InputStream brut = connexion.getInputStream();
            InputStream entree = new BufferedInputStream(brut);

            // On crée un tableau pour enregistrer les octets bruts
            byte[] donnees = new byte[taille];
            
            // Pour l’instant aucun octet n’a encore été lu
            int octetsLus;

            // Octets de déplacement, et octets déjà lus.
            int deplacement = 0;
            float dejaLu = 0;

            // On boucle pour lire tous les octets 1 à 1
            while(deplacement < taille)
            {
                octetsLus = entree.read(donnees, deplacement, donnees.length - deplacement);

                // Petit calcul: mise à jour du nombre total d’octets lus par ajout au nombre d’octets lus au cours des précédents passages au nombre d’octets lus pendant ce passage
                dejaLu = dejaLu + octetsLus;

                // Si on est à la fin du fichier, on sort de la boucle
                if  (octetsLus == -1)
                    break;

                // se cadrer à un endroit précis du fichier pour lire les octets suivants, c’est le déplacement
                deplacement += octetsLus;
            }
            // On ferme le fichier ouvert en ligne
            entree.close();

            // Enregistrement
            FileOutputStream fichierSortie = new FileOutputStream(nomFichier);
            fichierSortie.write(donnees);

            // On ferme les flux de sortie
            fichierSortie.flush();
            fichierSortie.close();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null,
                    "URL de téléchargement mal formée !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println( e.toString() );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Impossible de télécharger le fichier : " + nomFichier
                            + "\n\tErreur d'entée-sortie :\n" + e.toString(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            System.out.println( e.toString() );
        }
    }
    
    
    /**
     * Permet de récupérer le nom du fichier de positions
     * @return Nom du fichier de position à lire
     */
    public String getNomFichier() {
        return nomFichier;
    }
    
    /**
     * Permet de modifier le nom du fichier
     * @param nomFichier Nouveau nom pour le fichier
     */
    public void setNomFichier( String nomFichier ) {
        this.nomFichier = nomFichier;
    }

    
    /**
     * Permet de récupérer l'adresse URL de téléchargement du fichier de positions
     * @return URL de téléchargement des données
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Permet de modifier l'URL de téléchargement du fichier de position
     * @param URL Nouvelle adresse URL pour le fichier
     */
    public void setURL( String URL ) {
        this.url = URL;
    }

    
    /**
     * Permet de récupérer le nom (affiché) de l'animal
     * @return Nom de l'animal
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Permet de changer le nom (affiché) de l'animal
     * @param nom Nouveau nom de l'animal
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }
    
    
    /**
     * Permet de récupérer le tableau dynamique de positions
     * @return Tableau dynamique des positions
     */
    public ArrayList<DonneeArgos> getPositions() {
        return positions;
    }
}
