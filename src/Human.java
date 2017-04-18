import java.util.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

/**
   The Human class represents the human player in a game of Labyrinth.
*/
public class Human extends Player
{
   //Keeps track of whether or not it's the player's turn.
   private boolean isTurn;
   
   
   /**Constructor for the Human class.*/   
   public Human(Board board){
      super(board.getTile(new Point2D(0,0)), board);
   }
   
   /**Inserts the extra tile where the player wants to on the board.
     @param insertTile The tile to be inserted on the board.
   */
   public void getInsertTile(Tile insertTile)
   {
     
      //Inserts the tile where the player clicks.
      int x = insertTile.getXLocation();
      int y = insertTile.getYLocation(); 
      Point2D insertLocation = new Point2D(x, y);
      if(super.legalInsert(insertLocation))
      {
         board.insertTile(new Point2D(x, y));
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
   
   /**Moves the player where they want to move on the board. Then, if they 
      got their treasure, sets the next treasure. It then ends the turn.
      @param moveTile The tile that the player wants to move to.
   */
   public void getMoveTile(Tile moveTile)
   {
      //Move to the right location
      int x = moveTile.getXLocation();
      int y = moveTile.getYLocation();
      Point2D movePoint = new Point2D(x, y);
      if(super.pathExists(movePoint))
      {
         super.setLocation(movePoint);
      }
      
      //Checks if the tile they moved to has the treasure they need.
      Point2D treasureLocation = super.getCurrentTreasure().getTreasureLocation();
      
      if(super.getLocation().equals(treasureLocation))
      {
         super.setNextTreasure();
      }
      
      //Ends the turn.
      isTurn = false;
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
   }

}
