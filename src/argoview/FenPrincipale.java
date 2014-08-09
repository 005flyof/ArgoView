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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;


/**
 * Classe permettant de tout gérer et d'afficher la fenêtre principale
 * @author 005flyof & delta32000
 */
public class FenPrincipale extends javax.swing.JFrame implements ListSelectionListener {
    /*
        Variables
    */
        // Variables contenant les bases des fichiers
    String URL_base;
    String dossier_base;
    
        // Variables contenant les animaux
    private Animal animalSelect;
    private final Vector<Animal> animaux = new Vector<Animal>();
    
        // Variable contenant les boutons d'affichages des animaux
    private final Vector<JButton> boutons = new Vector<JButton>();
    
        // Variable contenant les données à afficher sur le carte et affichées dans le tableau
    private ArrayList<DonneeArgos> positionsAff;
    private String nomAnimal;
    
        // Variables permettant l'affichage
    private ArrayList<DonneeArgos> positions;
    private int j=0;                     // Nnombre d'images
    private boolean points = true;       // Afficher les points sur la carte
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
        
        initParam();
        initBoutons();
        
        initFolder();
        lireDonnees( false );
    }

    
    /**
     * Permet de charger les paramètres depuis le fichier de configuration XML
     */
    private void initParam() {
        // Variables d'erreur
        String errMsg = "";
        boolean err = false;
        
        try {
            // On charge le fichier XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                // Parseur
            DocumentBuilder builder = factory.newDocumentBuilder();
                // Document
            Document document = builder.parse(new File("config.ArgoView.xml"));
            
            // Vérification de la validité de l'élément racine
            final Element racine = document.getDocumentElement();
            if (!racine.getNodeName().equalsIgnoreCase("config")) {
                err = true;
                errMsg = "Racine invalide.";
            }
            else {
                // Extraction des paramètres
                final NodeList racineNoeuds = racine.getChildNodes();
                for (int i = 0; i < racineNoeuds.getLength(); i++) {
                    if (racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        // Paramètres de téléchargement
                        if (racineNoeuds.item(i).getNodeName().equals("fichierPositionsBasique")) {
                            Element download = (Element) racineNoeuds.item(i);
                            Element URL = (Element) download.getElementsByTagName("URL").item(0);
                            Element dossier = (Element) download.getElementsByTagName("dossierEnregistrement").item(0);

                            URL_base = URL.getTextContent();
                            dossier_base = dossier.getTextContent();
                        }

                        // Paramètres d'affichage
                        else if (racineNoeuds.item(i).getNodeName().equals("affichage")) {
                            Element affichage = (Element) racineNoeuds.item(i);
                        }

                        // Liste des animaux
                        else if (racineNoeuds.item(i).getNodeName().equals("listeAnimaux")) {
                            Element listeAnimaux = (Element) racineNoeuds.item(i);
                            NodeList animaux = listeAnimaux.getElementsByTagName("animal");

                            for (int j = 0; j < animaux.getLength(); j++) {
                                Element animal = (Element) animaux.item(j);
                                
                                String nom = animal.getElementsByTagName("nomAnimal").item(0).getTextContent();
                                
                                String fichier = dossier_base
                                        + animal.getElementsByTagName("fichier").item(0).getTextContent() + "."
                                        + animal.getElementsByTagName("fichier").item(0).getAttributes().getNamedItem("ext").getNodeValue();
                                
                                String url = "";
                                if (animal.getElementsByTagName("URLmodif").item(0).getAttributes().getNamedItem("modif").getNodeValue().equals("true"))
                                    url = animal.getElementsByTagName("URLmodif").item(0).getTextContent();
                                else
                                    url = URL_base
                                            + animal.getElementsByTagName("fichier").item(0).getTextContent() + "."
                                            + animal.getElementsByTagName("fichier").item(0).getAttributes().getNamedItem("ext").getNodeValue();
                                
                                this.animaux.add(new Animal(nom, fichier, url));
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            err = true;
            errMsg = "Impossible de charger le fichier de configuration !";
        }
        
        if (err)
            JOptionPane.showMessageDialog(this,
                    "Fichier de configuration invalide pour la raison suivante :\n" + errMsg,
                    "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Permet de générer les boutons
     */
    private void initBoutons() {
        // Création des boutons
        for (int i = 0; i < this.animaux.size(); i++) {
            this.boutons.add(i, new JButton() );
            this.boutons.get(i).setBackground(java.awt.Color.white);
            this.boutons.get(i).setText(this.animaux.get(i).getNom());
            this.boutons.get(i).setMaximumSize(new java.awt.Dimension(190, 23));
            this.boutons.get(i).setMinimumSize(new java.awt.Dimension(190, 23));
            this.boutons.get(i).setPreferredSize(new java.awt.Dimension(190, 23));
            this.boutons.get(i).addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    boutonActionPerformed(evt);
                }
            });
        }
        
        // Affichage des boutons
        JPanel conteneur = new JPanel();
        conteneur.setLayout( new GridLayout(this.animaux.size() / 2, 2, 10, 10));
        
        for (int i = 0; i < this.boutons.size(); i++)
            conteneur.add(this.boutons.get(i));
        conteneur.setPreferredSize( new Dimension(370, 33 * this.boutons.size() / 2) );
        
        JPanel createurMarges = new JPanel();
        createurMarges.setLayout( new FlowLayout(FlowLayout.CENTER, 10, 10) );
        createurMarges.add(conteneur);
        
        JScrollPane scrollChoixAnimal = new JScrollPane(createurMarges);
        
        containChoixAnimal.setLayout( new BoxLayout(containChoixAnimal, 1));
//        containChoixAnimal.setLayout( new FlowLayout(FlowLayout.CENTER, 10, 5) );
        containChoixAnimal.add(scrollChoixAnimal);
    }
    
    private void boutonActionPerformed(java.awt.event.ActionEvent evt) {
        selectAnimalChange(this.animaux.get(this.boutons.indexOf(evt.getSource())));
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
        for (int i = 0; i < this.animaux.size(); i++) {
            try {
                this.animaux.get(i).lireDonnees(affErr);
                this.boutons.get(i).setEnabled(true);
            } catch ( Exception e ) {
                this.boutons.get(i).setEnabled(false);
                fichiersManquants = true;
            }
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
        for (int i = 0; i < this.animaux.size(); i++) {
            this.animaux.get(i).telechargerFichier();
            valueProgress += pasProgress;
            fenProgression.setProgression(valueProgress);
            fenProgression.addText(this.animaux.get(i).getNom());
            fenProgression.addText("   -> URL : " + this.animaux.get(i).getUrl());
            fenProgression.addText("");
        }
        
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

        javax.swing.GroupLayout containChoixAnimalLayout = new javax.swing.GroupLayout(containChoixAnimal);
        containChoixAnimal.setLayout(containChoixAnimalLayout);
        containChoixAnimalLayout.setHorizontalGroup(
            containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );
        containChoixAnimalLayout.setVerticalGroup(
            containChoixAnimalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
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
