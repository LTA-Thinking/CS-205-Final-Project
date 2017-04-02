public class Labyrinth extends Application
{
	
	private Board board;
	private Player playerOne, playerTwo;
	private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
	
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
		
		board = new Board(); // Fill in args
		
		if(twoHumans)
		{
			playerOne = new Human();
			playerTwo = new Human();
		}
		else
		{
			playerOne = new Computer();
			playerTwo = new Computer();
		}
		
		setupDisplay(primaryStage);
		
        takeTurn();
    }
	
	/**
	 * Sets up the display of the game by placing all the major graphical elements.
	 * 
	 * @param primaryStage
	 */
	public void setupDisplay(Stage primaryStage)
	{
		BorderPane pane = new BorderPane();
        pane.setCenter(board);
		
		HBox rotateButtons = new HBox();
		
		Button rotateLeft = new Button();
		Button rotateRight = new Button();
		
		rotateButtons.add(rotateLeft);
		rotateButtons.add(rotateRight);
		
		VBox sidePanel = new VBox();
		sidePanel.add(board.getUnusedTile());
		sidePanel.add(rotateButtons);
		sidePanel.add(new Label("Player One:");
		sidePanel.add(displayPlayerOneTreasure);
		sidePanel.add(new Label("Player Two:");
		sidePanel.add(displayPlayerTwoTreasure);
		
		pane.setEast(sidePanel);
		
		Scene scene = new Scene(pane);
		
		primaryStage.setScene(scene);

        primaryStage.show();
	}
	
	/**
	 * Tells the players to take their turns.
	 * Handles conditions for if the game ends.
	 */
	public void takeTurn()
	{
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
	}

	public static void main(String [] args)
	{
		Application.launch(args);
	}
}