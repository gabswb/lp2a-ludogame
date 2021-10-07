package ludogame;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	
	DiePanel diePanel;
	JPanel buttonPanel, endButtonPanel;
	JButton dieButton, endButton;
	JLabel turn, step, die;
	
	MyFrame() {
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Ludo Game");
		this.setLayout(null);
		this.setSize(Main.FRAME_SIZE,Main.FRAME_SIZE);
		this.setVisible(false);
		
		diePanel = new DiePanel();
		this.add(diePanel);
		
		//affichage du joueur actuel
		turn = new JLabel();
		turn.setText("Blue turn");
		turn.setBounds(50, 800, 100, 20);
		this.add(turn);
		
		//affiche l'étape (jeu ou tour initial
		step = new JLabel();
		step.setText("INITIAL TURN");
		step.setBounds(50, 750, 100, 20);
		this.add(step);
		
		//affichage de l'historique
		die = new JLabel();
		die.setText("");
		die.setBounds(200, 760, 200, 190);
		die.setBackground(Color.GRAY);
		die.setOpaque(true);
		die.setLayout(null);
		die.setHorizontalAlignment(JLabel.CENTER);
		die.setVerticalAlignment(JLabel.TOP);
		this.add(die);
 

		buttonPanel = new JPanel();
		
		
		dieButton = new JButton("Tirer un dé");
		dieButton.setBounds(0, 0, 100,100);
		buttonPanel.add(dieButton);
		this.add(buttonPanel);
		dieButton.addActionListener(e-> diePanel.rollDie());
		dieButton.setEnabled(true);
		
		
		buttonPanel.setBounds(800, 450, 100, 100);
		diePanel.setBounds(750, 200, 200, 200);		
	}
	
	/*
	 * Affichage du choix de mode de jeu
	 */
	public void menu() {
		
		JFrame menu = new JFrame();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(false);
		menu.setTitle("Ludo Game");
		menu.setLayout(null);
		menu.setSize(Main.FRAME_SIZE,Main.FRAME_SIZE);
		menu.setVisible(true);
		menu.getContentPane().setBackground(Color.DARK_GRAY);
		
		JPanel botPanel = new JPanel();
		menu.add(botPanel);
		botPanel.setBounds(100,375,250,250);
		botPanel.setBackground(Color.PINK);
		botPanel.setLayout(null);
		
		JPanel playerPanel = new JPanel();
		menu.add(playerPanel);
		playerPanel.setBounds(650,375,250,250);
		playerPanel.setBackground(Color.PINK);
		playerPanel.setLayout(null);
		
		JLabel botLabel = new JLabel();
		botPanel.add(botLabel);
		botLabel.setText("Play in solo against 3 terrifying bots");
		botLabel.setBounds(25, 50, 250, 100);
		
		JLabel playerLabel = new JLabel();
		playerPanel.add(playerLabel);
		playerLabel.setText("Play against 3 friends");
		playerLabel.setBounds(65, 50, 250, 100);
		
		JButton botButton = new JButton("Bot Mod");
		botButton.setBounds(75, 10, 100, 50);
		botPanel.add(botButton);
		botButton.addActionListener(e -> botChoice());
		botButton.addActionListener(e -> menu.dispose());
		
		JButton playerButton = new JButton("Player Mod");
		playerButton.setBounds(75, 10, 100, 50);
		playerPanel.add(playerButton);
		playerButton.addActionListener(e -> playerChoice());
		playerButton.addActionListener(e -> menu.dispose());
		
		JPanel redPanel = new JPanel();
		JPanel bluePanel = new JPanel();
		JPanel yellowPanel = new JPanel();
		JPanel greenPanel = new JPanel();
		
		redPanel.setBackground(Color.RED);
		bluePanel.setBackground(Color.BLUE);
		yellowPanel.setBackground(Color.YELLOW);
		greenPanel.setBackground(Color.GREEN);
		
		menu.add(redPanel);
		menu.add(bluePanel);
		menu.add(yellowPanel);
		menu.add(greenPanel);
		
		redPanel.setBounds(500,0,500,500);
		bluePanel.setBounds(500,500,500,500);
		yellowPanel.setBounds(0,500,500,500);
		greenPanel.setBounds(0,0,500,500);
		
	}
	
	/*
	 * Prépare la partie contre des bots
	 */
	public void botChoice() {
		
		this.setVisible(true);
		this.diePanel.setGameCanStart(true); //il n'y a pas de tour initial pour ce mode de jeu (le bleu commence toujours)
		this.step.setText("Game");
		Main.board.setBot(true);
		
	}

	/*
	 * Prépare la partie contre 3 autres joueurs
	 */
	public void playerChoice() {
		
		this.setVisible(true);
		this.diePanel.setGameCanStart(false);
		Main.board.setBot(false);
		
	}
	
}
