
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
public class Computer extends Player{
    private Point2D[] allMoveLoc; 
    
    /**
     * constructor
     */
    public Computer(){
        super(new Point2D(0, 0));
    }

    @Override
    public void takeTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
