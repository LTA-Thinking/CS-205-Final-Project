
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
    private Labyrinth labyrinth;

    /**
     * constructor
     */
    public Computer(Board board, Labyrinth labyrinth) {
        super(new Point2D(0, 0), board);
        this.board = board;
        this.labyrinth = labyrinth;
    }

    @Override
    public void takeTurn() {
        System.out.println("treasure ; " + super.getCurrentTreasure());
        board.insertTile(new Point2D(0, 1));
        Point2D lastMove = new Point2D(0,1);//board.getLastMove();
        double distance = 999; 
//        Tile tile;
        Point2D insertPoint = new Point2D(0,1);
        Point2D[] possibleMove = {new Point2D(0, 1), new Point2D(0, 3), new Point2D(0, 5),
            new Point2D(1, 0), new Point2D(3, 0), new Point2D(5, 0), new Point2D(6, 1), 
            new Point2D(6, 3), new Point2D(6, 5), new Point2D(1, 6), new Point2D(3, 6), 
            new Point2D(5, 6)};
        for (int i = 0; i < possibleMove.length; ++i) {
            if (!super.oppositeLoc(lastMove).equals(possibleMove[i])) {
                for (int j = 0; j < 3; ++j) {
                    board.getExtraTile().rotateCW();
                    board.insertTile(possibleMove[i]);
                    labyrinth.changeExtraTile();
                    ArrayList<Point2D> posLoc = super.allPossibleLoc();
                    for (int k = 0; k < posLoc.size(); ++k) {
                        if(posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation()) < distance){
                            distance = posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation());
//                            tile = board.getTile(posLoc.get(k));
                            insertPoint = possibleMove[i];
                        }
                    }
                    board.insertTile(super.oppositeLoc(insertPoint));
                }
                
            }
        }
        
        board.insertTile(insertPoint); //TODO insert to opposite location
//        if(distance == 0){
//            setNextTreasure();
//        }

    }

}
