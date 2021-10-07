package ludogame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Player {
	
	private List<Pawn> pawns;
	private Pawn pawn1, pawn2, pawn3, pawn4;
		
	private int [][] cartesianCoordPath;
	
	private int numberPawnFinished;// Compte le nombre de pion arrivé à la base
	private String color;
	private boolean bot;
	
	Player(ImageIcon enableIcon, ImageIcon disableIcon, int[][] path, int [][] startCoord, String c, boolean bot){
		
		this.cartesianCoordPath = path;
		
		pawn1 = new Pawn(enableIcon, disableIcon, "1",startCoord[0], path, c, this);
		pawn2 = new Pawn(enableIcon, disableIcon, "2",startCoord[1], path, c, this);
		pawn3 = new Pawn(enableIcon, disableIcon, "3",startCoord[2], path, c, this);
		pawn4 = new Pawn(enableIcon, disableIcon, "4",startCoord[3], path, c, this);
		
		pawns = new ArrayList<Pawn>();
		
		pawns.add(pawn1);
		pawns.add(pawn2);
		pawns.add(pawn3);
		pawns.add(pawn4);
		
		numberPawnFinished = 0;
		this.color = c;
		
		this.bot = bot;
		
	}
	
	public Pawn getPawn(int i) {    
		return pawns.get(i);
	}
	
	public int getNumberPawFinished() {
		return this. numberPawnFinished;
	}
	
	public boolean getBot() {
		return this.bot;
	}
	
	public void setBot(boolean b) {
		this.bot = b;
	}
	
	public void setNumberPawnFinished(int i) {
		this.numberPawnFinished += i;
		if(this.numberPawnFinished==4) {Main.board.endGame(this);}
	}
	
		
	public void disablePawns() {
		
		this.pawn1.setEnabled(false);
		this.pawn2.setEnabled(false);
		this.pawn3.setEnabled(false);
		this.pawn4.setEnabled(false);	 
	}
	
	public String toString() {
		return this.color + "Player";
	}
}
	
