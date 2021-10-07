package ludogame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class DiePanel extends JPanel {
	 
	private int dieValue;
	private Random rand;
	
	private boolean gameCanStart;
	
	
	
	DiePanel(){
		
		dieValue = 0;
		rand = new Random();
		gameCanStart = false;	
	}
	
	
	
	public int getDieValue() {
		return dieValue;
	}
	
	public void setGameCanStart(boolean b) {
		this.gameCanStart = b;
	}
	
	
	
	
	public void rollDie() {
		
		dieValue =  rand.nextInt(6)+1;
		repaint();

		
		if(Main.board.getCurrentPlayerIndex() == 0)//on efface l'"hisorique" des dés quand c'est au joueur bleu de jouer
		{ 
			Main.frame.die.setText("");
		}
		Main.frame.die.setText(Main.frame.die.getText()+"<html>"+Main.board.getCurrentPlayer()+" did a "+this.dieValue+"<br>"); //historique des dés (on utilise du html car on ne peut pas sauter de ligne sinon)
		
		
		
		Main.frame.dieButton.setEnabled(false);
		if(!gameCanStart)//Tant que un joueur n'a pas eu au dessus de tous les autres, on recommence.
		{	
			gameCanStart = Main.board.determineWhoStart(dieValue);
		}
		else// début de la partie	
		{	
			Main.frame.step.setText("GAME"); //on affiche que la partie à commencée 
			Main.board.enablePawns(dieValue);
		}
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
 
		int width = getWidth()/2;
		int height = width;
 
		int ddice = dieValue;
		if ( ddice>0 ) { // s'il y a un dé tiré on le dessine
			g.setColor(Color.ORANGE);
 
			// on dessine le dé centré
			int x = (getWidth()-width)/2;
			int y = (getHeight()-height)/2;
			g.fillRoundRect(x, y, width, height, width/10, width/10);
 
			// dessin des points
			int ray = width/10;
			g.setColor(Color.BLACK);
			if ( ddice==1 || ddice==5 || ddice==3 ) {
				// point au centre
				g.fillOval(getWidth()/2 - ray, getHeight()/2 - ray, ray*2, ray*2);
			}
			if ( ddice==2 || ddice==3 || ddice==4 || ddice==5 || ddice==6 ) {
				// point en haut à gauche
				g.fillOval(x+ray, y+ray, ray*2, ray*2);
			}
			if ( ddice==4 || ddice==5 || ddice==6 ) {
				// point en haut à droite
				g.fillOval(x+width-3*ray, y+ray, ray*2, ray*2);
			}
			if ( ddice==4 || ddice==5 || ddice==6 ) {
				// point en bas à gauche
				g.fillOval(x+ray, y+width-3*ray, ray*2, ray*2);
			}
			if ( ddice==2 || ddice==3 || ddice==4 || ddice==5 || ddice==6 ) {
				// point en bas à droite
				g.fillOval(x+width-3*ray, y+width-3*ray, ray*2, ray*2);
			}
			if ( ddice==6 ) {
				// point à gauche et à droite
				g.fillOval(x+ray, getHeight()/2 - ray, ray*2, ray*2);
				g.fillOval(x+width-3*ray, getHeight()/2 - ray, ray*2, ray*2);
			}
		}
	}
}