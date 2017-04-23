import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * A class representing the cards used in the Labyrinth game. 
 * Holds information relating to the treasure, but does no actual work.
 *
 * @author RB_Johnson
 */
public class Card
{
	private int treasureNumber;
	private Pane treasureImage;
	private Rectangle colorDisplay;
	private Tile treasureLocation;
	private final int treasureSize = 30;
	
	/**
	 * Creates the card and gives it all its values. Once made these values can't be changed.
	 *
	 * @param number represents the number of the treasure
	 * @param location the tile where the treasure is located
	 */
	public Card(int number,Tile location)
	{
		treasureNumber = number;
		treasureLocation = location;
		
		treasureImage = new Pane();
		treasureImage.setTranslateX((Board.SQUARE_SIZE-treasureSize)/2);
		treasureImage.setTranslateY((Board.SQUARE_SIZE-treasureSize)/2);
		
		colorDisplay = new Rectangle(treasureSize,treasureSize,Color.YELLOW);
		treasureImage.getChildren().add(colorDisplay);
		
		Text moneySymbol = new Text("$$$");
		moneySymbol.setX(5);
		moneySymbol.setY((treasureSize+moneySymbol.getFont().getSize())/2);
		treasureImage.getChildren().add(moneySymbol);
	}
	
	/**
	 * Returns the card's treasure's number.
	 */
	public int getTreasureNumber()
	{
		return treasureNumber;
	}
	
	/** 
	 * Returns the card's treasure's image.
	 */
	public Node getTreasureImage()
	{
		return treasureImage;
	}
	
	/** 
	 * Returns the location of the tile where the card's treasure is located.
	 */
	public Point2D getTreasureLocation()
	{
		return treasureLocation.getLocation();
	}
	
	
	/**
	 * Changes the color the treasure displays as.
	 */
	public void setDisplayColor(Color c)
	{
		colorDisplay.setFill(c);
	}
}