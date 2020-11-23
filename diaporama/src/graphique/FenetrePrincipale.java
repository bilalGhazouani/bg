package graphique;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/*-----------------------READ ME ----------------------*/
//Les images doivent être renommées image + chiffre + .jpg 
//Elles doivent etre dans le dossier image
//Si on veut rajouter ou enlever des images :
//Il faut changer la variable nbImages ligne 40 avec le nombre d'images dans le diapo

public class FenetrePrincipale implements ActionListener{

	/*----------------------------------Attibuts----------------------------*/
	private JFrame laBase;

	//Conteneurs
	private JPanel boutons;
	private JPanel image;
	private JPanel diapo;

	//Boutons
	private JButton tabImage[];
	private JButton startPause;
	private JButton stop;
	private JButton accelerer;
	private JButton ralentir;
	private MouseListener doubleClic;

	//Tableau de JLabel pour le diaporama
	private JLabel tabJlab[];

	// Attributs en rapport avec les images
	java.net.URL img;
	private Image imagesPourDiapo[];
	private int nbImages = 6; //nombres d'images dans le diaporama (a Changer en fonction des images)
	private int imageSelec = 100; //10 veut dire aucune immage selectionne

	//Pour le timer
	private Timer timer;
	private int delais;
	private boolean sensDefilement; // true = vers la droite false= vers la gauche
	private ActionListener taskPerformer;

	/*-----------------------------------------CONSTRUCTEUR-----------------------*/

	public FenetrePrincipale()  {
		laBase = new JFrame();
		laBase.setTitle("Diaporama");
		laBase.setSize(700, 700);
		laBase.setLocationRelativeTo(null);
		laBase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		laBase.setLayout(new BorderLayout());

		/*-------Reglage des panels------*/
		boutons = new JPanel(new FlowLayout());
		boutons.setBackground(Color.white);
		image = new JPanel(new GridLayout(0, 2, 5, 5));
		diapo = new JPanel();


		//Activation des boutons (au debut que le start)
		startPause = new JButton("Start");
		stop = new JButton("Stop");
		accelerer = new JButton(">>");
		ralentir = new JButton("<<");

		//Init pour le double clic
		doubleClic = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Object obj = e.getSource();
				if (e.getClickCount() == 2) { // Detection du double clic sur une vignette
					startPause.setText("Pause");
					ralentir.setEnabled(true);
					stop.setEnabled(true);
					accelerer.setEnabled(true);
					for (int i = 0; i< tabImage.length; i++) {
						if (obj == tabImage[i])
							imageSelec = i;
					}
					diapo.removeAll();
					diapo.add(tabJlab[imageSelec]);
					image.setVisible(false);
					laBase.add(diapo, BorderLayout.CENTER);
					diapo.setVisible(true);
					sensDefilement = true;
					timer.start();
				}
			}
		};

		//initialisations images
		imagesPourDiapo = new Image[nbImages];
		tabImage = new JButton[nbImages];
		tabJlab = new JLabel[nbImages]; 
		for (int i=0; i<tabImage.length; i++) {
			//imagesPourDiapo[i] = Toolkit.getDefaultToolkit().createImage("/Users/bilalghazouani/Documents/POO_L3S1/diaporama/image" + i +".jpg"); // A Changer en fonction d'ou sont les images
			tabImage[i] = new JButton(new ImageIcon(getClass().getResource("/image" + i + ".jpg")));
			tabJlab[i] = new JLabel(new ImageIcon(getClass().getResource("/image" + i + ".jpg")));
			image.add(tabImage[i]);
			tabImage[i].addActionListener(this); // activation des boutons
			tabImage[i].addMouseListener(doubleClic);
		}

		//Ajout des element aux panels
		boutons.add(ralentir);
		boutons.add(startPause);
		boutons.add(stop);
		boutons.add(accelerer);

		//Activation des boutons
		ralentir.addActionListener(this);
		startPause.addActionListener(this);
		stop.addActionListener(this);
		accelerer.addActionListener(this);



		//BOutons inactifs au lancement
		ralentir.setEnabled(false);
		stop.setEnabled(false);
		accelerer.setEnabled(false);


		//Placement des boutons
		laBase.add(boutons, BorderLayout.SOUTH);
		laBase.add(image, BorderLayout.CENTER);

		//Programmation du timer
		delais = 3000; //temps en milisecondes
		taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (sensDefilement) { //Sens normal
					diapo.removeAll();
					diapo.setVisible(false);
					imageSelec = (imageSelec + 1) % nbImages;
					diapo.add(tabJlab[imageSelec]);
					diapo.setVisible(true);
				}
				else { //sens inverse
					diapo.removeAll();
					diapo.setVisible(false);
					if (imageSelec == 0)
						imageSelec = imagesPourDiapo.length - 1;
					else
						imageSelec = imageSelec - 1;
					diapo.add(tabJlab[imageSelec]);
					diapo.setVisible(true);
				}
			}
		};
		timer = new Timer (delais, taskPerformer); //création du timer

		laBase.setVisible(true);
	}

	/*------------------------------------- Méthodes ------------------------------*/

	//Parametrage des clics des boutons
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		//Parametre du bouton START/PAUSE
		if(source == startPause){
			if (startPause.getText() == "Start") {
				startPause.setText("Pause");
				ralentir.setEnabled(true);
				stop.setEnabled(true);
				accelerer.setEnabled(true);
				if (imageSelec == 100)
					imageSelec = 0;
				if (image.isVisible()) {
					diapo.removeAll();
					diapo.add(tabJlab[imageSelec]);
					image.setVisible(false);
					laBase.add(diapo, BorderLayout.CENTER);
					diapo.setVisible(true);
					sensDefilement = true;
				}
				timer.start();
			}
			else {
				startPause.setText("Start");
				timer.stop();
				ralentir.setEnabled(false);
				accelerer.setEnabled(false);
			}
		}

		//Parametre du bouton STOP
		else if (source == stop) {
			ralentir.setEnabled(false);
			stop.setEnabled(false);
			accelerer.setEnabled(false);
			diapo.setVisible(false);
			laBase.add(image, BorderLayout.CENTER);
			image.setVisible(true);
			timer.stop();
			startPause.setText("Start");
			//Pour moi a chaque stop le timer repars avec ses parametres de bases
			delais = 3000;
			timer.setDelay(delais);
		}

		//Parametre bouton accelerer
		else if (source == accelerer) {
			if (sensDefilement) {
				if (delais>1000) {
					delais = delais - 1000;
					timer.setDelay(delais);
				}
			}
			else {
				if (delais == 3000)
					sensDefilement = true;
				else {
					delais = delais + 1000;
					timer.setDelay(delais);
				}
			}
		}

		//Parametre bouton ralentir
		else if (source == ralentir) {
			if (sensDefilement) {
				if (delais == 3000)
					sensDefilement = false;
				else {
					delais = delais + 1000;
					timer.setDelay(delais);
				}
			}
			else {
				if (delais > 1000) {
					delais = delais - 1000;
					timer.setDelay(delais);
				}
			}
		}

		//Parametre du clic sur image en mode vignette
		for (int j=0; j<nbImages; j++) {
			if (source == tabImage[j]) {
				if (imageSelec != j) {
					imageSelec=j;
					for (int a=0; a<nbImages; a++) {
						if (a == j)
							tabImage[a].setBorder(BorderFactory.createBevelBorder(0,Color.blue,Color.blue));
						else
							tabImage[a].setBorder(BorderFactory.createBevelBorder(0,Color.white,Color.white));
					}
				}
				else {
					imageSelec=100;
					tabImage[j].setBorder(BorderFactory.createBevelBorder(0,Color.white,Color.white));
				}
			}
		}


	}

	//MAIN
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FenetrePrincipale f = new FenetrePrincipale();

	}
}
