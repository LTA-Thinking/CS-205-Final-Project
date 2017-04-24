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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

/**
 * This class starts the program, creates the board and players, and handles top level actions.
 * 
 * @author RB_Johnson
 */
public class Labyrinth extends Application
{
	private Board board;
	private Player playerOne, playerTwo;
	private Player currentPlayer;
	private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
	private Stage primaryStage;
	private Scene mainGameScene, introScene, promptScene;
	private Tile leftOverTile;
	private HBox extraTileHolder;
	private int turns = 0;
        
	
	/**
     * Starts the application, creates the players, and creates the board
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {  
		System.out.println("Welcome to Labyrinth");
		
		board = new Board(100,7,7); // Fill in args
		setUpTileButtons();
		
		String humanVsHuman = "Human vs Human";
		String humanVsCom = "Human vs Computer";
		String comVsCom = "Computer vs Computer";
		ChoiceDialog<String> gameTypeChoice = new ChoiceDialog<String>(humanVsHuman,humanVsHuman,humanVsCom,comVsCom);
		
		gameTypeChoice.showAndWait();
		String gameType = gameTypeChoice.getSelectedItem();
		
		if(gameType.equals(humanVsHuman))
		{
			playerOne = new Human(board,Color.RED);
			playerTwo = new Human(board,Color.BLUE);
		} 
		else if(gameType.equals(humanVsCom))
		{
			playerOne = new Human(board,Color.RED);
			playerTwo = new Computer(board,this,Color.BLUE);
		}
		else
		{
			playerOne = new Computer(board,this,Color.RED);
			playerTwo = new Computer(board,this,Color.BLUE);
		}
		
		System.out.println("Game type confirmed, dealing treasures...");
		dealCards();
		
		System.out.println("Treasures dealt, loading board...");
		setupDisplay(primaryStage);
	
		System.out.println("Board loaded, starting game...");
		currentPlayer = playerTwo;
		changeTurn();

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
				currentPlayer.getInsertTile(new Point2D(1,0));
                changeExtraTile();
				System.out.println("Top Left Button Pressed");
			}
		});
		
		topCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(3,0));
                changeExtraTile();
				System.out.println("Top Center Button Pressed");
			}
		});
		
		topRight.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(5,0));
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
				currentPlayer.getInsertTile(new Point2D(1,6));
                changeExtraTile();
				System.out.println("Bottom Left Button Pressed");
			}
		});
		
		bottomCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(3,6));
                changeExtraTile();
				System.out.println("Bottom Center Button Pressed");
			}
		});
		
		bottomRight.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(5,6));
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
				currentPlayer.getInsertTile(new Point2D(0,1));
                changeExtraTile();
				System.out.println("Left Top Button Pressed");
			}
		});
		
		leftCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(0,3));
                changeExtraTile();
				System.out.println("Left Center Button Pressed");
			}
		});
		
		leftBottom.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(0,5));
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
				currentPlayer.getInsertTile(new Point2D(6,1));
                changeExtraTile();
				System.out.println("Right Top Button Pressed");
			}
		});
		
		rightCenter.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(6,3));
                changeExtraTile();
				System.out.println("Right Center Button Pressed");
			}
		});
		
		rightBottom.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override public void handle(MouseEvent e)
			{
				currentPlayer.getInsertTile(new Point2D(6,5));
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
		
		displayPlayerOneTreasure = new Label("0");
		displayPlayerTwoTreasure = new Label("0");
		
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
                            if(board.isHighlight()){
                                ArrayList<Point2D> posLoc = playerOne.allPossibleLoc();
                                for(int i =0; i < posLoc.size(); ++i){
                                    board.getTile(posLoc.get(i)).unHighlight();
                                }
                                board.setHighlight(false);
                            }else{
                                ArrayList<Point2D> posLoc = playerOne.allPossibleLoc();
                                for(int i =0; i < posLoc.size(); ++i){
                                    board.getTile(posLoc.get(i)).highlight();
                                }
                                board.setHighlight(true);
                            }
				
				
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
						currentPlayer.getMoveTile(tile);
						if(!currentPlayer.isCurrentPlayer())
						{
							changeTurn();
						}
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
				currentPlayer.getMoveTile(tile);
				if(!currentPlayer.isCurrentPlayer())
				{
					changeTurn();
				}
				System.out.println("Tile " + tile.getXLocation() + ", " + tile.getYLocation() + " clicked");
			}
		});
	}
	
	/**
	 * Tells the players to take their turns.
	 * Handles conditions for if the game ends.
	 */
	public void changeTurn()
	{
		if(currentPlayer.checkWin())
		{
			if(currentPlayer == playerOne)
			{
				Alert gameOver = new Alert(Alert.AlertType.INFORMATION, "Player One Wins!\nPlease hit the restart button if you want to play again.");
				gameOver.showAndWait();
			}
			else if(currentPlayer == playerTwo)
			{
				Alert gameOver = new Alert(Alert.AlertType.INFORMATION, "Player Two Wins!\nPlease hit the restart button if you want to play again.");
				gameOver.showAndWait();
			}
			else
			{
				System.out.println("ERROR: Unknown winner.");
			}
		}
		else
		{
			turns++;
			
			displayPlayerOneTreasure.setText(""+playerOne.getScore());
			displayPlayerTwoTreasure.setText(""+playerTwo.getScore());
			
			if(currentPlayer == playerOne)
			{
				currentPlayer = playerTwo;
				System.out.println("\nPlayer Two's Turn\n");
			}
			else
			{
				currentPlayer = playerOne;
				System.out.println("\nPlayer One's Turn\n");
			}
			
			currentPlayer.takeTurn();
			
			if(!currentPlayer.isCurrentPlayer())
			{
				changeTurn();
			}
		}
	}

	/**
	 * Deals the cards to the players.
	 */
	public void dealCards()
	{
		ArrayList<Card> deck = new ArrayList<Card>(24);
		Tile [][] tiles = board.getGrid();
		int numCardsMade = 0, numCornerCardsMade = 0, numCornerTilesSeen = 0;
		
		deck.add(new Card(0,tiles[0][2]));
		tiles[0][2].setTreasure(deck.get(0));
		
		deck.add(new Card(1,tiles[0][4]));
		tiles[0][4].setTreasure(deck.get(1));
		
		deck.add(new Card(2,tiles[2][0]));
		tiles[2][0].setTreasure(deck.get(2));
		
		deck.add(new Card(3,tiles[2][2]));
		tiles[2][2].setTreasure(deck.get(3));
		
		deck.add(new Card(4,tiles[2][4]));
		tiles[2][4].setTreasure(deck.get(4));
		
		deck.add( new Card(5,tiles[2][6]));
		tiles[2][6].setTreasure(deck.get(5));
		
		deck.add(new Card(6,tiles[4][0]));
		tiles[4][0].setTreasure(deck.get(6));
		
		deck.add(new Card(7,tiles[4][2]));
		tiles[4][2].setTreasure(deck.get(7));
		
		deck.add(new Card(8,tiles[4][4]));
		tiles[4][4].setTreasure(deck.get(8));
		
		deck.add(new Card(9,tiles[4][6]));
		tiles[4][6].setTreasure(deck.get(9));
		
		deck.add(new Card(10,tiles[6][2]));
		tiles[6][2].setTreasure(deck.get(10));
		
		deck.add(new Card(11,tiles[6][4]));
		tiles[6][4].setTreasure(deck.get(11));
		
		numCardsMade = 12;
		
		LinkedList<Tile> cornerTiles = new LinkedList<Tile>();
		
		for(int i=0;i<tiles.length;i++)
		{
			for(int k=0;k<tiles[0].length;k++)
			{
				if(!tiles[i][k].isFixed())
				{
					if(tiles[i][k].getType()==Tile.T_TYPE)
					{
						deck.add(new Card(numCardsMade,tiles[i][k]));
						tiles[i][k].setTreasure(deck.get(numCardsMade));
						numCardsMade++;
					}
					else if(tiles[i][k].getType()==Tile.L_TYPE)
					{
						cornerTiles.add(tiles[i][k]);
					}
				}
				
			}
		}
		
		Collections.shuffle(cornerTiles);
		
		for(int i=0;i<6;i++)
		{
			Tile t = cornerTiles.remove(0);
			deck.add(new Card(numCardsMade,t));
			t.setTreasure(deck.get(numCardsMade));
			numCardsMade++;
		}
		
		Collections.shuffle(deck);
		
		int cardDelt;
		ArrayList<Card> playerOneCards = new ArrayList<Card>(12);
		ArrayList<Card> playerTwoCards = new ArrayList<Card>(12);
		
		for(int slotFilled=0;slotFilled<12;slotFilled++)
		{
			playerOneCards.add(deck.remove(0));
			playerTwoCards.add(deck.remove(0));
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
	{
		board = new Board(100,7,7); // Fill in args
		setUpTileButtons();
		
		String humanVsHuman = "Human vs Human";
		String humanVsCom = "Human vs Computer";
		String comVsCom = "Computer vs Computer";
		ChoiceDialog<String> gameTypeChoice = new ChoiceDialog<String>(humanVsHuman,humanVsHuman,humanVsCom,comVsCom);
		
		gameTypeChoice.showAndWait();
		String gameType = gameTypeChoice.getSelectedItem();
		
		if(gameType.equals(humanVsHuman))
		{
			playerOne = new Human(board,Color.RED);
			playerTwo = new Human(board,Color.BLUE);
		} 
		else if(gameType.equals(humanVsCom))
		{
			playerOne = new Human(board,Color.RED);
			playerTwo = new Computer(board,this,Color.BLUE);
		}
		else
		{
			playerOne = new Computer(board,this,Color.RED);
			playerTwo = new Computer(board,this,Color.BLUE);
		}
		
		currentPlayer = playerTwo;
		
		dealCards();
		
		changeTurn();
	}
	
	public static void main(String [] args)
	{
		Application.launch(args);
	}



}
