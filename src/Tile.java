
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class contains the information on a tile in the game. It displays the
 * picture of the tile and allows other objects to access the information of the
 * tile.
 *
 * @author RB_Johnson
 */
public class Tile extends Pane {

    public static final int T_TYPE = 0,
            L_TYPE = 1,
            I_TYPE = 2;

    public static final int NORTH = 0,
            EAST = 1,
            SOUTH = 2,
            WEST = 3;

    
    private ImageView tileIcon;
    private IntegerProperty xCord = new SimpleIntegerProperty();
    private IntegerProperty yCord = new SimpleIntegerProperty();
    private int type;
    private int rotation = 0;
    private boolean containsTreasure = false;
    private Card treasure;
    private boolean fixed;
    private Player[] playersOnTile = new Player[2];
    private Image tileImage;
    private Image highlightImg;

    /**
     * Sets up the tile at a loacation on the board with an image based on its
     * type rotated to a certain facing. It also linkes the tile to the board so
     * the tile displays.
     *
     * @param b The board for the tile to display in.
     * @param t The type of the tile selected from the constants defined in this
     * class.
     * @param x The x coordinate on the board for the tile to be located.
     * @param y The y coordinate on the board for the tile to be located.
     * @param r The amount of times the tile should be rotated 90degs CW.
     */
    public Tile(Pane b, int t, int x, int y, int r, boolean f) {
        type = t;
        rotation = r;
        fixed = f;

        String imageLocation = "test.jpg";
        String highlight = "test.jpg";

        if (type == T_TYPE) {
            highlight = "highlightT.png";
            if (fixed == true) {
                imageLocation = "test_T_fixed.png";

            } else {
                imageLocation = "test_T.png";
            }
        } else if (type == L_TYPE) {
            highlight = "highlightL.png";
            if (fixed == true) {
                imageLocation = "test_L_fixed.png";
            } else {
                imageLocation = "test_L.png";
            }
        } else if (type == I_TYPE) {
            highlight = "highlightI.png";
            imageLocation = "test_I.png";
        }

        this.translateXProperty().bind(xCord.multiply(Board.SQUARE_SIZE));
        this.translateYProperty().bind(yCord.multiply(Board.SQUARE_SIZE));

        tileImage = new Image(imageLocation);
        highlightImg = new Image(highlight);

        tileIcon = new ImageView(tileImage);
        tileIcon.setFitWidth(Board.SQUARE_SIZE);
        tileIcon.setPreserveRatio(true);
        tileIcon.setSmooth(true);
        tileIcon.setCache(true);
        tileIcon.setX(0);
        tileIcon.setY(0);

        tileIcon.setRotate(90 * rotation);

        this.getChildren().add(tileIcon);

        b.getChildren().add(this);

        moveToLocation(x, y);

    }

    /**
     * Overloaded constructor to work with Point2D.
     */
    public Tile(Pane b, int t, int x, int y, int r) {
        this(b, t, x, y, r, false);
    }

    /**
     * Overloaded constructor to work with Point2D.
     */
    public Tile(Pane b, int t, Point2D cord, int r) {
        this(b, t, cord.getX(), cord.getY(), r);
    }

    /**
     * Rotates the tile 90degs clockwise.
     */
    public void rotateCW() {
        rotation = (rotation + 1) % 4;
        tileIcon.setRotate(90 * rotation);
    }

    /**
     * Rotates the tile 90degs counter-clockwise.
     */
    public void rotateCCW() {
        rotateCW();
        rotateCW();
        rotateCW();
    }

    /**
     * Move the shape to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the square
     * remains fixed at the specified location.
     *
     * @param x x-coordinate on the tetris board
     * @param y y-coordinate on the tetris board
     */
    public void moveToLocation(int x, int y) {
        xCord.unbind();
        yCord.unbind();
        xCord.set(x);
        yCord.set(y);
    }

    /**
     * Move the shape to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the square
     * remains fixed at the specified location.
     *
     * @param loc The location to move the tile to.
     */
    public void moveToLocation(Point2D loc) {
        moveToLocation(loc.getX(), loc.getY());
    }

    /**
     * Checks whether the tile has an edge connecting it to the adjacent tile in
     * the direction given.
     *
     * @param direction The direction to check if an edge exists. In the form of
     * a direction constant from Tile.
     */
    public boolean isConnectedInDirection(int direction) {
        if (direction == NORTH) {
            if (type == T_TYPE && (rotation == 0 || rotation == 2 || rotation == 3)) {
                return true;
            } else if (type == L_TYPE && (rotation == 0 || rotation == 3)) {
                return true;
            } else if (type == I_TYPE && (rotation == 0 || rotation == 2)) {
                return true;
            }
        } else if (direction == EAST) {
            if (type == T_TYPE && (rotation == 0 || rotation == 1 || rotation == 3)) {
                return true;
            } else if (type == L_TYPE && (rotation == 0 || rotation == 1)) {
                return true;
            } else if (type == I_TYPE && (rotation == 1 || rotation == 3)) {
                return true;
            }
        } else if (direction == SOUTH) {
            if (type == T_TYPE && (rotation == 0 || rotation == 1 || rotation == 2)) {
                return true;
            } else if (type == L_TYPE && (rotation == 1 || rotation == 2)) {
                return true;
            } else if (type == I_TYPE && (rotation == 0 || rotation == 2)) {
                return true;
            }
        } else if (direction == WEST) {
            if (type == T_TYPE && (rotation == 1 || rotation == 2 || rotation == 3)) {
                return true;
            } else if (type == L_TYPE && (rotation == 2 || rotation == 3)) {
                return true;
            } else if (type == I_TYPE && (rotation == 1 || rotation == 3)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the current x coordinate of the tile on the board.
     */
    public int getXLocation() {
        return xCord.get();
    }

    /**
     * Returns the current y coordinate of the tile on the board.
     */
    public int getYLocation() {
        return yCord.get();
    }

    /**
     * Returns the current location of the tile as a point 2d.
     */
    public Point2D getLocation() {
        return new Point2D(xCord.get(), yCord.get());
    }

    /**
     * Returns the type of the tile.
     */
    public int getType() {
        return type;
    }

    /**
     * Adds the tile to a board to be displayed.
     *
     * @param b The board that tile should display in.
     */
    public void addToDrawing(Pane b) {
        b.getChildren().add(this);
    }

    /**
     * Removes the tile from being displayed as part of the board given.
     *
     * @param b The board that tile should no longer display on.
     */
    public void removeFromDrawing(Pane b) {
        b.getChildren().remove(this);
    }

    /**
     * Sets the treasure in the tile.
     *
     * @param c The card that holds the treasure to put in this tile.
     */
    public void setTreasure(Card c) {
        treasure = c;
        containsTreasure = true;
        this.getChildren().add(treasure.getTreasureImage());
    }

    /**
     * Returns whether the tile has a treasure in it.
     */
    public boolean containsTreasure() {
        return containsTreasure;
    }

    /**
     * Returns the card that holds the treasure found in the tile.
     */
    public Card getTreasure() {
        return treasure;
    }

    public void removeTreasure() {
        this.getChildren().remove(treasure.getTreasureImage());
        containsTreasure = false;
        treasure = null;
    }

    /**
     * Returns whether the tile can move or not.
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Indicates that a player has ended its move on this tile
     */
    public void addPlayer(Player p) {
        if (playersOnTile[0] == null && playersOnTile[1] != p) {
            playersOnTile[0] = p;
            this.getChildren().add(p.getDisplay());
            p.getDisplay().setX(Board.SQUARE_SIZE / 4 - 10);
            p.getDisplay().setY(Board.SQUARE_SIZE / 4 - 10);
        } else if (playersOnTile[1] == null && playersOnTile[0] != p) {
            playersOnTile[1] = p;
            this.getChildren().add(p.getDisplay());
            p.getDisplay().setX(3 * Board.SQUARE_SIZE / 4 - 10);
            p.getDisplay().setY(3 * Board.SQUARE_SIZE / 4 - 10);
        }
    }

    /**
     * Indicates that a player has moved off this tile.
     */
    public void removePlayer(Player p) {
        if (playersOnTile[0] == p) {
            playersOnTile[0] = null;
        } else if (playersOnTile[1] == p) {
            playersOnTile[1] = null;
        }

        this.getChildren().remove(p.getDisplay());
    }

    /**
     * Answers whether there is a player on this tile
     */
    public boolean isPlayerOnTile() {
        if (playersOnTile[0] != null || playersOnTile[1] != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns an array of all the players on this tile.
     */
    public Player[] getPlayersOnTile() {
        return playersOnTile;
    }

    /**
     * Returns info about the tile in the form of a string.
     */
    @Override
    public String toString() {
        String output = "Tile Info: ";
        if (type == T_TYPE) {
            output += "Type: T, ";
        } else if (type == L_TYPE) {
            output += "Type: L, ";
        } else if (type == I_TYPE) {
            output += "Type: I, ";
        } else {
            output += "Type: Unknown, ";
        }

        output += "X Cord: " + getXLocation() + ", ";
        output += "Y Cord: " + getYLocation() + ", ";
        output += "Rotation: " + rotation + ". ";

        return output;
    }

    public void highlight() {
        tileIcon.setImage(highlightImg);
    }

    public void unHighlight() {
        tileIcon.setImage(tileImage);
    }

}
