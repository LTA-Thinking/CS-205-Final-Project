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
   private int phaseOfTurn = 0;
   
   /**Constructor for the Human class.*/   
   public Human(Board board, Color color){
      super(board.getTile(new Point2D(0,0)), board, color);
   }
   
   /**Inserts the extra tile where the player wants to on the board.
     @param insertTile The tile to be inserted on the board.
   */
   @Override
   public void getInsertTile(Point2D insertPoint)
   {
     if(phaseOfTurn==1)
	 {
		  //Inserts the tile where the player clicks.
		  
		  if(super.legalInsert(insertPoint))
		  {
			 board.insertTile(insertPoint);
			 phaseOfTurn = 2;
		  } 
		  else
		  {
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("Illegal Move");
			 alert.setHeaderText("");
			 String s = "Cannot insert there!";
			 alert.setContentText(s);
			 alert.show();
		  }
	 }
   }
   
   /**Moves the player where they want to move on the board. Then, if they 
      got their treasure, sets the next treasure. It then ends the turn.
      @param moveTile The tile that the player wants to move to.
   */
   @Override
   public void getMoveTile(Tile moveTile)
   {
       for(int i = 0; i < 7; ++i){
            for(int j = 0; j < 7; ++j){
                board.getTile(new Point2D(i,j)).unHighlight();
            }
        }
        board.setHighlight(false);
	   if(phaseOfTurn == 2)
	   {
		  //Move to the right location
		  int x = moveTile.getXLocation();
		  int y = moveTile.getYLocation();
		  Point2D movePoint = new Point2D(x, y);
		  if(super.pathExists(movePoint))
		  {
			 super.setLocation(movePoint);
			 //Checks if the tile they moved to has the treasure they need.
			 Point2D treasureLocation = super.getCurrentTreasure().getTreasureLocation();
		  
			 if(super.getLocation().equals(treasureLocation))
			 {
				super.location.removeTreasure();
				super.setNextTreasure();
			 }
			 
			 //Ends the turn.
			 isTurn = false;
			 phaseOfTurn = 0;
		  }
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
   }
   
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
	  phaseOfTurn = 1;
   }

}
