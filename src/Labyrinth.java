import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class starts the program, creates the board and players, and handles top level actions.
 * 
 * @author RB_Johnson
 */
public class Labyrinth extends Application
{
	
	private Board board;
	//private Player playerOne, playerTwo;
	private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
	private Stage primaryStage;
	private Scene mainGameScene, introScene, promptScene;
	
	/**
     * Starts the application, creates the players, and creates the board
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {  
		boolean twoHumans = false;
		
		board = new Board(100,7,7); // Fill in args
		/*
		if(twoHumans)
		{
			playerOne = new Human();
			playerTwo = new Human();
		}
		else
		{
			playerOne = new Human();
			playerTwo = new Computer();
		}
		
		dealCards();
		*/
		setupDisplay(primaryStage);
		
        //takeTurn();
    }
	
	/**
	 * Sets up the display of the game by placing all the major graphical elements.
	 * 
	 * @param primaryStage
	 */
	public void setupDisplay(Stage primaryStage)
	{
		//***********Main Game Scene**************
		
		BorderPane mainPane = new BorderPane();
        mainPane.setCenter(board);
		
		HBox rotateButtons = new HBox();
		
		Button rotateLeft = new Button("L");
		Button rotateRight = new Button("R");
		
		rotateButtons.getChildren().add(rotateLeft);
		rotateButtons.getChildren().add(rotateRight);
		
		displayPlayerOneTreasure = new Label("TEST");
		displayPlayerTwoTreasure = new Label("TEST");
		
		VBox sidePanel = new VBox();
		//sidePanel.getChildren().add(board.getUnusedTile());
		new Tile(sidePanel,0,0,0,0);
		
		sidePanel.getChildren().add(rotateButtons);
		sidePanel.getChildren().add(new Label("Player One:"));
		sidePanel.getChildren().add(displayPlayerOneTreasure);
		sidePanel.getChildren().add(new Label("Player Two:"));
		sidePanel.getChildren().add(displayPlayerTwoTreasure);
		
		mainPane.setRight(sidePanel);
		
		mainGameScene = new Scene(mainPane);
		
		//**************************************
		
		//****************Intro scene**************
		/*
		BorderPane introPane = new BorderPane();
		String introBackgroundUrl = ""
		introPane.setBackgroune(new Background(new BackgroundImage(new Image(introBackgroundUrl),new BackgroundRepeat
		*/
		//***************************************
		
		primaryStage.setScene(mainGameScene);

        primaryStage.show();
	}
	
	/**
	 * Tells the players to take their turns.
	 * Handles conditions for if the game ends.
	 */
	public void takeTurn()
	{
		/*
		while(true)
		{
			boolean playerOneWins = playerOne.takeTurn();
			
			if(playerOneWins)
			{
				String[] options = {"Yes","No"};
				String answer = prompt("Congratulations, you win! Do you want to play again?",options);
				if(answer.equals("Yes"))
					restart();
				else
					System.exit(0);
			}
			
			boolean playerTwoWins = playerTwo.takeTurn();
			
			if(playerTwoWins)
			{
				String[] options = {"Yes","No"};
				String answer = prompt("Sorry you lose. Do you want to play again?",options);
				if(answer.equals("Yes"))
					restart();
				else
					System.exit(0);
			}
		}
		*/
	}

	/**
	 * Deals the cards to the players.
	 */
	public void dealCards()
	{
		/*
		Card [] deck = new Card [16];
		Tile [][] tiles = board.getTiles();
		int numCardsMade = 0, numCornerCardsMade = 0, numCornerTilesSeen = 0;
		
		for(int i=0;i<tiles.length;i++)
		{
			for(int k=0;k<tiles[0].length;k++)
			{
				if(tiles[i][k].getType()==Tile.T_TYPE)
				{
					deck[numCardsMade] = new Card(numCardsMade,tiles[i][k]);
					tiles[i][k].addTreasure(deck[numCardsMade]);
					numCardsMade++;
				}
				else if(tiles[i][k].getType()==Tile.L_TYPE && ((i!=0 && k!=0) || (i!=tiles.length-1 && k!=tiles[0].length-1) || (i!=tiles.length-1 && k!=0) || (i!=0 && k!=tiles[0].length-1)))
				{
					if(numCornerCardsMade<6 && (Math.random()*6>1.0/16.0 || 6-numCornerCardsMade>=16-numCornerTilesSeen))
					{
						deck[numCardsMade] = new Card(numCardsMade,tiles[i][k]);
						tiles[i][k].addTreasure(deck[numCardsMade]);
						numCardsMade++;
						numCornerCardsMade++;
					}
					numCornerTilesSeen++;
				}
				
				if(numCardsMade>=24)
					break;
			}
			
			if(numCardsMade>=24)
				break;
		}
		*/
		
	}
	
	public static void main(String [] args)
	{
		Application.launch(args);
	}
}