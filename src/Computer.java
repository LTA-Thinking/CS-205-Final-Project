
import java.util.*;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author neal
 */
public class Computer extends Player 
{
    private Board board;
    private Point2D[] allMoveLoc;
    private Labyrinth labyrinth;

    /**
     * constructor
     * @param board
     * @param labyrinth
     * @param color
     */
    public Computer(Board board, Labyrinth labyrinth, Color color) 
    {
        super(board.getTile(new Point2D(0, 0)), board, color);
        this.board = board;
        this.labyrinth = labyrinth;
    }

    @Override
    public void takeTurn() 
    {
//        System.out.println("treasure ; " + super.getCurrentTreasure().getTreasureLocation().getX() + "   " + super.getCurrentTreasure().getTreasureLocation().getY());

        Point2D lastMove = board.getLastMove();
        double distance = 999;
        Tile tile = board.getTile(new Point2D(0, 0));
        Point2D insertPoint = new Point2D(0, 1);
        Point2D[] possibleMove = {new Point2D(0, 1), new Point2D(0, 3), new Point2D(0, 5),
            new Point2D(1, 0), new Point2D(3, 0), new Point2D(5, 0), new Point2D(6, 1),
            new Point2D(6, 3), new Point2D(6, 5), new Point2D(1, 6), new Point2D(3, 6),
            new Point2D(5, 6)};
        for (int i = 0; i < possibleMove.length; ++i) 
        {
            if (!board.oppositeLoc(lastMove).equals(possibleMove[i])) 
            {
                for (int j = 0; j < 3; ++j) 
                {
                    board.insertTile(possibleMove[i]);
                    labyrinth.changeExtraTile();
                    
                    ArrayList<Point2D> posLoc = super.allPossibleLoc();
                    for (int k = 0; k < posLoc.size(); ++k) 
                    {
                        if (posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation()) < distance 
                                && board.getExtraTile().getTreasure() != super.getCurrentTreasure()) 
                        {
                            distance = posLoc.get(k).distance(super.getCurrentTreasure().getTreasureLocation());
                            tile = board.getTile(posLoc.get(k));
                            insertPoint = possibleMove[i];
                        }
                    }
                    board.insertTile(board.oppositeLoc(possibleMove[i]));
                    labyrinth.changeExtraTile();
                }
            }
        }
//        System.out.println("insert loc: " + insertPoint.getX() + "  " + insertPoint.getY());
        board.insertTile(insertPoint);
        labyrinth.changeExtraTile();
        super.setLocation(tile.getLocation());
//        System.out.println("current loc: " + super.getLocation().getX() + "  " + super.getLocation().getY());
//        System.out.println("treasure ; " + super.getCurrentTreasure().getTreasureLocation().getX() + "   " + super.getCurrentTreasure().getTreasureLocation().getY());
//        System.out.println("distance: " + distance);
        if (distance == 0) {
            System.out.println("got it");
//            System.out.println("number of treasure: " + this.getTreasures().size());
//            System.out.println("treasure at " + board.getTile(new Point2D(2,0)).getTreasure());
            tile.removeTreasure();
            setNextTreasure();
        }
    }
}
