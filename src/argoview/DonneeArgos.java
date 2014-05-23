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

import java.util.GregorianCalendar;

/**
 * Classe permettant de gérer les données Argos enregistrées pour chaque animal
 * @author 005flyof
 */
public class DonneeArgos {    
    /*
        Déclaration des variables
    */
    private String numBalise = new String();
    private String precision = new String();
    private GregorianCalendar date = new GregorianCalendar();
    private double latitude = 0;
    private double longitude = 0;
    
    
    /**
     * Constructeur de l'objet contenant les données Argos
     * @param numBalise Numéro de la balise concernée par les données
     * @param precision Précision du positionnement
     * @param heure     Heure d'enregistrement de la position
     * @param date      Date d'enregistrement de la position
     * @param latitude  Latitude de la position
     * @param longitude Longitude de la position
     */
    public DonneeArgos ( String numBalise, String precision,
                            String date, String heure,
                            String latitude, String longitude ) {
        this.numBalise = numBalise;
        this.precision = precision;
        
        int annee = 0;
        int mois = 0;
        int jour = 0;
        int heures = 0;
        int minutes = 0;
        
        // On extrait la date et l'heure
        try {
            annee   = Integer.parseInt(date.substring(0, 4));
            mois    = Integer.parseInt(date.substring(5, 7)) - 1;
            jour    = Integer.parseInt(date.substring(8, 10));
            heures  = Integer.parseInt(heure.substring(0, 2));
            minutes = Integer.parseInt(heure.substring(3, 5));
        } catch ( NumberFormatException | IndexOutOfBoundsException e ) {
            System.out.println("Impossible de charger les nombres : " + e.toString());
        }
        
        this.date = new GregorianCalendar(annee, mois, jour, heures, minutes);
        
        // On extrait les latitude et longitude
        try {
            this.latitude  = Double.parseDouble(latitude);
            this.longitude = Double.parseDouble(longitude);
        } catch ( NumberFormatException e ) {
            System.out.println("Impossible de charger les nombres : " + e.toString());
        }
    }


    /**
     * Permet de récupérer le numéro de la balise
     * @return Numéro de la balise
     */
    public String getNumBalise() {
        return numBalise;
    }

    /**
     * Permet de modifier le numéro de la balise
     * @param numBalise Numéro de la balise à modifier
     */
    public void setNumBalise( String numBalise ) {
        this.numBalise = numBalise;
    }

    
    /**
     * Permet de récupérer la précision
     * @return Précision du positionnement
     */
    public String getPrecision() {
        return precision;
    }
    /**
     * Permet de récupérer la précision pour l'afficher de manière compréhensible
     * @return Précision du positionnement pour affichage
     */
    public String getPrecisionAff() {
        // On renvoie des mots compréhensibles (en fonction des infos données sur le site officiel)
        switch (this.precision) {
            case "3":
                return "Très bonne";
            case "2":
                return "Bonne";
            case "1":
                return "Convenable";
            case "0":
                return "Moyenne";
            case "A":
                return "Potable";
            case "B":
                return "Médiocre";
            default:
                return "-";
        }
    }
    
    /**
     * Permet de modifier la précisier
     * @param precision Nouvelle précision
     */
    public void setPrecision( String precision ) {
        this.precision = precision;
    }

    
    /**
     * Permet de récupérer la date de la position
     * @return Date de mesure de position
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Permet de modifier la date de la position
     * @param date Nouvelle date de mesure de position
     */
    public void setDate( GregorianCalendar date ) {
        this.date = date;
    }

    
    /**
     * Permet de récupérer la latitude
     * @return Latitude de la position
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Permet de modifier la latitude
     * @param latitude Nouvelle latitude de la position
     */
    public void setLatitude( double latitude ) {
        this.latitude = latitude;
    }

    
    /**
     * Permet de récupérer la longitude
     * @return Longitude de la position
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Permet de modifier la longitude
     * @param longitude Nouvelle longitude de la position
     */
    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }
}
