import java.util.*;

/**
   The Human class represents the human player in a game of Labyrinth.
*/
public class Human extends Player
{
   /**Constructor for the Human class.*/   
   public Human(){
      super(new Point2D(0, 0));
   }

   /**
   The takeTurn method enacts the turn for the human player.
   @param -These might change
   */
   @override
   private void takeTurn(Tile insertTile, Tile moveTile)
   {
     
     //inserting the tile
      int x = insertTile.getXLocation();
      int y = insertTile.getYLocation(); 
      if(super.checkLegalMove(insertTile))
      {
         //placeholder
         board.InsertTile(x, y);
      } 
      
      //Move to the right location
      x = moveTile.getXLocation();
      y = moveTile.getYLocation();
      if(super.pathExists(moveTile))
      {
         super.setLocation(new Point2D(x, y));
      }
      
      Tile treasureTile = currentTreasure.getTreasureLocation();
      x = treasureTile.getXLocation();
      y = treasureTile.getYLocation();
      Point2D treasureLocation = new Point2D(x, y);
      
      if(super.getLocation.equals(treasureLocation))
      {
         //add currentTreasure to ArrayList
         //if treasure is gotten, get a new treasure 
         
      }
      
      //Return true if max treasures/all treasures gone
 
   }

}