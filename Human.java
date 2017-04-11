import java.util.*;

public class Human extends Player
{
   
   public Human(){
      super(new Point2D(0, 0));
   }

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
      

   /**
   */
   private boolean checkLegalMove(Point2D location)
   {
      //if x = 0 check if x = maximum and y = y
      //if y = 0 check if y = maximum and x = x
      
   }

}