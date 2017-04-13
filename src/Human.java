import java.util.*;

/**
   The Human class represents the human player in a game of Labyrinth.
*/
public class Human extends Player
{
   //Keeps track of whether or not it's the player's turn.
   boolean isTurn;
   
   /**Constructor for the Human class.*/   
   public Human(){
      super(new Point2D(0, 0));
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
         board.setTile(insertTile);
      } 
   }
   
   /**Moves the player where they want to move on the board. Then, if they 
      got their treasure, sets the next treasure. It then ends the turn.
      @param moveTile The tile that the player wants to move to.
   */
   public void getMoveTile(Tile moveTile)
   {
      //Move to the right location
      x = moveTile.getXLocation();
      y = moveTile.getYLocation();
      if(super.pathExists(moveTile))
      {
         super.setLocation(new Point2D(x, y));
      }
      
      //Checks if the tile they moved to has the treasure they need.
      Tile treasureTile = currentTreasure.getTreasureLocation();
      x = treasureTile.getXLocation();
      y = treasureTile.getYLocation();
      Point2D treasureLocation = new Point2D(x, y);
      if(super.getLocation.equals(treasureLocation))
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
   private void takeTurn()
   {
      isTurn = true;
   }

}
