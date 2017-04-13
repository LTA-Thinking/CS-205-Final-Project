
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
public class Computer extends Player {

    private Board board;
    private Point2D[] allMoveLoc;

    /**
     * constructor
     */
    public Computer(Board board) {
        super(new Point2D(0, 0), board);
    }

    @Override
    public void takeTurn() {
        double distance = 999; 
        Tile tile;
        Point2D insertPoint;
        
        
//        HashMap<HashMap<Tile, Point2D>, Double> memory = new HashMap<>();
        Point2D[] possibleMove = {new Point2D(0, 1), new Point2D(0, 3), new Point2D(0, 5), new Point2D(1, 0), new Point2D(3, 0), new Point2D(5, 0)};
        for (int i = 0; i < possibleMove.length; ++i) {
            if (super.legalInsert(possibleMove[i])) {
                for (int j = 0; j < 3; ++j) {
                    
                    board.insertTile(j);
                    ArrayList<Point2D> posLoc = super.allPossibleLoc();
                    for (int k = 0; k < posLoc.size(); ++k) {
                        if(posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation()) < distance){
                            distance = posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation());
                            tile = board.getTile(posLoc.get(k));
                            insertPoint = possibleMove[i];
                        }
//                        double distance = posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation());
//                        HashMap<Tile, Point2D> move = new HashMap<>();
//                        move.put(super.getCurrentTreasure().getTreasureLocation(), possibleMove[i]);
//                        memory.put(move, distance);
                    }
                    board.insertTile(j); //TODO insert to opposite location
                }
            }
        }
//        List<Double> distance = new ArrayList<>(memory.keySet());
//        Collections.sort(distance);
        
        board.insertTile();
        

    }

}
