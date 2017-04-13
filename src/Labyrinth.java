import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * This class starts the program, creates the board and players, and handles top level actions.
 * 
 * @author RB_Johnson
 */
public class Labyrinth extends Application
{
	
	private Board board;
	//private Player playerOne, playerTwo, currentPlayer;
	private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
	private Stage primaryStage;
	private Scene mainGameScene, introScene, promptScene;
	private Tile leftOverTile;
	
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
		
		VBox boardHolder = new VBox();
		
		HBox topButtons = new HBox();
		
		Image arrowButton = new Image("arrow_button.png");
		
		ImageView topLeft = new ImageView(arrowButton);
		ImageView topCenter = new ImageView(arrowButton);
		ImageView topRight = new ImageView(arrowButton);
		
		/*
		Button topLeft = new Button("",new ImageView(arrowButton));
		topLeft.setMaxSize(Board.SQUARE_SIZE,Board.SQUARE_SIZE);
		Button topCenter = new Button("",new ImageView(arrowButton));
		topCenter.setMaxSize(Board.SQUARE_SIZE,Board.SQUARE_SIZE);
		Button topRight = new Button("",new ImageView(arrowButton));
		topRight.setMaxSize(Board.SQUARE_SIZE,Board.SQUARE_SIZE);
		*/
		
		topLeft.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				//currentPlayer.insertTile(new Point2D(1,0));
				System.out.println("Top Left Button Pressed");
			}
		});
		
		topCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				//currentPlayer.insertTile(new Point2D(3,0));
				System.out.println("Top Center Button Pressed");
			}
		});
		
		topRight.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				//currentPlayer.insertTile(new Point2D(5,0));
				System.out.println("Top Right Button Pressed");
			}
		});
		
		topButtons.setSpacing(Board.SQUARE_SIZE);
		topButtons.setPrefHeight(Board.SQUARE_SIZE);
		topButtons.getChildren().add(topLeft);
		topButtons.getChildren().add(topCenter);
		topButtons.getChildren().add(topRight);
		
		boardHolder.getChildren().add(topButtons);
		boardHolder.getChildren().add(board);
        mainPane.setCenter(boardHolder);
		
		HBox rotateButtons = new HBox();
		
		Button rotateCCW = new Button("L");
		Button rotateCW = new Button("R");
		
		rotateButtons.getChildren().add(rotateCCW);
		rotateButtons.getChildren().add(rotateCW);
		
		displayPlayerOneTreasure = new Label();//new ImageView(playerOne.getCurrentTreasure().getTreasureNumber()));
		displayPlayerTwoTreasure = new Label();//new ImageView(playerOne.getCurrentTreasure().getTreasureNumber()));
		
		VBox sidePanel = new VBox();
		sidePanel.setPadding(new Insets(10));
		sidePanel.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.MEDIUM)));
		
		leftOverTile = board.getExtraTile();
		leftOverTile.addToDrawing(sidePanel);
				
		rotateCCW.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				leftOverTile.rotateCCW();
			}
		});
		
		rotateCW.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				leftOverTile.rotateCW();
			}
		});
		
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
		Card [] deck = new Card [24];
		Tile [][] tiles = new Tile[7][7];//board.getTiles();
		int numCardsMade = 0, numCornerCardsMade = 0, numCornerTilesSeen = 0;
		
		for(int i=0;i<tiles.length;i++)
		{
			for(int k=0;k<tiles[0].length;k++)
			{
				if(tiles[i][k].getType()==Tile.T_TYPE)
				{
					deck[numCardsMade] = new Card(numCardsMade,tiles[i][k]);
					tiles[i][k].setTreasure(deck[numCardsMade]);
					numCardsMade++;
				}
				else if(tiles[i][k].getType()==Tile.L_TYPE && ((i!=0 && k!=0) || (i!=tiles.length-1 && k!=tiles[0].length-1) || (i!=tiles.length-1 && k!=0) || (i!=0 && k!=tiles[0].length-1)))
				{
					if(numCornerCardsMade<6 && (Math.random()*6>1.0/16.0 || 6-numCornerCardsMade>=16-numCornerTilesSeen))
					{
						deck[numCardsMade] = new Card(numCardsMade,tiles[i][k]);
						tiles[i][k].setTreasure(deck[numCardsMade]);
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
		
		int cardDelt;
		Card [] playerOneCards = new Card[12];
		Card [] playerTwoCards = new Card[12];
		
		for(int slotFilled=0;slotFilled<12;slotFilled++)
		{
			while(true)
			{
				cardDelt = (int)(Math.random()*24);
				if(deck[cardDelt]!=null)
				{
					playerOneCards[slotFilled] = deck[cardDelt];
					deck[cardDelt] = null;
					break;
				}
			}
			
			while(true)
			{
				cardDelt = (int)(Math.random()*24);
				if(deck[cardDelt]!=null)
				{
					playerTwoCards[slotFilled] = deck[cardDelt];
					deck[cardDelt] = null;
					break;
				}
			}
		}
		
		//playerOne.setTreasures(new ArrayList<Card>(playerOneCards));
		//playerTwo.setTreasures(new ArrayList<Card>(playerTwoCards));
		
	}
	
	public static void main(String [] args)
	{
		Application.launch(args);
	}
}