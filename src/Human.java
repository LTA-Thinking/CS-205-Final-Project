import java.util.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

/**
   The Human class represents the human player in a game of Labyrinth.
*/
public class Human extends Player
{
   //Keeps track of whether or not it's the player's turn.
   private boolean isTurn = false;
   
   /**Constructor for the Human class.*/   
   public Human(Board board, Color color)
   {
      super(board.getTile(new Point2D(0,0)), board, color);
   }
   
   /**
     Inserts the extra tile where the player wants to on the board.
     @param insertPoint The place for the tile to be inserted on the board.
   */
   @Override
   public void getInsertTile(Point2D insertPoint)
   {
   	//If it's the part of the turn when the player can insert,
      if(getPhaseOfTurn()==1)
	   {
		   //Inserts the tile where the player clicks, as long as it's a legal insert. 
		   if(super.legalInsert(insertPoint))
		   {
			   board.insertTile(insertPoint);
			   super.setPhaseOfTurn(2);
		   }
         //Otherwise, lets the user know they can't insert there. 
		   else
		   {
            Alert alert = new Alert(AlertType.WARNING);
			   alert.setTitle("Illegal move!");
			   alert.setHeaderText("");
			   String s = "Cannot insert there.";
			   alert.setContentText(s);
			   alert.show();	 
		   }
	   }
      //If the player already inserted, tells them to move.
   	else
    	{
         Alert alert = new Alert(AlertType.WARNING);
		   alert.setTitle("Cannot insert now!");
		   alert.setHeaderText("");
		   String s = "Please move your player.";
		   alert.setContentText(s);
		   alert.show();
      }
   }   
   
   /**
      Moves the player where they want to move on the board. Then, if they 
      got their treasure, sets the next treasure. It then ends the turn.
      @param moveTile The tile that the player wants to move to.
   */
   @Override
   public void getMoveTile(Tile moveTile)
   {
      //Removes the highlight from the board if it was there from helper mode.
      for(int i = 0; i < 7; ++i)
	   {
         for(int j = 0; j < 7; ++j)
		   {
        	   board.getTile(new Point2D(i,j)).unHighlight();
         }
      }
      board.setHighlight(false);
      
      //If the player is allowed to move,
	   if(getPhaseOfTurn() == 2)
	   {
		   //Gets the location the player clicked.
	  	   int x = moveTile.getXLocation();
		   int y = moveTile.getYLocation();
		   Point2D movePoint = new Point2D(x, y);
         
         //If a path exists to that point,
		   if(super.pathExists(movePoint))
		   {
            //Moves the player there.
			   super.setLocation(movePoint);
			   //Checks if the tile they moved to has the treasure they need.
			   Point2D treasureLocation = super.getCurrentTreasure().getTreasureLocation();
		  	   if(super.getLocation().equals(treasureLocation))
			   {
               //If it did, sets the next treasure.
				   super.location.removeTreasure();
				   super.setNextTreasure();
			   }
			 
			   //Ends the turn.
			   isTurn = false;
			   super.setPhaseOfTurn(0);
		   }
         //Alerts the player if they try to move illegally.
		   else
		   {
		   	Alert alert = new Alert(AlertType.WARNING);
			   alert.setTitle("Illegal Move");
			   alert.setHeaderText("");
			   String s = "Cannot move there!";
			   alert.setContentText(s);
			   alert.show();
		   }
	   }
      //Alerts the player that they have to insert the tile before moving.
      else
      {
         Alert alert = new Alert(AlertType.WARNING);
		   alert.setTitle("Can't move now!");
		   alert.setHeaderText("");
		   String s = "Please insert the extra tile.";
		   alert.setContentText(s);
		   alert.show();  
      }      
   }
   
   /**
      The isCurrentPlayer method returns whether or not it 
      is this player's turn.
      @return If it's the player's turn or not.
   */
   @Override
   public boolean isCurrentPlayer()
   {
   	return isTurn;
   }
   
   /**
      The takeTurn method enacts the turn for the human player.
   */
   @Override
   public void takeTurn()
   {
      isTurn = true;
	   super.setPhaseOfTurn(1);
   }


}
