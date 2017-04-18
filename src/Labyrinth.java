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
import javafx.stage.Popup;
import java.util.ArrayList;

/**
 * This class starts the program, creates the board and players, and handles top level actions.
 * 
 * @author RB_Johnson
 */
public class Labyrinth extends Application
{
	
	private Board board;
	private Player playerOne, playerTwo, currentPlayer;
	private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
	private Stage primaryStage;
	private Scene mainGameScene, introScene, promptScene;
	private Tile leftOverTile;
	private HBox extraTileHolder;
	
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
		setUpTileButtons();
		
		//****************************** FOR TESTING ***************************
		playerOne = new Computer(board, this);
		playerTwo = new Computer(board, this);
		/*
		if(twoHumans)
		{
			playerOne = new Human(board);
			playerTwo = new Human(board);
		}
		else
		{
			playerOne = new Human(board);
			playerTwo = new Computer(board);
		}
		*/
		dealCards();
		
		setupDisplay(primaryStage);
		
        //takeTurn();
		/*
		Popup popup = new Popup();
		popup.getContent().add(new Label("This is a popup"));
		popup.show();
		*/
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
		
		Image arrowButton = new Image("arrow_button.png");
		
		//*********************************************Top buttons**************************
		HBox topButtons = new HBox();
		
		ImageView topLeft = new ImageView(arrowButton);
		topLeft.setRotate(180);
		ImageView topCenter = new ImageView(arrowButton);
		topCenter.setRotate(180);
		ImageView topRight = new ImageView(arrowButton);
		topRight.setRotate(180);
		
		topLeft.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(1,0));
                                changeExtraTile();
				System.out.println("Top Left Button Pressed");
			}
		});
		
		topCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(3,0));
                                changeExtraTile();
				System.out.println("Top Center Button Pressed");
			}
		});
		
		topRight.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(5,0));
                                changeExtraTile();
				System.out.println("Top Right Button Pressed");
			}
		});
		
		topButtons.setSpacing(Board.SQUARE_SIZE);
		topButtons.setPadding(new Insets(0,Board.SQUARE_SIZE*2,0,Board.SQUARE_SIZE*2));
		topButtons.getChildren().add(topLeft);
		topButtons.getChildren().add(topCenter);
		topButtons.getChildren().add(topRight);
		
		
		//*************************************Bottom buttons**************************
		HBox bottomButtons = new HBox();
		
		ImageView bottomLeft = new ImageView(arrowButton);
		ImageView bottomCenter = new ImageView(arrowButton);
		ImageView bottomRight = new ImageView(arrowButton);
		
		bottomLeft.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(1,6));
                                changeExtraTile();
				System.out.println("Bottom Left Button Pressed");
			}
		});
		
		bottomCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(3,6));
                                changeExtraTile();
				System.out.println("Bottom Center Button Pressed");
			}
		});
		
		bottomRight.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(5,6));
                                changeExtraTile();
				System.out.println("Bottom Right Button Pressed");
			}
		});
		
		bottomButtons.setSpacing(Board.SQUARE_SIZE);
		bottomButtons.setPadding(new Insets(0,Board.SQUARE_SIZE*2,0,Board.SQUARE_SIZE*2));
		bottomButtons.getChildren().add(bottomLeft);
		bottomButtons.getChildren().add(bottomCenter);
		bottomButtons.getChildren().add(bottomRight);
		
		//*************************************Left hand buttons****************************
		VBox leftButtons = new VBox();
		
		ImageView leftTop = new ImageView(arrowButton);
		leftTop.setRotate(90);
		ImageView leftCenter = new ImageView(arrowButton);
		leftCenter.setRotate(90);
		ImageView leftBottom = new ImageView(arrowButton);
		leftBottom.setRotate(90);
		
		leftTop.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(0,1));
                changeExtraTile();
				System.out.println("Left Top Button Pressed");
			}
		});
		
		leftCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(0,3));
                changeExtraTile();
				System.out.println("Left Center Button Pressed");
			}
		});
		
		leftBottom.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(0,5));
                changeExtraTile();
				System.out.println("Left Bottom Button Pressed");
			}
		});
		
		leftButtons.setSpacing(Board.SQUARE_SIZE);
		leftButtons.setPadding(new Insets(Board.SQUARE_SIZE,0,Board.SQUARE_SIZE,0));
		leftButtons.getChildren().add(leftTop);
		leftButtons.getChildren().add(leftCenter);
		leftButtons.getChildren().add(leftBottom);
		
		//************************************Right hand buttons**************************
		VBox rightButtons = new VBox();
		
		ImageView rightTop = new ImageView(arrowButton);
		rightTop.setRotate(270);
		ImageView rightCenter = new ImageView(arrowButton);
		rightCenter.setRotate(270);
		ImageView rightBottom = new ImageView(arrowButton);
		rightBottom.setRotate(270);
		
		rightTop.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(6,1));
                changeExtraTile();
				System.out.println("Right Top Button Pressed");
			}
		});
		
		rightCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(6,3));
                changeExtraTile();
				System.out.println("Right Center Button Pressed");
			}
		});
		
		rightBottom.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				board.insertTile(new Point2D(6,5));
                changeExtraTile();
				System.out.println("Right Bottom Button Pressed");
			}
		});
		
		rightButtons.setSpacing(Board.SQUARE_SIZE);
		rightButtons.setPadding(new Insets(Board.SQUARE_SIZE,0,Board.SQUARE_SIZE,0));
		rightButtons.getChildren().add(rightTop);
		rightButtons.getChildren().add(rightCenter);
		rightButtons.getChildren().add(rightBottom);
		
		//**********************************************************************************
		
		HBox centerSection = new HBox();
		centerSection.getChildren().add(leftButtons);
		centerSection.getChildren().add(board);
		centerSection.getChildren().add(rightButtons);
		
		boardHolder.getChildren().add(topButtons);
		boardHolder.getChildren().add(centerSection);
		boardHolder.getChildren().add(bottomButtons);
        mainPane.setCenter(boardHolder);
		
		//******************************************** Side Panel **********************************
		HBox rotateButtons = new HBox();
		
		Button rotateCCW = new Button("L");
		Button rotateCW = new Button("R");
		
		rotateButtons.getChildren().add(rotateCCW);
		rotateButtons.getChildren().add(rotateCW);
		
		displayPlayerOneTreasure = new Label(playerOne.getCurrentTreasure().getTreasureLocation().toString());//new ImageView(playerOne.getCurrentTreasure().getTreasureNumber()));
		displayPlayerTwoTreasure = new Label();//new ImageView(playerOne.getCurrentTreasure().getTreasureNumber()));
		
		VBox sidePanel = new VBox();
		sidePanel.setPadding(new Insets(10));
		sidePanel.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.MEDIUM)));
		
		extraTileHolder = new HBox();
		changeExtraTile();
				
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
		
		Button runTest = new Button("RUN TEST");
		
		runTest.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				//**************************************** CALL TEST METHODS HERE ********************************
				takeTurn();
				
			}
		});
		
		sidePanel.getChildren().add(extraTileHolder);
		sidePanel.getChildren().add(rotateButtons);
		sidePanel.getChildren().add(new Label("Player One:"));
		sidePanel.getChildren().add(displayPlayerOneTreasure);
		sidePanel.getChildren().add(new Label("Player Two:"));
		sidePanel.getChildren().add(displayPlayerTwoTreasure);
		
		sidePanel.getChildren().add(runTest);
		
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
	
	public void setUpTileButtons()
	{
		Tile [][] grid = board.getGrid();
		
		for(int i=0;i<grid.length;i++)
		{
			for(int k=0;k<grid[0].length;k++)
			{
				Tile tile = grid[i][k];
				
				tile.setOnMouseClicked(new EventHandler<MouseEvent>()
				{
					@Override public void handle(MouseEvent e)
					{
						System.out.println("Tile " + tile.getXLocation() + ", " + tile.getYLocation() + " clicked");
					}
				});
			}
		}
		
		Tile tile = board.getExtraTile();
		
		tile.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				System.out.println("Tile " + tile.getXLocation() + ", " + tile.getYLocation() + " clicked");
			}
		});
	}
	
	/**
	 * Tells the players to take their turns.
	 * Handles conditions for if the game ends.
	 */
	public void takeTurn()
	{
		int turns = 0;
		
		while(true)
		{
			try
			{
				playerOne.takeTurn();
				currentPlayer = playerOne;
				
				while(playerOne.isCurrentPlayer()){};
				
				if(playerOne.checkWin())
				{
					System.out.println("\nPlayer One Wins, Turns: " + turns +" \n");
					break;
					/*
					String[] options = {"Yes","No"};
					String answer = prompt("Congratulations, you win! Do you want to play again?",options);
					if(answer.equals("Yes"))
						restart();
					else
						System.exit(0);
					*/
				}
				
				
				//Thread.sleep(2000);
				
				playerTwo.takeTurn();
				currentPlayer = playerTwo;
				
				while(playerTwo.isCurrentPlayer()){};
				
				if(playerTwo.checkWin())
				{
					System.out.println("\nPlayer Two Wins, Turns: " + turns +" \n");
					break;
					/*
					String[] options = {"Yes","No"};
					String answer = prompt("Sorry you lose. Do you want to play again?",options);
					if(answer.equals("Yes"))
						restart();
					else
						System.exit(0);
					*/
				}
				
				//Thread.sleep(2000);
				turns++;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}

	/**
	 * Deals the cards to the players.
	 */
	public void dealCards()
	{
		Card [] deck = new Card [24];
		Tile [][] tiles = board.getGrid();
		int numCardsMade = 0, numCornerCardsMade = 0, numCornerTilesSeen = 0;
		
		deck[0] = new Card(0,tiles[0][2]);
		tiles[0][2].setTreasure(deck[0]);
		
		deck[1] = new Card(1,tiles[0][4]);
		tiles[0][4].setTreasure(deck[1]);
		
		deck[2] = new Card(2,tiles[2][0]);
		tiles[2][0].setTreasure(deck[2]);
		
		deck[3] = new Card(3,tiles[2][2]);
		tiles[2][2].setTreasure(deck[3]);
		
		deck[4] = new Card(4,tiles[2][4]);
		tiles[2][4].setTreasure(deck[4]);
		
		deck[5] = new Card(5,tiles[2][6]);
		tiles[2][6].setTreasure(deck[5]);
		
		deck[6] = new Card(6,tiles[4][0]);
		tiles[4][0].setTreasure(deck[6]);
		
		deck[7] = new Card(7,tiles[4][2]);
		tiles[4][2].setTreasure(deck[7]);
		
		deck[8] = new Card(8,tiles[4][4]);
		tiles[4][4].setTreasure(deck[8]);
		
		deck[9] = new Card(9,tiles[4][6]);
		tiles[4][6].setTreasure(deck[9]);
		
		deck[10] = new Card(10,tiles[6][2]);
		tiles[6][2].setTreasure(deck[10]);
		
		deck[11] = new Card(11,tiles[6][4]);
		tiles[6][4].setTreasure(deck[11]);
		
		numCardsMade = 12;
		
		for(int i=0;i<tiles.length;i++)
		{
			for(int k=0;k<tiles[0].length;k++)
			{
				if(!tiles[i][k].isFixed())
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
				
			}
			
			if(numCardsMade>=24)
				break;
		}
		
		int cardDelt;
		ArrayList<Card> playerOneCards = new ArrayList<Card>(12);
		ArrayList<Card> playerTwoCards = new ArrayList<Card>(12);
		
		for(int slotFilled=0;slotFilled<12;slotFilled++)
		{
			while(true)
			{
				cardDelt = (int)(Math.random()*24);
				if(deck[cardDelt]!=null)
				{
					playerOneCards.add(deck[cardDelt]);
					deck[cardDelt] = null;
					break;
				}
			}
			
			while(true)
			{
				cardDelt = (int)(Math.random()*24);
				if(deck[cardDelt]!=null)
				{
					playerTwoCards.add(deck[cardDelt]);
					deck[cardDelt] = null;
					break;
				}
			}
		}
		
		playerOne.setTreasures(playerOneCards);
		playerTwo.setTreasures(playerTwoCards);
	}
	
	public void changeExtraTile()
	{
		if(leftOverTile != null)
			leftOverTile.removeFromDrawing(extraTileHolder);
		
		leftOverTile = board.getExtraTile();
		leftOverTile.addToDrawing(extraTileHolder);
		leftOverTile.moveToLocation(0,0);
	}
	
	/** 
	 * Restarts the game by resetting the board and player data.
	 */
	public void restart()
	{}
	
	public static void main(String [] args)
	{
		Application.launch(args);
	}
}
