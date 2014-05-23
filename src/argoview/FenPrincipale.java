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

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Classe permettant de tout gérer et d'afficher la fenêtre principale
 * @author 005flyof & delta32000
 */
public class FenPrincipale extends javax.swing.JFrame implements ListSelectionListener {
    /*
        Variables
    */
        // Variables contenant les animaux
    private Animal animalSelect;
    private final Animal gaia         = new Animal("Baleine à bosse Gaia", "gaia");
    private final Animal irchad       = new Animal("Baleine à bosse Irchad", "irchad");
    private final Animal teria3       = new Animal("Baleine à bosse Teria3", "teria3");
    private final Animal aquila       = new Animal("Manchot royal Aquila", "aquila");
    private final Animal leloki       = new Animal("Manchot royal Leloki", "leloki");
    private final Animal toms         = new Animal("Manchot royal Toms", "toms");
    private final Animal victor       = new Animal("Manchot royal Victor", "victor");
    private final Animal arcaique     = new Animal("Ours polaire Arcaique", "arcaique");
    private final Animal bandido      = new Animal("Ours polaire Bandido", "bandido");
    private final Animal flocon       = new Animal("Ours polaire Flocon", "flocon");
    private final Animal liriane      = new Animal("Ours polaire Liriane", "liriane");
    private final Animal malys        = new Animal("Ours polaire Malys", "malys");
    private final Animal neige        = new Animal("Ours polaire Neige", "neige");
    private final Animal una          = new Animal("Ours polaire Una", "una");
    private final Animal vanille      = new Animal("Ours polaire Vanille", "vanille");
    private final Animal ecume        = new Animal("Tortue Ecume", "ecume");
    private final Animal aura         = new Animal("Eléphant de mer Aura", "aura");
    private final Animal aurore       = new Animal("Eléphant de mer Autore", "aurore");
    private final Animal nora         = new Animal("Eléphant de mer Nora", "nora");
    private final Animal vella        = new Animal("Eléphant de mer Vella", "vella");
    
        // Variable contenant les données à afficher sur le carte et affichées dans le tableau
    private ArrayList<DonneeArgos> positionsAff;
    private String nomAnimal;
    
        // Variables permettant l'affichage
    private ArrayList<DonneeArgos> positions;
    private int j=0;                     // Nnombre d'images
    private boolean points = true;      // Afficher les points sur la carte
    private boolean trace = false;       // Afficher le tracer sur la carte
    private int mapType = 3;             // Type de carte choisie par l'utilisateur, default=hybrid
    private String map;                  // Type de carte en string (pour l'URL)
    private int nbrPoints = 0;           // Nombre de points à afficher
    private int nbrPointsAff = 1;
    private String nbrPointsAffiche = " ";
    
        // Diverses couleurs utilisées dans le programme
    private final Color couleurValide =     new Color(0, 175, 81);
    
    
    /**
     * Constructeur de la classe FenPrincipale
     */
    public FenPrincipale() {
        initComponents();
        
        this.nomAnimal = "Aucun animal";
        this.positions = new ArrayList();
        this.positionsAff = new ArrayList();
        
        nbrPointsAff = sliderNbrPoints.getValue()+1;
        nbrPointsAffiche = Integer.toString(nbrPointsAff);
        choixNbPtsLabel2.setText(nbrPointsAffiche);
        
        initFolder();
        lireDonnees( false );
    }

    
    /**
     * Permet de charger toutes les positions depuis les fichiers enregistrés
     * Cette fonction propose de télécharger tous les fichiers si l'un d'eux ne peut pas être chargé
     * @param affErr  Notifier des erreurs de lecture ou non (par boîtes de dialogue)
     */
    private void lireDonnees( boolean affErr ) {
        // Variable nécessaire pour savoir s'il faut demander à mettre à jour les fichiers de positions
        boolean fichiersManquants = false;
        
        // Lecture de toutes les positions
        try {
            gaia.lireDonnees(affErr);
            boutonGaia.setEnabled(true);
        } catch ( Exception e ) {
            boutonGaia.setEnabled(false);
            fichiersManquants = true;
        }

        try {
            irchad.lireDonnees(affErr);
            boutonIrchad.setEnabled(true);
        } catch ( Exception e ) {
            boutonIrchad.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            teria3.lireDonnees(affErr);
            boutonTeria3.setEnabled(true);
        } catch ( Exception e ) {
            boutonTeria3.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            aquila.lireDonnees(affErr);
            boutonAquila.setEnabled(true);
        } catch ( Exception e ) {
            boutonAquila.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            leloki.lireDonnees(affErr);
            boutonLeloki.setEnabled(true);
        } catch ( Exception e ) {
            boutonLeloki.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            toms.lireDonnees(affErr);
            boutonToms.setEnabled(true);
        } catch ( Exception e ) {
            boutonToms.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            victor.lireDonnees(affErr);
            boutonVictor.setEnabled(true);
        } catch ( Exception e ) {
            boutonVictor.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            arcaique.lireDonnees(affErr);
            boutonArcaique.setEnabled(true);
        } catch ( Exception e ) {
            boutonArcaique.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            bandido.lireDonnees(affErr);
            boutonBandido.setEnabled(true);
        } catch ( Exception e ) {
            boutonBandido.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            flocon.lireDonnees(affErr);
            boutonFlocon.setEnabled(true);
        } catch ( Exception e ) {
            boutonFlocon.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            liriane.lireDonnees(affErr);
            boutonLiriane.setEnabled(true);
        } catch ( Exception e ) {
            boutonLiriane.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            malys.lireDonnees(affErr);
            boutonMalys.setEnabled(true);
        } catch ( Exception e ) {
            boutonMalys.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            neige.lireDonnees(affErr);
            boutonNeige.setEnabled(true);
        } catch ( Exception e ) {
            boutonNeige.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            una.lireDonnees(affErr);
            boutonUna.setEnabled(true);
        } catch ( Exception e ) {
            boutonUna.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            vanille.lireDonnees(affErr);
            boutonVanille.setEnabled(true);
        } catch ( Exception e ) {
            boutonVanille.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            ecume.lireDonnees(affErr);
            boutonEcume.setEnabled(true);
        } catch ( Exception e ) {
            boutonEcume.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            aura.lireDonnees(affErr);
            boutonAura.setEnabled(true);
        } catch ( Exception e ) {
            boutonAura.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            aurore.lireDonnees(affErr);
            boutonAurore.setEnabled(true);
        } catch ( Exception e ) {
            boutonAurore.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            nora.lireDonnees(affErr);
            boutonNora.setEnabled(true);
        } catch ( Exception e ) {
            boutonNora.setEnabled(false);
            fichiersManquants = true;
        }
        
        try {
            vella.lireDonnees(affErr);
            boutonVella.setEnabled(true);
        } catch ( Exception e ) {
            boutonVella.setEnabled(false);
            fichiersManquants = true;
        }
        
        // S'il y a des fichiers qui n'ont pas étés chargés, on demande si on télécharge les positions
        if ( fichiersManquants ) {
            int rep = JOptionPane.showConfirmDialog(this,
                            "Certains fichiers n'ont pas pu être chargés."
                                + "Voulez-vous mettre à jour tous les fichiers de positions ?",
                            "Téléchargement des données manquantes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( rep == JOptionPane.YES_OPTION )
                majDonneesActionPerformed(null);
        }
    }
    
    /**
     * Permet de télécharger tous les fichiers de positionnement
     * @param fenProgression Fenêtre de progression à modifier
     */
    private void download(ProgressDownload fenProgression) {
        int valueProgress = 0, pasProgress = 5;
        fenProgression.setProgression(valueProgress);
        
        // Téléchargement des fichiers
        gaia.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(gaia.getNom());
        fenProgression.addText("   -> URL : " + gaia.getUrl());
        fenProgression.addText("");
        
        irchad.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(irchad.getNom());
        fenProgression.addText("   -> URL : " + irchad.getUrl());
        fenProgression.addText("");
        
        teria3.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(teria3.getNom());
        fenProgression.addText("   -> URL : " + teria3.getUrl());
        fenProgression.addText("");
        
        aquila.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(aquila.getNom());
        fenProgression.addText("   -> URL : " + aquila.getUrl());
        fenProgression.addText("");
        
        leloki.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(leloki.getNom());
        fenProgression.addText("   -> URL : " + leloki.getUrl());
        fenProgression.addText("");
        
        toms.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(toms.getNom());
        fenProgression.addText("   -> URL : " + toms.getUrl());
        fenProgression.addText("");
        
        victor.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(victor.getNom());
        fenProgression.addText("   -> URL : " + victor.getUrl());
        fenProgression.addText("");
        
        arcaique.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(arcaique.getNom());
        fenProgression.addText("   -> URL : " + arcaique.getUrl());
        fenProgression.addText("");
        
        bandido.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(bandido.getNom());
        fenProgression.addText("   -> URL : " + bandido.getUrl());
        fenProgression.addText("");
        
        flocon.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(flocon.getNom());
        fenProgression.addText("   -> URL : " + flocon.getUrl());
        fenProgression.addText("");
        
        liriane.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(liriane.getNom());
        fenProgression.addText("   -> URL : " + liriane.getUrl());
        fenProgression.addText("");
        
        malys.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(malys.getNom());
        fenProgression.addText("   -> URL : " + malys.getUrl());
        fenProgression.addText("");
        
        neige.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(neige.getNom());
        fenProgression.addText("   -> URL : " + neige.getUrl());
        fenProgression.addText("");
        
        una.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(una.getNom());
        fenProgression.addText("   -> URL : " + una.getUrl());
        fenProgression.addText("");
        
        vanille.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(vanille.getNom());
        fenProgression.addText("   -> URL : " + vanille.getUrl());
        fenProgression.addText("");
        
        ecume.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(ecume.getNom());
        fenProgression.addText("   -> URL : " + ecume.getUrl());
        fenProgression.addText("");
        
        aura.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(aura.getNom());
        fenProgression.addText("   -> URL : " + aura.getUrl());
        fenProgression.addText("");
        
        aurore.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(aurore.getNom());
        fenProgression.addText("   -> URL : " + aurore.getUrl());
        fenProgression.addText("");
        
        nora.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(nora.getNom());
        fenProgression.addText("   -> URL : " + nora.getUrl());
        fenProgression.addText("");
        
        vella.telechargerFichier();
        valueProgress += pasProgress;
        fenProgression.setProgression(valueProgress);
        fenProgression.addText(vella.getNom());
        fenProgression.addText("   -> URL : " + vella.getUrl());
        fenProgression.addText("");

        JOptionPane.showMessageDialog(fenProgression,
                "Mise à jour des fichiers de positionnement terminé !",
                "Mise à jour", JOptionPane.INFORMATION_MESSAGE);

        majDonnees.setBackground(couleurValide);
        majDonnees.setText("Données à jour !");
        majDonnees.setEnabled(true);

        // Relecture des données
        lireDonnees(true);
    }

    /**
     * Permet de recharger l'affichage du tableau positions
     */
    private void afficherDonnees() {
        // On initialise l'écouteur de sélection
        ptsTable.getSelectionModel().addListSelectionListener( this );
        
        // Puis on détermine le tableau
            // En tête du tableau
        String[] enTetes = new String [] {
                "Nom de l'animal", "Balise n°", "Précision", "Date", "Heure", "Latitude", "Longitude"
        };
        Object[][] contenu = new Object[positionsAff.size()][enTetes.length];
        
            // Affichage des positions une à une
        for (int i = 0; i < positionsAff.size(); i++) {
            contenu[i][0] = nomAnimal;
            contenu[i][1] = positionsAff.get(i).getNumBalise();
            contenu[i][2] = positionsAff.get(i).getPrecisionAff();
            String date = (positionsAff.get(i).getDate().get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "")
                    + positionsAff.get(i).getDate().get(Calendar.DAY_OF_MONTH) + " ";
            switch ( positionsAff.get(i).getDate().get(Calendar.MONTH) ) {
                case Calendar.JANUARY:
                    date += "janvier";
                    break;
                case Calendar.FEBRUARY:
                    date += "février";
                    break;
                case Calendar.MARCH:
                    date += "mars";
                    break;
                case Calendar.APRIL:
                    date += "avril";
                    break;
                case Calendar.MAY:
                    date += "mai";
                    break;
                case Calendar.JUNE:
                    date += "juin";
                    break;
                case Calendar.JULY:
                    date += "juillet";
                    break;
                case Calendar.AUGUST:
                    date += "aoüt";
                    break;
                case Calendar.SEPTEMBER:
                    date += "septembre";
                    break;
                case Calendar.OCTOBER:
                    date += "octobre";
                    break;
                case Calendar.NOVEMBER:
                    date += "novembre";
                    break;
                case Calendar.DECEMBER:
                    date += "décembre";
                    break;
                default:
                    date += "mois";
                    break;
            }
            date += " ";
            date += positionsAff.get(i).getDate().get(Calendar.YEAR);
            contenu[i][3] = date;
            contenu[i][4] = (positionsAff.get(i).getDate().get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "")
                    + positionsAff.get(i).getDate().get(Calendar.HOUR_OF_DAY)
                    + ":"
                    + (positionsAff.get(i).getDate().get(Calendar.MINUTE) < 10 ? "0" : "")
                    + positionsAff.get(i).getDate().get(Calendar.MINUTE);
            contenu[i][5] = positionsAff.get(i).getLatitude();
            contenu[i][6] = positionsAff.get(i).getLongitude();
        }
        
            // Affichage du modèle
        DefaultTableModel modele = new DefaultTableModel(contenu, enTetes) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        ptsTable.setModel(modele);
        
            // On centre pour plus de design
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        for( int i=0; i < ptsTable.getColumnModel().getColumnCount(); i++ )
            ptsTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        
            // On ajuste la taille des colonnes automatiquement
        ptsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ptsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        ptsTable.getColumnModel().getColumn(3).setPreferredWidth(125);
    }
    
    /**
     * Permet de savoir ce qui a été sélectionné dans le tableau d'affichage des données de positionnement
     * @param event Evénement sucitant l'appel de cette fonction
     */
    @Override
    public void valueChanged( ListSelectionEvent event )
    {
        // On vérifie que la sélection a bien été effectuée sur le tableau
        if ( event.getSource() == ptsTable.getSelectionModel() && event.getFirstIndex() >= 0 )
        {
            // On détermine la première ligne sélectionnée
            int[] ligneSelect = ptsTable.getSelectedRows();
            
            // Si trop de points sont sélectionnés, erreur
            if (ligneSelect.length > 30) {
                JOptionPane.showMessageDialog(this,
                        "Trop de points sélectionnés !\n(30 points max.)",
                        "Sélection", JOptionPane.WARNING_MESSAGE);
            }
            // On enregistre dans positions les valeurs sélectionnées s'il y en a
            //      sinon, on enregistre le tableau entier
            else if ( ligneSelect.length == 0 )
                positions.equals(animalSelect.getPositions().clone());
            else {
                positions = new ArrayList();
                for (int i = 0; i < ligneSelect.length; i++) {
                    positions.add(positionsAff.get(i));
                }
            }
        }
    }
    
    
    /**
     * Permet de changer l'animal sélectionné
     * @param nouvelAnimal Nouvel animal à enregistrer et afficher
     */
    private void selectAnimalChange( Animal nouvelAnimal ) {
        animalSelect = nouvelAnimal;
        positions = animalSelect.getPositions();
        positionsAff = animalSelect.getPositions();
        nomAnimal = animalSelect.getNom();
        afficherDonnees();
    }
    
    
    /**
     * Permet de créer les dossiers :
     *      - celui qui contiendra les images et qui est temporaire
     *      - celui qui contient les fichiers de positions (s'il n'existe pas déjà)
     */
    private void initFolder() {
        // On crée le dossier temporaire contenant les images
        String dirName = "img";
        File dir = new File(dirName);
        if ( !dir.mkdirs() )
            JOptionPane.showMessageDialog(this,
                    "Impossible de créer le dossier temporaire contenant les images des cartes...",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        
        // On crée le dossier contenant les positions s'il n'existe pas
        dirName = "Positions";
        File pos = new File(dirName);
        if ( !pos.exists() ) {
            if ( !pos.mkdirs() )
                JOptionPane.showMessageDialog(this,
                        "Impossible de créer le dossier temporaire contenant les images des cartes...",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * Permet de vider un dossier récursivement
     * @param aSupprimer    Fichier/dossier à vider puis supprimer
     * @param affErr        Affiche les messages d'erreur (true)
     */
    public static void delRecursif( File aSupprimer, boolean affErr ) {
        // Si le fichier n'existe pas
        if ( !aSupprimer.exists() && affErr ) {
            JOptionPane.showMessageDialog(null,
                    "Impossible de supprimer le fichier : " + aSupprimer.getPath()
                            + "\nFichier inexistant",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Si l'adresse fait référence à un dossier, on le vide avant de le supprimer
        if (aSupprimer.isDirectory()) {
            File[] enfants = aSupprimer.listFiles();
            for (int i=0; enfants != null && i < enfants.length; i++)
                delRecursif( enfants[i], affErr );
        }
        
        // On tente de supprimer
        if ( !aSupprimer.delete() && affErr )
            JOptionPane.showMessageDialog(null,
                    "Impossible de supprimer le fichier : " + aSupprimer.getPath(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * Permet de calculer la moyenne des latitudes
     */
    private double calcMLat () {
       double add = 0;
       for (int i = 0; i < nbrPoints; i++) {
           add = add + positions.get(i).getLatitude();
       }
       double result = add / nbrPoints;
       return (result);
    }
    
    /**
     * Permet de calculer la moyenne des longitudes
     */
    private double calcMLon () {
       double add = 0;
       for (int i = 0; i < nbrPoints; i++) {
           add = add + positions.get(i).getLongitude();
       }
       double result = add / nbrPoints;
       return (result);
    }

    /**
     * Permet de définir le type de carte à afficher
     */
    private String map () {
        if (mapType == 0)
            map ="roadmap";
        else if (mapType == 1)
            map = "terrain";
        else if (mapType == 2)
            map = "satellite";
        else if (mapType == 3)
            map = "hybrid";
        
        return (map);
    }
    
    /**
     * Permet d'afficher la carte en fonction de l'animal sélectionné
     */
    private void afficherCarte() {
        //nombre d'image à enregister
        j++; 
        //définition des variables
        String url;
        double latitude,longitude;
        // On limite le nombre de positions pour réduir le lien URL
        if (positions.size() > 30){ nbrPoints = 30;}
        else if (positions.size() <= 30){ nbrPoints = positions.size();}
        //on définit la moyenne des coordonnées
        latitude = calcMLat();
        longitude = calcMLon();
        //on définit le type de carte à afficher
        mapType = listTypeCarte.getSelectedIndex();
        map = map();
        //puis on définit l'url avec tous les parametres
            url = "http://maps.googleapis.com/maps/api/staticmap?center="
                    +Double.toString(latitude)
                    +","+Double.toString(longitude)
                    +"&zoom="+sliderZoom.getValue()
                    +"&size=512x512&maptype=";
            url = url+map;
        
        /**
         * Fonction qui ajoute des markers dans l'url
         */
        if (points == true){
            for(int i = 0; (i < nbrPoints*nbrPointsAff)&&(i<positions.size()); i=i+nbrPointsAff){
                if (i/nbrPointsAff < 10){
                    url = url+"&markers=color:0xff0000|label:"+(i/nbrPointsAff)+"|"+positions.get(i).getLatitude()
                    +","+positions.get(i).getLongitude();
                    System.out.println(i);
                }
                if (i/nbrPointsAff >= 10 && i/nbrPointsAff < 20){
                    url = url+"&markers=color:0xFF0060|label:"+((i/nbrPointsAff)-10)+"|"+positions.get(i).getLatitude()
                    +","+positions.get(i).getLongitude();
                    System.out.println(i);
                }
                if (i/nbrPointsAff >= 20 && i/nbrPointsAff < 30){
                    url = url+"&markers=color:0xFF00AA|label:"+((i/nbrPointsAff)-20)+"|"+positions.get(i).getLatitude()
                    +","+positions.get(i).getLongitude();
                    System.out.println(i);
                }
            }
					
                    System.out.println(url);
        
        }
        /**
         * fonction qui ajoute une trace entre chaque markers
         */
        if (trace == true) {
            url = url+"&path=color:red";
            for (int i = 0; (i < nbrPoints*nbrPointsAff)&&(i<positions.size()); i=i+nbrPointsAff){
                url = url+"|"+positions.get(i).getLatitude()
                         +","+positions.get(i).getLongitude();
            }
        }
        //on ferme l'url
        url = url+"&sensor=false";
        //on envoit url et le nombre d'image à 'carte' avant de l'afficher
        Carte fen = new Carte(url,j,nomAnimal);
        fen.setVisible(true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containChoixAnimal = new javax.swing.JPanel();
        boutonGaia = new javax.swing.JButton();
        boutonIrchad = new javax.swing.JButton();
        boutonTeria3 = new javax.swing.JButton();
        boutonAquila = new javax.swing.JButton();
        boutonLeloki = new javax.swing.JButton();
        boutonToms = new javax.swing.JButton();
        boutonVictor = new javax.swing.JButton();
        boutonBandido = new javax.swing.JButton();
        boutonArcaique = new javax.swing.JButton();
        boutonFlocon = new javax.swing.JButton();
        boutonMalys = new javax.swing.JButton();
        boutonEcume = new javax.swing.JButton();
        boutonVanille = new javax.swing.JButton();
        boutonLiriane = new javax.swing.JButton();
        boutonUna = new javax.swing.JButton();
        boutonNeige = new javax.swing.JButton();
        boutonNora = new javax.swing.JButton();
        boutonAura = new javax.swing.JButton();
        boutonAurore = new javax.swing.JButton();
        boutonVella = new javax.swing.JButton();
        containAnimal = new javax.swing.JPanel();
        ptsScroll = new javax.swing.JScrollPane();
        ptsTable = new javax.swing.JTable();
        containActs = new javax.swing.JPanel();
        majDonnees = new javax.swing.JButton();
        affDonnees = new javax.swing.JButton();
        containAff = new javax.swing.JPanel();
        affTrace = new javax.swing.JCheckBox();
        affPts = new javax.swing.JCheckBox();
        sliderZoom = new javax.swing.JSlider();
        zoomLabelInfo = new javax.swing.JLabel();
        zoomLabelMin = new javax.swing.JLabel();
        zoomLabelMax = new javax.swing.JLabel();
        scrollTypeCarte = new javax.swing.JScrollPane();
        listTypeCarte = new javax.swing.JList();
        sliderNbrPoints = new javax.swing.JSlider();
        choixNbPtsLabel1 = new javax.swing.JLabel();
        choixNbPtsLabel2 = new javax.swing.JLabel();
        choixNbPtsLabel3 = new javax.swing.JLabel();
        sepZoomChoixNbPts = new javax.swing.JSeparator();
        sepInfosAffTypeCarte = new javax.swing.JSeparator();
        sepTypeCarteZoom = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ArgoView");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        containChoixAnimal.setBorder(javax.swing.BorderFactory.createTitledBorder("Choix de l'animal"));
        containChoixAnimal.setPreferredSize(new java.awt.Dimension(200, 0));

        boutonGaia.setBackground(java.awt.Color.white);
        boutonGaia.setText("Baleine à bosse Gaïa");
        boutonGaia.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonGaia.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonGaia.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonGaia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonGaiaActionPerformed(evt);
            }
        });

        boutonIrchad.setBackground(java.awt.Color.white);
        boutonIrchad.setText("Baleine à bosse Irchad");
        boutonIrchad.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonIrchad.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonIrchad.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonIrchad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonIrchadActionPerformed(evt);
            }
        });

        boutonTeria3.setBackground(java.awt.Color.white);
        boutonTeria3.setText("Baleine à bosse Teria3");
        boutonTeria3.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonTeria3.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonTeria3.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonTeria3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonTeria3ActionPerformed(evt);
            }
        });

        boutonAquila.setBackground(java.awt.Color.white);
        boutonAquila.setText("Manchot royal Aquila");
        boutonAquila.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonAquila.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonAquila.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonAquila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonAquilaActionPerformed(evt);
            }
        });

        boutonLeloki.setBackground(java.awt.Color.white);
        boutonLeloki.setText("Manchot royal Leloki");
        boutonLeloki.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonLeloki.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonLeloki.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonLeloki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonLelokiActionPerformed(evt);
            }
        });

        boutonToms.setBackground(java.awt.Color.white);
        boutonToms.setText("Manchot royal Toms");
        boutonToms.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonToms.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonToms.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonToms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonTomsActionPerformed(evt);
            }
        });

        boutonVictor.setBackground(java.awt.Color.white);
        boutonVictor.setText("Manchot royal Victor");
        boutonVictor.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonVictor.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonVictor.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonVictor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonVictorActionPerformed(evt);
            }
        });

        boutonBandido.setBackground(java.awt.Color.white);
        boutonBandido.setText("Ours polaire Bandido");
        boutonBandido.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonBandido.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonBandido.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonBandido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonBandidoActionPerformed(evt);
            }
        });

        boutonArcaique.setBackground(java.awt.Color.white);
        boutonArcaique.setText("Ours polaire Arcaique");
        boutonArcaique.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonArcaique.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonArcaique.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonArcaique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonArcaiqueActionPerformed(evt);
            }
        });

        boutonFlocon.setBackground(java.awt.Color.white);
        boutonFlocon.setText("Ours polaire Flocon");
        boutonFlocon.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonFlocon.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonFlocon.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonFlocon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonFloconActionPerformed(evt);
            }
        });

        boutonMalys.setBackground(java.awt.Color.white);
        boutonMalys.setText("Ours polaire Malys");
        boutonMalys.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonMalys.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonMalys.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonMalys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonMalysActionPerformed(evt);
            }
        });

        boutonEcume.setBackground(java.awt.Color.white);
        boutonEcume.setText("Tortue Ecume");
        boutonEcume.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonEcume.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonEcume.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonEcume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonEcumeActionPerformed(evt);
            }
        });

        boutonVanille.setBackground(java.awt.Color.white);
        boutonVanille.setText("Ours polaire Vanille");
        boutonVanille.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonVanille.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonVanille.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonVanille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonVanilleActionPerformed(evt);
            }
        });

        boutonLiriane.setBackground(java.awt.Color.white);
        boutonLiriane.setText("Ours polaire Liriane");
        boutonLiriane.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonLiriane.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonLiriane.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonLiriane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonLirianeActionPerformed(evt);
            }
        });

        boutonUna.setBackground(java.awt.Color.white);
        boutonUna.setText("Ours polaire Una");
        boutonUna.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonUna.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonUna.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonUna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonUnaActionPerformed(evt);
            }
        });

        boutonNeige.setBackground(java.awt.Color.white);
        boutonNeige.setText("Ours polaire Neige");
        boutonNeige.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonNeige.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonNeige.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonNeige.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonNeigeActionPerformed(evt);
            }
        });

        boutonNora.setBackground(java.awt.Color.white);
        boutonNora.setText("Eléphant de mer Nora");
        boutonNora.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonNora.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonNora.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonNora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonNoraActionPerformed(evt);
            }
        });

        boutonAura.setBackground(java.awt.Color.white);
        boutonAura.setText("Eléphant de mer Aura");
        boutonAura.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonAura.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonAura.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonAura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonAuraActionPerformed(evt);
            }
        });

        boutonAurore.setBackground(java.awt.Color.white);
        boutonAurore.setText("Eléphant de mer Aurore");
        boutonAurore.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonAurore.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonAurore.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonAurore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonAuroreActionPerformed(evt);
            }
        });

        boutonVella.setBackground(java.awt.Color.white);
        boutonVella.setText("Eléphant de mer Vella");
        boutonVella.setMaximumSize(new java.awt.Dimension(200, 23));
        boutonVella.setMinimumSize(new java.awt.Dimension(200, 23));
        boutonVella.setPreferredSize(new java.awt.Dimension(200, 23));
        boutonVella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonVellaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containChoixAnimalLayout = new javax.swing.GroupLayout(containChoixAnimal);
        containChoixAnimal.setLayout(containChoixAnimalLayout);
        containChoixAnimalLayout.setHorizontalGroup(
            containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containChoixAnimalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonGaia, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonTeria3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonAquila, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonLeloki, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonToms, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonVictor, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonArcaique, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonBandido, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonIrchad, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonFlocon, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonLiriane, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonNeige, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonUna, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonVanille, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonEcume, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonAura, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonAurore, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonNora, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonMalys, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonVella, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containChoixAnimalLayout.setVerticalGroup(
            containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containChoixAnimalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containChoixAnimalLayout.createSequentialGroup()
                        .addComponent(boutonLiriane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonMalys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonNeige, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonUna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonVanille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonEcume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonAura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonAurore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonNora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containChoixAnimalLayout.createSequentialGroup()
                        .addComponent(boutonGaia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonIrchad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonTeria3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonAquila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonLeloki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonToms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonVictor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonArcaique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boutonBandido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boutonFlocon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boutonVella, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        containAnimal.setBorder(javax.swing.BorderFactory.createTitledBorder("Données de positionnement relatives à l'animal"));
        containAnimal.setPreferredSize(new java.awt.Dimension(200, 0));

        ptsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom de l'animal", "Balise n°", "Précision", "Date", "Heure", "Latitude", "Longitude"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ptsTable.setFocusable(false);
        ptsScroll.setViewportView(ptsTable);

        javax.swing.GroupLayout containAnimalLayout = new javax.swing.GroupLayout(containAnimal);
        containAnimal.setLayout(containAnimalLayout);
        containAnimalLayout.setHorizontalGroup(
            containAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ptsScroll)
        );
        containAnimalLayout.setVerticalGroup(
            containAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ptsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );

        containActs.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions sur les données"));
        containActs.setPreferredSize(new java.awt.Dimension(200, 0));

        majDonnees.setBackground(java.awt.Color.white);
        majDonnees.setText("Mettre à jour");
        majDonnees.setMaximumSize(new java.awt.Dimension(200, 23));
        majDonnees.setMinimumSize(new java.awt.Dimension(200, 23));
        majDonnees.setPreferredSize(new java.awt.Dimension(200, 23));
        majDonnees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                majDonneesActionPerformed(evt);
            }
        });

        affDonnees.setBackground(java.awt.Color.white);
        affDonnees.setText("Afficher");
        affDonnees.setMaximumSize(new java.awt.Dimension(200, 23));
        affDonnees.setMinimumSize(new java.awt.Dimension(200, 23));
        affDonnees.setPreferredSize(new java.awt.Dimension(200, 23));
        affDonnees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affDonneesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout containActsLayout = new javax.swing.GroupLayout(containActs);
        containActs.setLayout(containActsLayout);
        containActsLayout.setHorizontalGroup(
            containActsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containActsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(majDonnees, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(affDonnees, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        containActsLayout.setVerticalGroup(
            containActsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containActsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containActsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(majDonnees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(affDonnees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        containAff.setBorder(javax.swing.BorderFactory.createTitledBorder("Options d'affichage des données"));
        containAff.setPreferredSize(new java.awt.Dimension(200, 0));

        affTrace.setText("Afficher le tracé");
        affTrace.setBorderPaintedFlat(true);
        affTrace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affTraceActionPerformed(evt);
            }
        });

        affPts.setSelected(true);
        affPts.setText("Afficher les points");
        affPts.setBorderPaintedFlat(true);
        affPts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affPtsActionPerformed(evt);
            }
        });

        sliderZoom.setMaximum(11);
        sliderZoom.setMinimum(1);
        sliderZoom.setValue(6);

        zoomLabelInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zoomLabelInfo.setText("Zoom");

        zoomLabelMin.setText("0");

        zoomLabelMax.setText("11");

        listTypeCarte.setBackground(new java.awt.Color(240, 240, 240));
        listTypeCarte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        listTypeCarte.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Carte", "Terrain", "Satellite", "Satellite et routes" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listTypeCarte.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTypeCarte.setViewportView(listTypeCarte);

        sliderNbrPoints.setMaximum(3);
        sliderNbrPoints.setValue(0);
        sliderNbrPoints.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderNbrPointsStateChanged(evt);
            }
        });

        choixNbPtsLabel1.setText("Afficher tous les");

        choixNbPtsLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        choixNbPtsLabel3.setText("points");

        jLabel1.setText("(30 points maximum affichés)");

        javax.swing.GroupLayout containAffLayout = new javax.swing.GroupLayout(containAff);
        containAff.setLayout(containAffLayout);
        containAffLayout.setHorizontalGroup(
            containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sepInfosAffTypeCarte)
            .addComponent(sepTypeCarteZoom)
            .addComponent(sepZoomChoixNbPts)
            .addGroup(containAffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containAffLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(containAffLayout.createSequentialGroup()
                        .addGroup(containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollTypeCarte, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(affPts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(affTrace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sliderNbrPoints, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addGroup(containAffLayout.createSequentialGroup()
                                .addComponent(zoomLabelMin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(zoomLabelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(zoomLabelMax))
                            .addComponent(sliderZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(containAffLayout.createSequentialGroup()
                                .addComponent(choixNbPtsLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choixNbPtsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choixNbPtsLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        containAffLayout.setVerticalGroup(
            containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containAffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(affPts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(affTrace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sepInfosAffTypeCarte, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTypeCarte, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sepTypeCarteZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zoomLabelMin)
                    .addComponent(zoomLabelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zoomLabelMax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sliderZoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sepZoomChoixNbPts, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containAffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choixNbPtsLabel1)
                    .addComponent(choixNbPtsLabel3)
                    .addComponent(choixNbPtsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderNbrPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(containAnimal, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(containActs, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(containChoixAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(containAff, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(containChoixAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(containActs, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(containAff, javax.swing.GroupLayout.PREFERRED_SIZE, 408, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(containAnimal, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addContainerGap())
        );

        containAff.getAccessibleContext().setAccessibleName("");
        containAff.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boutonGaiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonGaiaActionPerformed
        selectAnimalChange(gaia);
    }//GEN-LAST:event_boutonGaiaActionPerformed

    private void boutonIrchadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonIrchadActionPerformed
        selectAnimalChange(irchad);
    }//GEN-LAST:event_boutonIrchadActionPerformed

    private void boutonTeria3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonTeria3ActionPerformed
        selectAnimalChange(teria3);
    }//GEN-LAST:event_boutonTeria3ActionPerformed

    private void boutonAquilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonAquilaActionPerformed
        selectAnimalChange(aquila);
    }//GEN-LAST:event_boutonAquilaActionPerformed

    private void boutonLelokiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonLelokiActionPerformed
        selectAnimalChange(leloki);
    }//GEN-LAST:event_boutonLelokiActionPerformed

    private void boutonTomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonTomsActionPerformed
        selectAnimalChange(toms);
    }//GEN-LAST:event_boutonTomsActionPerformed

    private void boutonVictorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonVictorActionPerformed
        selectAnimalChange(victor);
    }//GEN-LAST:event_boutonVictorActionPerformed

    private void boutonBandidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonBandidoActionPerformed
        selectAnimalChange(bandido);
    }//GEN-LAST:event_boutonBandidoActionPerformed

    private void boutonArcaiqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonArcaiqueActionPerformed
        selectAnimalChange(arcaique);
    }//GEN-LAST:event_boutonArcaiqueActionPerformed

    private void majDonneesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_majDonneesActionPerformed
        majDonnees.setEnabled(false);
        
        // On crée la fenêtre d'informations sur le téléchargement
        ProgressDownload fenProgression = new ProgressDownload(this, false);
        fenProgression.setVisible(true);
        
        download(fenProgression);
    }//GEN-LAST:event_majDonneesActionPerformed
    
    private void affDonneesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affDonneesActionPerformed
        afficherCarte();
    }//GEN-LAST:event_affDonneesActionPerformed
    
    private void boutonFloconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonFloconActionPerformed
        selectAnimalChange(flocon);
    }//GEN-LAST:event_boutonFloconActionPerformed

    private void boutonMalysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonMalysActionPerformed
        selectAnimalChange(malys);
    }//GEN-LAST:event_boutonMalysActionPerformed

    private void boutonEcumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonEcumeActionPerformed
        selectAnimalChange(ecume);
    }//GEN-LAST:event_boutonEcumeActionPerformed

    private void boutonVanilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonVanilleActionPerformed
        selectAnimalChange(vanille);
    }//GEN-LAST:event_boutonVanilleActionPerformed

    private void boutonLirianeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonLirianeActionPerformed
        selectAnimalChange(liriane);
    }//GEN-LAST:event_boutonLirianeActionPerformed

    private void boutonUnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonUnaActionPerformed
        selectAnimalChange(una);
    }//GEN-LAST:event_boutonUnaActionPerformed

    private void boutonNeigeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonNeigeActionPerformed
        selectAnimalChange(neige);
    }//GEN-LAST:event_boutonNeigeActionPerformed

    private void boutonNoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonNoraActionPerformed
        selectAnimalChange(nora);
    }//GEN-LAST:event_boutonNoraActionPerformed

    private void boutonAuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonAuraActionPerformed
        selectAnimalChange(aura);
    }//GEN-LAST:event_boutonAuraActionPerformed

    private void boutonAuroreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonAuroreActionPerformed
        selectAnimalChange(aurore);
    }//GEN-LAST:event_boutonAuroreActionPerformed

    private void boutonVellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonVellaActionPerformed
        selectAnimalChange(vella);
    }//GEN-LAST:event_boutonVellaActionPerformed

    private void affPtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affPtsActionPerformed
        if(points == false){
            points = true;
        }
        else if (points == true){
            points = false;
        }
    }//GEN-LAST:event_affPtsActionPerformed

    private void affTraceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affTraceActionPerformed
        if (trace == false){
            trace = true;
        }
        else if (trace == true){
            trace = false;
        }
    }//GEN-LAST:event_affTraceActionPerformed

    private void sliderNbrPointsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderNbrPointsStateChanged
        if (sliderNbrPoints.getValue() == 0){
            nbrPointsAff = sliderNbrPoints.getValue()+1;
        }                                             
        if (sliderNbrPoints.getValue() == 1){
            nbrPointsAff = sliderNbrPoints.getValue()+1;
        }                                             
        if (sliderNbrPoints.getValue() == 2){
            nbrPointsAff = sliderNbrPoints.getValue()+3;
        }                                             
        if (sliderNbrPoints.getValue() == 3){
            nbrPointsAff = sliderNbrPoints.getValue()+7;
        }
        
        nbrPointsAffiche = Integer.toString(nbrPointsAff);
        choixNbPtsLabel2.setText(nbrPointsAffiche);
    }//GEN-LAST:event_sliderNbrPointsStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // On vide le dossier img
        File del = new File("img");
        delRecursif(del, true);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton affDonnees;
    private javax.swing.JCheckBox affPts;
    private javax.swing.JCheckBox affTrace;
    private javax.swing.JButton boutonAquila;
    private javax.swing.JButton boutonArcaique;
    private javax.swing.JButton boutonAura;
    private javax.swing.JButton boutonAurore;
    private javax.swing.JButton boutonBandido;
    private javax.swing.JButton boutonEcume;
    private javax.swing.JButton boutonFlocon;
    private javax.swing.JButton boutonGaia;
    private javax.swing.JButton boutonIrchad;
    private javax.swing.JButton boutonLeloki;
    private javax.swing.JButton boutonLiriane;
    private javax.swing.JButton boutonMalys;
    private javax.swing.JButton boutonNeige;
    private javax.swing.JButton boutonNora;
    private javax.swing.JButton boutonTeria3;
    private javax.swing.JButton boutonToms;
    private javax.swing.JButton boutonUna;
    private javax.swing.JButton boutonVanille;
    private javax.swing.JButton boutonVella;
    private javax.swing.JButton boutonVictor;
    private javax.swing.JLabel choixNbPtsLabel1;
    private javax.swing.JLabel choixNbPtsLabel2;
    private javax.swing.JLabel choixNbPtsLabel3;
    private javax.swing.JPanel containActs;
    private javax.swing.JPanel containAff;
    private javax.swing.JPanel containAnimal;
    private javax.swing.JPanel containChoixAnimal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList listTypeCarte;
    private javax.swing.JButton majDonnees;
    private javax.swing.JScrollPane ptsScroll;
    private javax.swing.JTable ptsTable;
    private javax.swing.JScrollPane scrollTypeCarte;
    private javax.swing.JSeparator sepInfosAffTypeCarte;
    private javax.swing.JSeparator sepTypeCarteZoom;
    private javax.swing.JSeparator sepZoomChoixNbPts;
    private javax.swing.JSlider sliderNbrPoints;
    private javax.swing.JSlider sliderZoom;
    private javax.swing.JLabel zoomLabelInfo;
    private javax.swing.JLabel zoomLabelMax;
    private javax.swing.JLabel zoomLabelMin;
    // End of variables declaration//GEN-END:variables
}
