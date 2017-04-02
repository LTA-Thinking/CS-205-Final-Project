
import javafx.geometry.Point2D;

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
    private Point2D location; 
    private Card[] treasures = new Card[24];
    
    /**
     * set player's location to the input
     * @param location
     */
    public Player(Point2D location){
        this.location = location;
    }
    
    /**
     * 
     */
    public abstract void takeTurn();
    
    /**
     * take in a location, return true if a path exist from the current 
     * location to the input location, otherwise return false
     * @param location
     * @return 
     */
    public boolean pathExist(Point2D location){
       
        return false;
    }

    /**
     * get the location of the player
     * @return the location
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     * set the location of the player
     * @param location the location to set
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }
    
}
