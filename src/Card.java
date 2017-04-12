import javafx.scene.image.Image;

/**
 * A class representing the cards used in the Labyrinth game. 
 * Holds information relating to the treasure, but does no actual work.
 *
 * @author RB_Johnson
 */
public class Card
{
	private int treasureNumber;
	private Image treasureImage;
	private Tile treasureLocation;
	
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
		
		String imageLocation = "";
		treasureImage = new Image(imageLocation);
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
	public Image getTreasureImage()
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
	
}