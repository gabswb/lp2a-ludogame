package ludogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Pawn extends JButton implements ActionListener{
	
	private int index=-1; //L'index correspond au num�ro de la case sur laquelle le pion est situ� actuellement, l'index -1 correspond � la base, et l'index 0 correspond � la premi�re case du jeu
	
	protected int [][] cartesianCoordPath;
	protected int [] pixelCoordStart;
	
	private String color;
	
	private Player superPlayer;//Correspond au joueur qui poss�de ce pion
		
	
	
	
	Pawn(ImageIcon enableIcon, ImageIcon disableIcon, String nb, int[] tab, int [][] path, String color,Player p) {
		
		this.cartesianCoordPath = path;
		this.pixelCoordStart = tab;
		
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setIcon(enableIcon);
		this.setDisabledIcon(disableIcon);   //pour pas que les pions soient gris�s
		this.setLayout(null);
		this.setFocusable(false);
		this.setBounds(pixelCoordStart[0], pixelCoordStart[1], Main.PAWN_DIMENSION,Main.PAWN_DIMENSION);
		this.setEnabled(false); 
		this.setText(nb);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setFont(new Font("Arial",Font.PLAIN,25));
		this.setForeground(Color.white);
		
		this.addActionListener(this);
		
		this.superPlayer = p;
		
		this.color = color;
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Main.board.endTurn(false, !movesCases(Main.frame.diePanel.getDieValue()));
	}
	
	
	
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int n) {
		this.index += n;
	}
	
	public String getColor() {
		return this.color;
	}
	

	
	
	
	/*
	 * Prend en parametre le pion � bouger, le numero obtenu par le d�.
	 * 
	 * L'image du plateau a pour dimension 750x750, il y a donc 15x15 cases de dimension 50x50.
	 * Pour passer des coordonn�es cartesiennes (par unit� de case) au coordonn�es en pixel on a juste � multiplier les coordonn�es-1 par le nombre de case.
	 * */
	public boolean movePawn(int dieValue) {
		
		boolean eat=false;
		if(dieValue==0) {return eat;}
		
		
		eat = Main.board.eatEnemyPawn(cartesianCoordPath[this.index+dieValue][0]-1,cartesianCoordPath[this.index+dieValue][1]-1, this);
		
		Main.board.addPawnInBox(cartesianCoordPath[this.index+dieValue][0]-1, cartesianCoordPath[this.index+dieValue][1]-1, this);
		
		if(index>=0) //si le pion est � la base pas besoin de le retirer du tableau boxes car il n'est pas dedans
		{
			Main.board.removePawnInBox(cartesianCoordPath[this.index][0]-1, cartesianCoordPath[this.index][1]-1, this);
		}
		
		Main.board.setBoundsPawn((cartesianCoordPath[this.index+dieValue][0]-1), (cartesianCoordPath[this.index+dieValue][1]-1),this);
		
		this.setIndex(dieValue);			
		
		if (this.index==56) {superPlayer.setNumberPawnFinished(1);}
		
		
		return eat;

	}
	
		
	public boolean movesCases(int dieValue) {
		boolean temp;
		
		if(dieValue == 6 && this.index == -1) {
			return movePawn(1);
		}
		
		else if(Main.board.containsOneFriendlyPawn(cartesianCoordPath[this.index][0]-1,cartesianCoordPath[this.index][1]-1, this) !=null) {
			temp =Main.board.containsOneFriendlyPawn(cartesianCoordPath[this.index][0]-1,cartesianCoordPath[this.index][1]-1, this).movePawn(dieValue/2);
			movePawn(dieValue/2);
			
			return temp;
		}
		
		else return movePawn(dieValue);	 		
	}
	
	
	
	
	public boolean pathIsBlocked(int dieValue) {
		boolean result=false;
		
		if(index>=0) {
			
			for(int i=index; i<index+dieValue; i++) {
				if(Main.board.isBlocked(cartesianCoordPath[i][0]-1, cartesianCoordPath[i][1]-1, this)) { result = true;}
			}
		}

		return result;
	}
	
	
	
	
	public void backToBase() {
		this.setBounds(pixelCoordStart[0], pixelCoordStart[1],Main.PAWN_DIMENSION,Main.PAWN_DIMENSION);
		this.index = -1;
	}
	
}
