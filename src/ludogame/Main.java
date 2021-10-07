package ludogame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
	final static int FRAME_SIZE = 1000;
	final static int BUTTON_PANEL_SIZE = 800;
	final static int DIE_PANEL_SIZE = 200;
	final static int PAWN_DIMENSION = 50;
	
	public static Board board;
	public static MyFrame frame;

	
	
	
	public static void main(String[] args) {		
	
		
		frame = new MyFrame(); //déclaration de la frame 
		frame.menu(); //affichage du menu (choix du mode de jeu)
		
		JPanel boardPanel = new JPanel(); //déclaration du Panel qui va contenir le plateau (label) et le pions (button)
		JPanel diePanel = new JPanel(); //déclaration du Panael qui va contenir le dé et son bouton
		
		JLabel boardBackgroundLabel = new JLabel();//déclaration de l'image de fond du plateau
		boardBackgroundLabel.setIcon(new ImageIcon("boardBackground.jpg"));
		
	
		board = new Board();
		 
		for(int i=0; i<4;i++) {
			for(int j=0 ;j<4;j++) {
				boardPanel.add(board.getPlayer(i).getPawn(j)); //Place les pions dans la base
			}
		}
		
		
		boardPanel.add(boardBackgroundLabel); 
		frame.add(boardPanel);
		
		boardBackgroundLabel.setBounds(0,-25,BUTTON_PANEL_SIZE,BUTTON_PANEL_SIZE);
		boardPanel.setBounds(0,0,BUTTON_PANEL_SIZE,BUTTON_PANEL_SIZE);
			
		boardPanel.setLayout(null);
		boardBackgroundLabel.setLayout(null);
			
		
	}

	
	
}
