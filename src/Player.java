
import java.util.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/*
   The Player class represents either a Human or a Computer in a game of Labyrinth.
 */
/**
 *
 * @author neal
 */
public abstract class Player 
{
    private int phaseOfTurn = 999;
    private boolean isCurrentPlayer = false;
    protected Board board;
    protected Tile location;
    private ArrayList<Card> treasures = new ArrayList<>();
    private Card currentTreasure;
    private int score = 0;
    private Rectangle displayPlayer;
    private Color color;
	
    /**
    * Constructor for the Player class. 
    *
    * @param location The location to set the player at.
    * @param board The game board.
    * @param color The color of the player.
    */
    public Player(Tile location, Board board, Color color)
    {
       this.board = board;
       this.location = location;
       this.color = color;
       displayPlayer = new Rectangle(20,20,color);	
       this.location.addPlayer(this);
    }

    /**
    * Abstract method for the Player's turn.
    */
    public abstract void takeTurn();
	
    /**
    * Abstract method for getting the spot the player wants to move to.
    * @param moveTile The tile to move to.
    */
    public void getMoveTile(Tile moveTile){};
	
    /**
    * Abstract method for getting where the player wants to insert the tile.
    * @param insertPoint The place to insert the tile.
    */
    public void getInsertTile(Point2D insertPoint){};

    /**
    * Based on the player's current location, this method will return all possible
    * locations the player can move to.
    * @return The array of all locations the player can move to.
    */
    public ArrayList<Point2D> allPossibleLoc() 
    {
       ArrayList<Point2D> allPossibleLoc = new ArrayList<>();
       //Iterates through all locations on the board to see if a path exists to there.
       for (int i = 0; i < 7; ++i) 
       {
          for (int j = 0; j < 7; ++j) 
	  {
             if (pathExists(new Point2D(i, j))) 
	     {
                allPossibleLoc.add(new Point2D(i, j));
             }
          }
       }
       return allPossibleLoc;
    }

    /**
    * The pathExists method takes in a location and returns true if a path exists from the current location
    * to the input location. Otherwise, returns false.
    * @param location The location to check if a path exists to.
    * @return Whether or not a path exists to that location.
    */
    public boolean pathExists(Point2D location) 
    {
        boolean[][] visited = new boolean[7][7];
        return pathExistsHelper(visited, this.location.getLocation(), location);
    }

    /**
    * This ethod is a recursive method to help pathExists find if a path exists from one
    * location to another.
    * @param visited Which locations were visited.
    * @param start The start location.
    * @param end The end location.
    * @return Whether or not a path exists from the start point to the end point.
    */
    public boolean pathExistsHelper(boolean[][] visited, Point2D start, Point2D end) 
    {
       int x1 = start.getX();
       int y1 = start.getY();
       int x2 = end.getX();
       int y2 = end.getY();

       if(start.equals(end))
          return true;
		
       visited[x1][y1] = true;
	    
       //For each direction on the board, check if a path exists in that direction.
       if (y1 < 6) 
       {
          if (!visited[x1][y1 + 1] && board.canMove(start, Tile.SOUTH))
	  {
             if (x1 == x2 && y2 == y1 + 1)
	     {
                return true;
             }
             if (pathExistsHelper(visited, new Point2D(x1, y1 + 1), new Point2D(x2, y2))) 
	     {
                return true;
             }
          }
       }
       if (y1 > 0) 
       {
          if (!visited[x1][y1 - 1] && board.canMove(start, Tile.NORTH))
	  {
             if (x1 == x2 && y2 == y1 - 1) 
	     {
                return true;
             }
             if (pathExistsHelper(visited, new Point2D(x1, y1 - 1), new Point2D(x2, y2))) 
	     {
                return true;
             }
          }
       }
       if (x1 > 0)
       {
          if (!visited[x1 - 1][y1] && board.canMove(start, Tile.WEST))
	  {
             if (x2 == x1 - 1 && y2 == y1) 
	     {
                return true;
             }
             if (pathExistsHelper(visited, new Point2D(x1 - 1, y1), new Point2D(x2, y2))) 
	     {
                return true;
             }
          }
       }
       if (x1 < 6)
       {
          if (!visited[x1 + 1][y1] && board.canMove(start, Tile.EAST)) 
	  {
             if (x2 == x1 + 1 && y2 == y1) 
	     {
                return true;
             }
             if (pathExistsHelper(visited, new Point2D(x1 + 1, y1), new Point2D(x2, y2))) 
	     {
                return true;
             }
          }
       }

       return false;
    }

    /**
    * Gets the location of the player.
    * @return The player's location.
    */
    public Point2D getLocation() 
    {
       return location.getLocation();
    }

    /**
    * Sets the location of the player.
    * @param location The location to set the player at.
    */
    public void setLocation(Point2D location) 
    {
       this.location.removePlayer(this);
       this.location = board.getTile(location);	
       this.location.addPlayer(this);
    }

    /**
    * Returns the player's current treasure.
    * @return The current treasure.
    */
    public Card getCurrentTreasure()
    {
       return currentTreasure;
    }

    /**
    * Sets the current treasure.
    * @param currentTreasure The treasure to set.
    */
    public void setCurrentTreasure(Card currentTreasure) 
    {
       this.currentTreasure = currentTreasure;
    }
    
    
    /**
    * This method checks if the user's insert is legal or not.
    * @param insert The place the tile will be inserted.
    * @return If the insert is legal or not.
    */
    public boolean legalInsert(Point2D insert)
    {
       Point2D lastMove = board.getLastMove();
		
       if((lastMove.getX() == 0 && insert.getX() == 6) || (lastMove.getX() == 6 && insert.getX() == 0))
       {
          if(lastMove.getY() == insert.getY())
	  {
             return false;
          }
	  else
          {
             return true;
          }
       }
       else if((lastMove.getY() == 0 && insert.getY() == 6) || (lastMove.getY() == 6 && insert.getY() == 0))
       {
          if(lastMove.getX() == insert.getX())
	  {
             return false;
          }
	  else
    	  {
             return true;
          }
       }
       else
       {
          return true;
       }
    }

    /**
    * Returns the player's treasures.
    * @return The player's treasures.
    */
    public ArrayList<Card> getTreasures() 
    {
       return treasures;
    }
    
    /**
    * Removes the current treasure from the ArrayList and gets the next one. 
    * Then, updates the score by one.
    *
    */
    public void setNextTreasure()
    {  
        treasures.remove(currentTreasure);
        score++;
        if(treasures.size() != 0)
        {
            currentTreasure = this.treasures.get(0);
			currentTreasure.setDisplayColor(color);
        }  
    }
    
    public boolean checkWin()
    {
       if(treasures.size()==0)
       {
           return true;
       }
       return false;
        
    }

    /**
     * @param treasures the treasures to set
     */
    public void setTreasures(ArrayList<Card> treasures) {
        this.treasures = treasures;
        currentTreasure = this.treasures.get(0);
		currentTreasure.setDisplayColor(color);
    }

    /**
     * @return the isCurrentPlayer
     */
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * @param isCurrentPlayer the isCurrentPlayer to set
     */
    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }
	
	/**
	 * Returns the node to display coresponding to the player.
	 */
	public Rectangle getDisplay()
	{
		return displayPlayer;
	}

	/**
	 * Returns the player's score;
	 */
	public int getScore()
	{
		return score;
	}

    /**
     * @return the phaseOfTurn
     */
    public int getPhaseOfTurn() {
        return phaseOfTurn;
    }

    /**
     * @param phaseOfTurn the phaseOfTurn to set
     */
    public void setPhaseOfTurn(int phaseOfTurn) {
        this.phaseOfTurn = phaseOfTurn;
    }
}
