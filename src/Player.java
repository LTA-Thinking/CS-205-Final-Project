
import java.util.*;

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

    private Board board;
    private Point2D location;
    private Card[] treasures = new Card[12];
    private Card currentTreasure;

    /**
     * set player's location to the input
     *
     * @param location
     * @param board
     */
    public Player(Point2D location, Board board) {
        this.board = board;
        this.location = location;
    }

    /**
     *
     */
    public abstract void takeTurn();
    
    /**
    * Creates the deck of treasures from the deck passed in
    * @param deck The deck of treasures to copy from.
    */
    public void makeDeck(Card[] deck)
    {
        for(int i = 0; i < deck.size; i++)
        {
            treasures[i] = deck[i];
        }  
        
    }

    /**
     * basic on the player's current, this method will return all possible
     * location the player can move to
     *
     * @return
     */
    public ArrayList<Point2D> allPossibleLoc() {
        ArrayList<Point2D> allPossibleLoc = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 6; ++j) {
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
        return pathExistsHelper(visited, this.location, location);
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
            if (!visited[x1][y1 + 1] && board.canMove(start, 0)) {
                if (x1 == x2 && y2 == y1 + 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (y1 > 1) {
            if (!visited[x1][y1 - 1] && board.canMove(start, 2)) {
                if (x1 == x2 && y2 == y1 + 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1 - 1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (x1 > 1) {
            if (!visited[x1][y1 - 1] && board.canMove(start, 4)) {
                if (x2 == x1 - 1 && y2 == y1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1 - 1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (y1 < 6) {
            if (!visited[x1 + 1][y1] && board.canMove(start, 1)) {
                if (x2 == x1 + 1 Ï&& y2 == y1) {
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
        return location;
    }

    /**
     * set the location of the player
     *
     * @param location the location to set
     */
    public void setLocation(Point2D location) {
        this.location = location;
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
        Point2D lastMove = board.lastMove();
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

}
