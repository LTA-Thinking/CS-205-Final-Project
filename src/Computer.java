
import java.util.ArrayList;
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
public class Computer extends Player {

    private Board board;
    private Point2D[] allMoveLoc;
    private Labyrinth labyrinth;

    /**
     * constructor
     *
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

    /**
     * computer make a move. 
     * computer will test all possible moves, and pick the move that will bring 
     * itself to his current treasure or the closest point 
     */
    @Override
    public void takeTurn() 
    {
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
                        else if (Math.random() > 0.98) 
                        {
                            distance = 1;
                            tile = board.getTile(posLoc.get(k));
                            insertPoint = possibleMove[i];
                        }
                    }
                    board.insertTile(board.oppositeLoc(possibleMove[i]));
                    labyrinth.changeExtraTile();
                }
            }
        }
        
        board.insertTile(insertPoint);
        labyrinth.changeExtraTile();
        super.setLocation(tile.getLocation());
        
        if (distance == 0) 
        {
            System.out.println("got it");
            tile.removeTreasure();
            setNextTreasure();
        }
    }
}
