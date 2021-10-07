package ludogame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Board{
	
	private Player player1, player2, player3, player4;
	private int currentPlayer; //Joueur à qui c'est le tour de jouer
	private List<Player> players;
	
	private List<Integer> highestValue;
	private int highestRoller; 
	 
	private Box [][] boxes;//tableau représentant les 55 cases du plateau, et pouvant contenir 4 pièces par case.
	
	
	
	
	
	
	Board(){
		 
		 this.currentPlayer = 0;//Le player1 (le bleu) commencera à lancer le dé pour savoir qui commence la partie.
		 
		 player1 = new Player(new ImageIcon("bluePawnEnableIcon.png"), new ImageIcon("bluePawnDisableIcon.png"), Paths.blueCartesianCoordPath, Paths.pixelCoordStart[0],"blue", false);
		 player2 = new Player(new ImageIcon("yellowPawnEnableIcon.png"), new ImageIcon("yellowPawnDisableIcon.png"), Paths.yellowCartesianCoordPath, Paths.pixelCoordStart[3],"yellow", false);	 
		 player3 = new Player(new ImageIcon("greenPawnEnableIcon.png"), new ImageIcon("greenPawnDisableIcon.png"), Paths.greenCartesianCoordPath, Paths.pixelCoordStart[2], "green", false);
		 player4 = new Player(new ImageIcon("redPawnEnableIcon.png"), new ImageIcon("redPawnDisableIcon.png"), Paths.redCartesianCoordPath, Paths.pixelCoordStart[1], "red", false);
		
		 players = new ArrayList<Player>();
		 
		 players.add(player1);
		 players.add(player2);
		 players.add(player3);
		 players.add(player4);
		 
		 highestValue = new ArrayList<Integer>();
		 highestRoller = 0;
		 
		 boxes = new Box[15][15];
		 for (int i = 0; i < 15; i++) {
	            for (int j = 0; j < 15; j++) {
	                Box b = new Box();
	                boxes[i][j] = b;
	            }
	        }  
	 }
	 
	
	
	
	
	 public void setBot(boolean b) { //on définit tout les joueurs, sauf le joueur bleu, en bot 
		 player2.setBot(b);
		 player3.setBot(b);
		 player4.setBot(b);
	 }
	 
	 public Player getPlayer(int i){
		 return players.get(i);     
	 }	 
	 
	 public int getCurrentPlayerIndex() {
		 return (this.currentPlayer%4); //au cours de la partie, après chaque tour, l'attribut current player augmente de 1, or on à une liste de 4 player seulement, donc on prend le currentPlayer modulo 4 pour avoir l'entier compris entre 0 et 3 correspondant
	 }
	 
	 public void setCurrentPlayerIndex(int i) {
		 this.currentPlayer += i;
	 }
	 
	 public Player getCurrentPlayer() {
		 return players.get(getCurrentPlayerIndex());
	 }
	 
	 public void setBoundsPawn(int x, int y, Pawn p) {
		 p.setBounds(x*Main.PAWN_DIMENSION+(this.boxes[x][y].box.size()-1)*15, y*Main.PAWN_DIMENSION, Main.PAWN_DIMENSION,  Main.PAWN_DIMENSION);
	 }
	 
	 public void addPawnInBox(int x, int y, Pawn p) {
		 boxes[x][y].box.add(p);
	 }
	 
	 public void removePawnInBox(int x, int y, Pawn p) {
		 boxes[x][y].box.remove(p);
	 }

	 
	 
	 
	 
	 
	 
	 /*Si la case en paramètre (x,y)  contient un pion de la même couleur que celui en paramètre,
	  * la méthode retourne le pion que contient cette case
	  */
	 public Pawn containsOneFriendlyPawn(int x, int y, Pawn p) {
		 Pawn pawnReturned=null;
		 Pawn theOtherPawn;
		 
		 if(boxes[x][y].box.size()==2) {
			 
			 if(boxes[x][y].box.get(0)==p) {
				 theOtherPawn= boxes[x][y].box.get(1);
			 }
			 else theOtherPawn= boxes[x][y].box.get(0);
			 
			 
			 
			 if(theOtherPawn.getColor().equals(p.getColor())) {
				 pawnReturned = theOtherPawn;
			 }	
		 }

		 return pawnReturned;	 
	 }
	 
	 
	 public boolean containsEnemyPawn(int x, int y, Pawn p) {
		 
		 if( (x!=6||y!=2) && (x!=2||y!=8) && (x!=12||y!=6) && (x!=8||y!=12) && boxes[x][y].box.size()==1 && !boxes[x][y].box.get(0).getColor().equals(p.getColor()) ) {
			 return true;}
		 
		 else return false; 
	 }
	 
	 public boolean eatEnemyPawn(int x, int y, Pawn p) {
		 
		 if(containsEnemyPawn(x,y,p)) {
			boxes[x][y].box.get(0).backToBase();
			boxes[x][y].box.remove(0);
			return true;
		 } 
		 else return false;
	 }
	 
	 public boolean isBlocked(int x, int y, Pawn p) {
		 if (boxes[x][y].box.size()>1 && !boxes[x][y].box.get(0).getColor().equals(p.getColor()) && boxes[x][y].box.get(0).getColor().equals(boxes[x][y].box.get(1).getColor())) 	
			return true;
		
		 else return false;
	 }
	 	 
	 

	 
	 
	 
	 
	 /*Détermine qui fait le numéro le plus haut au début pour pouvoir commmencer
	  */
	 public boolean determineWhoStart(int dieValue) {
		 
		boolean gameCanStart=false;
		 
		if(getCurrentPlayerIndex()==0) {//si currentPlayer%4 == 0, cela veut dire que tous les joueurs ont lancé une fois le dé.
			highestValue.clear();
			highestValue.add(0);
		}
		
		if(dieValue>highestValue.get(0)) {
				
			highestValue.clear();
			highestValue.add(dieValue);		
			highestRoller = getCurrentPlayerIndex();
		}
		
		else if(dieValue==highestValue.get(0)) {
			highestValue.add(dieValue);
		}
		
		endTurn(true,true);
			
		if((getCurrentPlayerIndex()==0) && (highestValue.size()==1)) {
			setCurrentPlayerIndex(highestRoller);
			gameCanStart = true;
		}
		return gameCanStart;

	 }
	 
	 
	 
	 
	 /*
	  * Actives les pions selon la valeur du dé et les caractéristiques de la case d'arrivée d pion
	  */
	 public void enablePawns(int dieValue) {//Active les pions qui peuvent être bougés
		
		 if(dieValue == 6) {
			 for(int i=0; i<4; i++) {
				if(getCurrentPlayer().getPawn(i).getIndex()+dieValue<57 && !getCurrentPlayer().getPawn(i).pathIsBlocked(dieValue)) //Le pion ne peut pas aller plus loin que la case 56 (arrivée) et doit faire le chiffre exact pour y entrer
				{
					getCurrentPlayer().getPawn(i).setEnabled(true);
				}
				else endTurn(false,true);
			 }	 
		}
		else {
			int pawnOutOfTheBase=0;
			
			for(int i=0; i<4;i++) {
				if(getCurrentPlayer().getPawn(i).getIndex()>=0 && (getCurrentPlayer().getPawn(i).getIndex()+dieValue<57) && !getCurrentPlayer().getPawn(i).pathIsBlocked(dieValue)) {
					getCurrentPlayer().getPawn(i).setEnabled(true);
					pawnOutOfTheBase++;
				}
			}
			if(pawnOutOfTheBase==0) {
				endTurn(false,true);
			}
		}
		
		 if(this.getCurrentPlayer().getBot()) { //une fois les pions activés, on vérifie si le joueur actuel est un bot et si oui on déclenche le tour du bot
			 botTurn(dieValue);
		 }
		 
	 }
	 
	 
	 
	 
	 
	 /* Une fois que le joueur à joué, on désactive tous ses pions, 
	  * Selon le cas, on passe au joueur suivant en ajoutant +1 à currentPlayer
	  * On affiche qui doit jouer ensuite
	  * et on réactive le bouton pour lancer le dé 
	  */
	 public void endTurn(boolean beforeStart, boolean didntEat) {
	        
         getCurrentPlayer().disablePawns();
         if(beforeStart || (Main.frame.diePanel.getDieValue()!=6 && didntEat)) //Si le joueur fait 6 il peut rejouer mais seulement lorsque la partie a commencé, pas quand on determine qui commence
         {
             this.setCurrentPlayerIndex(1);
         }
         
         switch(Main.board.getCurrentPlayerIndex()) {
         case 0:
             Main.frame.turn.setText("Blue turn");
             break;
         case 1:
             Main.frame.turn.setText("Yellow turn");
             break;
         case 2:
             Main.frame.turn.setText("Green turn");
             break;
         case 3:
             Main.frame.turn.setText("Red turn");
             break;
         }
         
         Main.frame.dieButton.setEnabled(true); 
         this.botCheck(); // voir méthode botCheck
    }
	 
	 
	 
	 /*
	  * Fenetre de fin de partie
	  */
	 
	public void endGame(Player player) {
		JFrame f = new JFrame();
		f.setSize(300,100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		JLabel l = new JLabel();
		l.setText(player+" has won !");
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVerticalAlignment(JLabel.CENTER);
		l.setHorizontalTextPosition(JLabel.CENTER);
		l.setVerticalTextPosition(JLabel.CENTER);
		l.setVisible(true);
		f.add(l);
		Main.frame.dispose();
	}
	
	
	

	
	//========================================================================= Partie IA

	
	/*
	 * On vérifie si le joueur actuel est un bot ou non
	 * Si oui, on "lance" les dés 
	 */
	public void botCheck() { 
		
		if(this.getCurrentPlayer().getBot()) {
			Main.frame.diePanel.rollDie();		
		} 

	}
	
	
	/*
	 * La fonction qui définit le caractère du bot
	 */
	public void botTurn(int dieValue) {

        boolean hasPlayed = false; 

        /*
         * On établie 4 possibiltiés pour un pion activé dans cet ordre de priorité:
         *     - rejoindre la dernière case (finir)
         *     - sortir un pion de la base
         *     - manger un pion
         *     - avancer le pion le moins loin
         */
        for(int i=0 ; i<4 ; i++) { //on vérifie pour chaque pion les possiblités
            if(getCurrentPlayer().getPawn(i).isEnabled() && botCheckFinish(dieValue, getCurrentPlayer().getPawn(i))) {
                endTurn(false, !getCurrentPlayer().getPawn(i).movesCases(dieValue));
                hasPlayed = true;
                break;
            }
            if(getCurrentPlayer().getPawn(i).isEnabled() && botCheckOut(dieValue, getCurrentPlayer().getPawn(i))) {
                endTurn(false, !getCurrentPlayer().getPawn(i).movesCases(dieValue));
                hasPlayed = true;
                break;
            }
            if(getCurrentPlayer().getPawn(i).isEnabled() && botCheckEat(dieValue, getCurrentPlayer().getPawn(i))) {
                endTurn(false, !getCurrentPlayer().getPawn(i).movesCases(dieValue));
                hasPlayed = true;
                break;
            }
        }

        //si on ne peut ni finir, ni sortir ou manger de pion, on bouge le moins avancé
        if(botCheckLessAdvanced(this.getCurrentPlayer()) != null && hasPlayed == false) {
            endTurn(false, !this.botCheckLessAdvanced(this.getCurrentPlayer()).movesCases(dieValue));
        }else if(botCheckLessAdvanced(this.getCurrentPlayer()) == null && hasPlayed == false) {
            endTurn(false,true);
        }

    }


	
	/*
	 * Permet de vérifier si un pion peut finir
	 */
	public boolean botCheckFinish(int dieResult, Pawn pawn) {
		
		boolean temp = false;
		
		if(pawn.getIndex() + dieResult == 56) {
			temp = true;
		}
			
		return temp;
	}
	
	/*
	 * Permet et vérifier si un pion peut sortir
	 */
	public boolean botCheckOut(int dieResult, Pawn pawn) {
		
		boolean temp = false;
		
		if(dieResult == 6 && pawn.getIndex() == -1) {
			temp = true;
		}
		
		return temp;
		
	}

	/*
	 * Permet de vérifier si un pion peut manger un pion 
	 */
	public boolean botCheckEat(int dieResult, Pawn pawn) {
		
		boolean temp = false;
		
		temp = eatEnemyPawn(pawn.cartesianCoordPath[pawn.getIndex()+dieResult][0]-1,pawn.cartesianCoordPath[pawn.getIndex()+dieResult][1]-1, pawn);

		return temp;	
	}
	
	
	
	
	/*
     * Renvoie le pion le moins avancé
     */
	public Pawn botCheckLessAdvanced(Player player) {

        Pawn pawnLessAdvanced = null;;
        Pawn[] pawns = new Pawn[4];

        for(int i=0 ; i<4 ; i++) {
            pawns[i] = player.getPawn(i);
        }


        //on trie les pions par index croissant
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (pawns[i].getIndex() < pawns[j].getIndex()) {
                    Pawn temp = pawns[i];
                    pawns[i] = pawns[j];
                    pawns[j] = temp;
                }
            }
        }

        //on récupère le pion avec le plus petit index différent de -1
        for(int i=0 ; i<4 ; i++) {
            if(pawns[i].getIndex() > -1 && pawns[i].isEnabled()) {
                pawnLessAdvanced = pawns[i];
                break;
            }
        }

        return pawnLessAdvanced;
    }


}