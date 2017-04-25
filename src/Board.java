
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;

/**
 * This class contains the information for the board of the tile game. It
 * creates a board based on the tile class and fills it in according to the game
 * rules
 *
 * @author Ivan Spizizen
 */
public class Board extends Pane {

    /**
     * @param SQUARE_SIZE is the size of each tile
     * @param X_DIM_SQUARES is the amount of x cords on grid
     * @param Y_DIM_SQUARES is the amount of y cords on grid
     * @param Tile grid [][] is a 2 dimensional array that holds tiles
     * @param counter is used to fill the board with random tiles from the
     * shuffled array
     * @param Tile extraTile is the remaining tile when the board is created
     * @param Tile t1 & t2 are generic tiles used for the canMove method
     * @param startX & startY are used in the highlightMoves method to avoid an
     * endless loop going back and forth from the starting tile location to the
     * first direction
     */
    public static int SQUARE_SIZE;
    public final int X_DIM_SQUARES;
    public final int Y_DIM_SQUARES;
    protected Tile grid[][];
    private int counter = 0;
    private Tile extraTile;
    private Tile t1;
    private Tile t2;
    private int startX;
    private int startY;
    private Point2D lastMove = new Point2D(0, 0);
    private boolean highlight;
    private boolean helperMode = false;

    /**
     * @return the lastMove
     */
    public Point2D getLastMove() {
        return lastMove;
    }

    /**
     * @param lastMove the lastMove to set
     */
    public void setLastMove(Point2D lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Sets up the board filling it with tiles based on X/Y_DIM_SQUARES. First
     * it fills in the 16 pieces of the board that do not move Then, it creates
     * a randomized array to fill in the remaining 33 pieces
     *
     * @param s is the size of each square
     * @param x is the amount of x coordinates
     * @param y is the amount of y coordines
     */
    public Board(int s, int x, int y) {
        SQUARE_SIZE = s;
        X_DIM_SQUARES = x;
        Y_DIM_SQUARES = y;

        grid = new Tile[X_DIM_SQUARES][Y_DIM_SQUARES];

        this.setPrefHeight(Y_DIM_SQUARES * SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES * SQUARE_SIZE);

        grid[0][0] = new Tile(this, 1, 0, 0, 1, true);
        grid[2][0] = new Tile(this, 0, 2, 0, 1, true);
        grid[4][0] = new Tile(this, 0, 4, 0, 1, true);
        grid[6][0] = new Tile(this, 1, 6, 0, 2, true);
        grid[0][2] = new Tile(this, 0, 0, 2, 0, true);
        grid[2][2] = new Tile(this, 0, 2, 2, 0, true);
        grid[4][2] = new Tile(this, 0, 4, 2, 1, true);
        grid[6][2] = new Tile(this, 0, 6, 2, 2, true);
        grid[0][4] = new Tile(this, 0, 0, 4, 0, true);
        grid[2][4] = new Tile(this, 0, 2, 4, 3, true);
        grid[4][4] = new Tile(this, 0, 4, 4, 2, true);
        grid[6][4] = new Tile(this, 0, 6, 4, 2, true);
        grid[0][6] = new Tile(this, 1, 0, 6, 0, true);
        grid[2][6] = new Tile(this, 0, 2, 6, 3, true);
        grid[4][6] = new Tile(this, 0, 4, 6, 3, true);
        grid[6][6] = new Tile(this, 1, 6, 6, 3, true);

        int[] tilearrs = new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        List<Integer> tList = new ArrayList<Integer>();
        for (int index = 0; index < tilearrs.length; index++) {
            tList.add(tilearrs[index]);
        }
        Collections.shuffle(tList);

        for (int row = 0; row < X_DIM_SQUARES; row += 2) {
            for (int col = 1; col < Y_DIM_SQUARES; col += 2) {
                Random rand = new Random();
                int n = rand.nextInt(3) + 1;

                grid[col][row] = new Tile(this, tList.get(counter), col, row, n);
                counter++;

            }
        }
        for (int row = 1; row < X_DIM_SQUARES; row += 2) {
            for (int col = 0; col < Y_DIM_SQUARES; col++) {
                // create new GameSquare object for this column and row.  Pass in
                // the panel and action listener so the button can be linked up!
                Random rand = new Random();
                int n = rand.nextInt(3) + 1;
                grid[col][row] = new Tile(this, tList.get(counter), col, row, n);
                counter++;
            }
        }
        //gets the extra tile
        Random rand = new Random();
        int n = rand.nextInt(3) + 1;
        Tile extraTile = new Tile(this, tList.get(33), -1, -1, n);
        setExtraTile(extraTile);
    }

    /**
     * Returns the tile.
     *
     * @param t the location of tile
     * @return tile at location t
     */
    public Tile getTile(Point2D t) {
        return grid[t.getX()][t.getY()];
    }

    public Point2D oppositeLoc(Point2D insert) {
        if (insert.getX() == 0) {
            return new Point2D(6, insert.getY());
        } else if (insert.getX() == 6) {
            return new Point2D(0, insert.getY());
        } else if (insert.getY() == 0) {
            return new Point2D(insert.getX(), 6);
        } else {
            return new Point2D(insert.getX(), 0);
        }
    }

    /**
     * gets the extra tile which is the leftover gamepiece
     *
     * @return extra tile
     */
    public Tile getExtraTile() {
        return extraTile;
    }

    /**
     * sets the extra tile which is the leftover gamepiece
     *
     * @param extraTile
     * @return
     */
    public Tile setExtraTile(Tile extraTile) {
        return this.extraTile = extraTile;
    }

    public void insertTile(Point2D insertLoc) {
        for(int i = 0; i < 7; ++i){
            for(int j = 0; j < 7; ++j){
                getTile(new Point2D(i,j)).unHighlight();
            }
        }
        setHighlight(false);
        
        //get tile
        int opposTileX = oppositeLoc(insertLoc).getX();
        int opposTileY = oppositeLoc(insertLoc).getY();
        
        if (getTile(new Point2D(opposTileX, opposTileY)).isPlayerOnTile()) {
                Player[] players = getTile(new Point2D(opposTileX, opposTileY)).getPlayersOnTile();
                if (players[0] != null) {
                    Player temp = players[0];
                    getTile(new Point2D(opposTileX, opposTileY)).removePlayer(temp);
                    getExtraTile().addPlayer(temp);
                }
                if (players[1] != null) {
                    Player temp = players[1];
                    getTile(new Point2D(opposTileX, opposTileY)).removePlayer(temp);
                    getExtraTile().addPlayer(temp);
                }
            }

        if (insertLoc.getX() == 0) {
            t1 = getExtraTile();
            setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
            grid[opposTileX][opposTileY].removeFromDrawing(this);
            for (int i = 6; i > 0; --i) {
                grid[i - 1][insertLoc.getY()].moveToLocation(i, insertLoc.getY());
                grid[i][insertLoc.getY()] = grid[i - 1][insertLoc.getY()];
            }
        } else if (insertLoc.getX() == 6) {
            t1 = getExtraTile();
            setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));

            for (int i = 0; i < 6; ++i) {
                grid[i + 1][insertLoc.getY()].moveToLocation(i, insertLoc.getY());
                grid[i][insertLoc.getY()] = grid[i + 1][insertLoc.getY()];
            }

        } else if (insertLoc.getY() == 0) {
            t1 = getExtraTile();
            setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
            for (int i = 6; i > 0; --i) {
                grid[insertLoc.getX()][i - 1].moveToLocation(insertLoc.getX(), i);
                grid[insertLoc.getX()][i] = grid[insertLoc.getX()][i - 1];
            }
        } else if (insertLoc.getY() == 6) {
            t1 = getExtraTile();
            setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
            grid[opposTileX][opposTileY].removeFromDrawing(this);

            for (int i = 0; i < 6; ++i) {
                grid[insertLoc.getX()][i + 1].moveToLocation(insertLoc.getX(), i);
                grid[insertLoc.getX()][i] = grid[insertLoc.getX()][i + 1];
            }
        }

        t1.addToDrawing(this);
        t1.moveToLocation(insertLoc.getX(), insertLoc.getY());
        grid[insertLoc.getX()][insertLoc.getY()] = t1;
        if (t1.isPlayerOnTile()) {
            Player[] players = t1.getPlayersOnTile();
            if (players[0] != null) {
                players[0].setLocation(insertLoc);
            }
            if (players[1] != null) {
                players[1].setLocation(insertLoc);
            }
        }
        setLastMove(insertLoc);
    }

    /**
     * Specifies if t1 can move to t2 in all 4 directs this is a one position
     * move, not a full path move EX: can tile 0,0 (top left corner) move to
     * tile 1,0
     *
     * @param start
     * @param dir
     * @return
     */
    public boolean canMove(Point2D start, int dir) {
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = 0;
        int y2 = 0;
        if (dir == 0) {
            x2 = x1;
            y2 = y1 - 1;
        }
        if (dir == 1) {
            x2 = x1 + 1;
            y2 = y1;
        }
        if (dir == 2) {
            x2 = x1;
            y2 = y1 + 1;
        }
        if (dir == 3) {
            x2 = x1 - 1;
            y2 = y1;
        }
        if (y2 < 0 || x2 < 0) {
            return false;
        } else {
            t1 = grid[x1][y1];
            t2 = grid[x2][y2];
            if (y1 - y2 == -1 && t1.isConnectedInDirection(2) && t2.isConnectedInDirection(0)) {
                return true;
            } else if (x1 - x2 == 1 && t1.isConnectedInDirection(3) && t2.isConnectedInDirection(1)) {
                return true;
            } else if (y1 - y2 == 1 && t1.isConnectedInDirection(0) && t2.isConnectedInDirection(2)) {
                return true;
            } else if (x1 - x2 == -1 && t1.isConnectedInDirection(1) && t2.isConnectedInDirection(3)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * returns grid
     */
    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * @return the highlight
     */
    public boolean isHighlight() {
        return highlight;
    }

    /**
     * @param highlight the highlight to set
     */
    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    /**
     * @return the helperMode
     */
    public boolean isHelperMode() {
        return helperMode;
    }

    /**
     * @param helperMode the helperMode to set
     */
    public void setHelperMode(boolean helperMode) {
        this.helperMode = helperMode;
    }

}
