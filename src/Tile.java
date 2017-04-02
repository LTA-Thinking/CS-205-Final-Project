import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

/**
 * This class contains the information on a tile in the game. 
 * It displays the picture of the tile and allows other objects to access the information of the tile. 
 *
 * @author RB_Johnson
 */
public class Tile
{
	public static final int T_TYPE = 0,
								L_TYPE = 1,
								I_TYPE = 2;
								
	private ImageView tileIcon;
	private IntegerProperty xCord = new SimpleIntegerProperty();
	private IntegerProperty yCord = new SimpleIntegerProperty();
	private int type;
	private int rotation = 0;
	
	/**
	 * Sets up the tile at a loacation on the board with an image based on its type rotated to a certain facing.
	 * It also linkes the tile to the board so the tile displays.
	 *
	 * @param b The board for the tile to display in.
	 * @param t The type of the tile selected from the constants defined in this class.
	 * @param x The x coordinate on the board for the tile to be located.
	 * @param y The y coordinate on the board for the tile to be located.
	 * @param r The amount of times the tile should be rotated 90degs CW.
	 */
	public Tile(Board b,int t,int x,int y,int r)
	{
		type = t;
		rotation = r;
		
		// TODO: pick imageLocation based on type
		
		tileIcon = new ImageView(imageLocation);
		tileIcon.setFitWidth(Board.SQUARE_SIZE);
        tileIcon.setPreserveRatio(true);
        tileIcon.setSmooth(true);
        tileIcon.setCache(true);
		tileIcon.xProperty().bind(xCord.multiply(Board.SQUARE_SIZE));
		tileIcon.yProperty().bind(yCord.multiply(Board.SQUARE_SIZE));
		tileIcon.setRotate(90*rotation);
		
		b.getChildren().add(tileIcon);
		
		moveToLocation(x,y);
	}
	
	/**
	 * Rotates the tile 90degs clockwise.
	 */
	public void rotateCW()
	{
		rotation = (rotation+1)%4;
		tileIcon.setRotate(90*rotation);
	}
	
	/**
	 * Rotates the tile 90degs counter-clockwise.
	 */
	public void rotateCCW()
	{
		rotateCW();
		rotateCW();
		rotateCW();
	}
	
	/**
     * Move the shape to the specified x and y board coordinates. Undoes any
     * binding currently in effect on the coordinates, so that the square
     * remains fixed at the specified location.
     * @param x x-coordinate on the tetris board
     * @param y y-coordinate on the tetris board
     */
    public void moveToLocation(int x, int y) 
    {
        xCord.unbind();
        yCord.unbind();
        xCord.set(x);
        yCord.set(y);
    }
	
	/**
	 * Returns the current x coordinate of the tile on the board.
	 */
	public void getXLocation()
	{
		return xCord.get();
	}
	
	/**
	 * Returns the current y coordinate of the tile on the board.
	 */
	public void getYLocation()
	{
		return yCord.get();
	}
	
	/**
	 * Removes the tile from being displayed as part of the board given.
	 *
	 * @param b The board that tile should no longer display on.
	 */
	public void removeFromDrawing(Board b)
	{
		b.getChildren().remove(tileIcon);
	}
}