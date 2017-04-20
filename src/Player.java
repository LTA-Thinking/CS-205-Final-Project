
import java.util.*;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author neal
 */
public abstract class Player {
    
    private boolean isCurrentPlayer = false;
    protected Board board;
    protected Tile location;
    private ArrayList<Card> treasures = new ArrayList<>();
    private Card currentTreasure;
    private int score;
	private Rectangle displayPlayer;
	
    /**
     * set player's location to the input
     *
     * @param location
     * @param board
	 * @param color
     */
    public Player(Tile location, Board board, Color color) {
        this.board = board;
        this.location = location;
		
		displayPlayer = new Rectangle(20,20,color);
    }

    /**
     *
     */
    public abstract void takeTurn();

    /**
     * basic on the player's current, this method will return all possible
     * location the player can move to
     *
     * @return
     */
    public ArrayList<Point2D> allPossibleLoc() {
        ArrayList<Point2D> allPossibleLoc = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (pathExists(new Point2D(i, j))) {
                    allPossibleLoc.add(new Point2D(i, j));
                }
            }
        }
        return allPossibleLoc;
    }

    /**
     * take in a location, return true if a path exist from the current location
     * to the input location, otherwise return false
     *
     * @param location
     * @return
     */
    public boolean pathExists(Point2D location) {
        boolean[][] visited = new boolean[7][7];
        return pathExistsHelper(visited, this.location.getLocation(), location);
    }

    /**
     * recursive method help pathExists to find if a path exists from one
     * location to another.
     *
     * @param visited
     * @param start
     * @param end
     * @return
     */
     public boolean pathExistsHelper(boolean[][] visited, Point2D start, Point2D end) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();

        visited[x1][y1] = true;
        if (y1 < 6) {
            if (!visited[x1][y1 + 1] && board.canMove(start, Tile.SOUTH)) {
                if (x1 == x2 && y2 == y1 + 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1 + 1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (y1 > 0) {
            if (!visited[x1][y1 - 1] && board.canMove(start, Tile.NORTH)) {
                if (x1 == x2 && y2 == y1 - 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1 - 1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (x1 > 0) {
            if (!visited[x1 - 1][y1] && board.canMove(start, Tile.WEST)) {
                if (x2 == x1 - 1 && y2 == y1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1 - 1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (x1 < 6) {
            if (!visited[x1 + 1][y1] && board.canMove(start, Tile.EAST)) {
                if (x2 == x1 + 1 && y2 == y1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1 + 1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * get the location of the player
     *
     * @return the location
     */
    public Point2D getLocation() {
        return location.getLocation();
    }

    /**
     * set the location of the player
     *
     * @param location the location to set
     */
    public void setLocation(Point2D location) {
        this.location = board.getTile(location);
    }

    /**
     * @return the currentTreasure
     */
    public Card getCurrentTreasure() {
        return currentTreasure;
    }

    /**
     * @param currentTreasure the currentTreasure to set
     */
    public void setCurrentTreasure(Card currentTreasure) {
        this.currentTreasure = currentTreasure;
    }
    
    
    /**
     * this method will check if the user insert legal or not
     * @param insert
     * @return 
     */
    public boolean legalInsert(Point2D insert){
        Point2D lastMove = board.getLastMove();
        if(lastMove.getX() == 0 || lastMove.getX() == 6){
            if(lastMove.getY() == insert.getY()){
                return false;
            }else{
                return true;
            }
        }else if(lastMove.getY() == 0 || lastMove.getY() == 6){
            if(lastMove.getX() == insert.getX()){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
    
    public Point2D oppositeLoc(Point2D insert){
        Point2D lastMove = new Point2D(0,1);//board.getLastMove();
        if(insert.getX() == 0){
            return new Point2D(6, insert.getY());
        }else if(insert.getX() == 6){
            return new Point2D(0, insert.getY());
        }else if(insert.getY() == 0){
            return new Point2D(insert.getX(), 6);
        }else{
            return new Point2D(insert.getX(), 0);
        }
    }

    /**
     * @return the treasures
     */
    public ArrayList<Card> getTreasures() {
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
	public Node getDisplay()
	{
		return displayPlayer;
	}

}
