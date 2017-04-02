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
	
	/**
	 * Creates the card and gives it all its values. Once made these values can't be changed.
	 *
	 * @param number represents the number of the treasure
	 * @param imageLocation gives the file path for the image representing this treasure
	 */
	public Card(int number,String imageLocation)
	{
		treasureNumber = number;
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
}