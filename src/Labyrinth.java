
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
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

/**
 * This class starts the program, creates the board and players, and handles top
 * level actions.
 *
 * @author RB_Johnson
 */
public class Labyrinth extends Application {

    private Board board;
    private Player playerOne, playerTwo;
    private Player currentPlayer;
    private Label displayPlayerOneTreasure, displayPlayerTwoTreasure;
    private Stage primaryStage;
    private Scene mainGameScene, introScene, promptScene;
    private Tile leftOverTile;
    private HBox extraTileHolder;
    private int turns = 0;
	private long startTime;
	private Image greyArrowButton, arrowButton;
	private ImageView[][] arrowButtons = new ImageView[4][3];
	
    /**
     * Starts the application, creates the players, and creates the board
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Welcome to Labyrinth");
		startTime = System.currentTimeMillis();
		
        board = new Board(100, 7, 7); // Fill in args
        setUpTileButtons();

        String humanVsHuman = "Human vs Human";
        String humanVsCom = "Human vs Computer";
  
        ChoiceDialog<String> gameTypeChoice = new ChoiceDialog<String>(humanVsHuman, humanVsHuman, humanVsCom);

        gameTypeChoice.showAndWait();
        String gameType = gameTypeChoice.getSelectedItem();

        if (gameType.equals(humanVsHuman)) {
            playerOne = new Human(board, Color.RED);
            playerTwo = new Human(board, Color.BLUE);
        } else if (gameType.equals(humanVsCom)) {
            playerOne = new Human(board, Color.RED);
            playerTwo = new Computer(board, this, Color.BLUE);
        } 

        System.out.println("Game type confirmed, dealing treasures...");
        dealCards();
        currentPlayer = playerTwo;
        System.out.println("Treasures dealt, loading board...");
        setupDisplay(primaryStage);
        System.out.println("Board loaded, starting game...");

        changeTurn();

        /*
		Popup popup = new Popup();
		popup.getContent().add(new Label("This is a popup"));
		popup.show();
         */
    }

    /**
     * Sets up the display of the game by placing all the major graphical
     * elements.
     *
     * @param primaryStage
     */
    public void setupDisplay(Stage primaryStage) {
        //***********Main Game Scene**************

        BorderPane mainPane = new BorderPane();

        VBox boardHolder = new VBox();
		boardHolder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));

        arrowButton = new Image("arrow_button.png");
		Image arrowButtonPressed = new Image("arrow_button_pressed.png");
		greyArrowButton = new Image("arrow_button_greyed.png");

        //*********************************************Top buttons**************************
        HBox topButtons = new HBox();

        ImageView topLeft = new ImageView(arrowButton);
        topLeft.setRotate(180);
        ImageView topCenter = new ImageView(arrowButton);
        topCenter.setRotate(180);
        ImageView topRight = new ImageView(arrowButton);
        topRight.setRotate(180);

        topLeft.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(1, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(1, 0));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(topLeft.getImage() != greyArrowButton)
				topLeft.setImage(arrowButtonPressed);
        });
		
        topLeft.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(1, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(1, 0)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(topLeft.getImage() != greyArrowButton)
				topLeft.setImage(arrowButton);
        });
		
        topLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(1, 0)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(1, 0));
                changeExtraTile();
                System.out.println("Top Left Button Pressed");
            }
        });

        topCenter.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(3, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(3, 0));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(topCenter.getImage() != greyArrowButton)
				topCenter.setImage(arrowButtonPressed);
        });
		
        topCenter.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(3, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(3, 0)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(topCenter.getImage() != greyArrowButton)
				topCenter.setImage(arrowButton);
        });
		
        topCenter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(3, 0)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(3, 0));
                changeExtraTile();
                System.out.println("Top Center Button Pressed");
            }
        });

        topRight.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(5, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(5, 0));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(topRight.getImage() != greyArrowButton)
				topRight.setImage(arrowButtonPressed);
        });
		
        topRight.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(5, 6))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(5, 0)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(topRight.getImage() != greyArrowButton)
				topRight.setImage(arrowButton);
        });
		
        topRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(5, 0)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(5, 0));
                changeExtraTile();
                System.out.println("Top Right Button Pressed");
            }
        });

        topButtons.setSpacing(Board.SQUARE_SIZE);
        topButtons.setPadding(new Insets(0, Board.SQUARE_SIZE * 2, 0, Board.SQUARE_SIZE * 2));
        topButtons.getChildren().add(topLeft);
        topButtons.getChildren().add(topCenter);
        topButtons.getChildren().add(topRight);
		
		arrowButtons[0][0] = topLeft;
		arrowButtons[0][1] = topCenter;
		arrowButtons[0][2] = topRight;

        //*************************************Bottom buttons**************************
        HBox bottomButtons = new HBox();

        ImageView bottomLeft = new ImageView(arrowButton);
        ImageView bottomCenter = new ImageView(arrowButton);
        ImageView bottomRight = new ImageView(arrowButton);

        bottomLeft.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(1, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(1, 6));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(bottomLeft.getImage() != greyArrowButton)
				bottomLeft.setImage(arrowButtonPressed);
        });
		
        bottomLeft.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(1, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(1, 6)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(bottomLeft.getImage() != greyArrowButton)
				bottomLeft.setImage(arrowButton);
        });
		
        bottomLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(1, 6)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(1, 6));
                changeExtraTile();
                System.out.println("Bottom Left Button Pressed");
            }
        });

        bottomCenter.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(3, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(3, 6));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(bottomCenter.getImage() != greyArrowButton)
				bottomCenter.setImage(arrowButtonPressed);
        });
		
        bottomCenter.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(3, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(3, 6)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(bottomCenter.getImage() != greyArrowButton)
				bottomCenter.setImage(arrowButton);
        });
		
        bottomCenter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(3, 6)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(3, 6));
                changeExtraTile();
                System.out.println("Bottom Center Button Pressed");
            }
        });

        bottomRight.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(5, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(5, 6));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(bottomRight.getImage() != greyArrowButton)
				bottomRight.setImage(arrowButtonPressed);
        });
		
        bottomRight.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(5, 0))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(5, 6)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(bottomRight.getImage() != greyArrowButton)
				bottomRight.setImage(arrowButton);
        });
		
        bottomRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(5, 6)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(5, 6));
                changeExtraTile();
                System.out.println("Bottom Right Button Pressed");
            }
        });

        bottomButtons.setSpacing(Board.SQUARE_SIZE);
        bottomButtons.setPadding(new Insets(0, Board.SQUARE_SIZE * 2, 0, Board.SQUARE_SIZE * 2));
        bottomButtons.getChildren().add(bottomLeft);
        bottomButtons.getChildren().add(bottomCenter);
        bottomButtons.getChildren().add(bottomRight);
		
		arrowButtons[1][0] = bottomLeft;
		arrowButtons[1][1] = bottomCenter;
		arrowButtons[1][2] = bottomRight;

        //*************************************Left hand buttons****************************
        VBox leftButtons = new VBox();

        ImageView leftTop = new ImageView(arrowButton);
        leftTop.setRotate(90);
        ImageView leftCenter = new ImageView(arrowButton);
        leftCenter.setRotate(90);
        ImageView leftBottom = new ImageView(arrowButton);
        leftBottom.setRotate(90);

        leftTop.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (currentPlayer.getPhaseOfTurn() == 1
                        && board.isHelperMode()
                        && !board.getLastMove().equals(new Point2D(6, 1))) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(new Point2D(0, 1));
                    changeExtraTile();
                    ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                    for (int i = 0; i < posLoc.size(); ++i) {
                        board.getTile(posLoc.get(i)).highlight();
                    }
                    board.setHighlight(true);
                    board.setLastMove(lastMove);
                }
				
				if(leftTop.getImage() != greyArrowButton)
					leftTop.setImage(arrowButtonPressed);
            }
        });
		
        leftTop.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (currentPlayer.getPhaseOfTurn() == 1
                        && board.isHelperMode()
                        && !board.getLastMove().equals(new Point2D(6, 1))) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(0, 1)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
				
				if(leftTop.getImage() != greyArrowButton)
					leftTop.setImage(arrowButton);
            }
        });
		
        leftTop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(0, 1)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(0, 1));
                changeExtraTile();
                System.out.println("Left Top Button Pressed");
            }
        });

        leftCenter.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(6, 3))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(0, 3));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(leftCenter.getImage() != greyArrowButton)
				leftCenter.setImage(arrowButtonPressed);
        });
		
        leftCenter.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(6, 3))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(0, 3)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(leftCenter.getImage() != greyArrowButton)
				leftCenter.setImage(arrowButton);
        });
		
        leftCenter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(0, 3)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(0, 3));
                changeExtraTile();
                System.out.println("Left Center Button Pressed");
            }
        });

        leftBottom.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(6, 5))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(0, 5));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(leftBottom.getImage() != greyArrowButton)
				leftBottom.setImage(arrowButtonPressed);
        });
		
        leftBottom.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(6, 5))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(0, 5)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(leftBottom.getImage() != greyArrowButton)
				leftBottom.setImage(arrowButton);
        });
		
        leftBottom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(0, 5)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(0, 5));
                changeExtraTile();
                System.out.println("Left Bottom Button Pressed");
            }
        });

        leftButtons.setSpacing(Board.SQUARE_SIZE);
        leftButtons.setPadding(new Insets(Board.SQUARE_SIZE, 0, Board.SQUARE_SIZE, 0));
        leftButtons.getChildren().add(leftTop);
        leftButtons.getChildren().add(leftCenter);
        leftButtons.getChildren().add(leftBottom);
		
		arrowButtons[2][0] = leftTop;
		arrowButtons[2][1] = leftCenter;
		arrowButtons[2][2] = leftBottom;

        //************************************Right hand buttons**************************
        VBox rightButtons = new VBox();

        ImageView rightTop = new ImageView(arrowButton);
        rightTop.setRotate(270);
        ImageView rightCenter = new ImageView(arrowButton);
        rightCenter.setRotate(270);
        ImageView rightBottom = new ImageView(arrowButton);
        rightBottom.setRotate(270);

        rightTop.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 1))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(6, 1));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(rightTop.getImage() != greyArrowButton)
				rightTop.setImage(arrowButtonPressed);
        });
		
        rightTop.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 1))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(6, 1)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(rightTop.getImage() != greyArrowButton)
				rightTop.setImage(arrowButton);
        });
		
        rightTop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(6, 1)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(6, 1));
                changeExtraTile();
                System.out.println("Right Top Button Pressed");
            }
        });

        rightCenter.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 3))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(6, 3));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(rightCenter.getImage() != greyArrowButton)
				rightCenter.setImage(arrowButtonPressed);
        });
		
        rightCenter.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 3))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(6, 3)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(rightCenter.getImage() != greyArrowButton)
				rightCenter.setImage(arrowButton);
        });
		
        rightCenter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(6, 3)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(6, 3));
                changeExtraTile();
                System.out.println("Right Center Button Pressed");
            }
        });

        rightBottom.setOnMouseEntered((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 5))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(new Point2D(6, 5));
                changeExtraTile();
                ArrayList<Point2D> posLoc = currentPlayer.allPossibleLoc();
                for (int i = 0; i < posLoc.size(); ++i) {
                    board.getTile(posLoc.get(i)).highlight();
                }
                board.setHighlight(true);
                board.setLastMove(lastMove);
            }
			
			if(rightBottom.getImage() != greyArrowButton)
				rightBottom.setImage(arrowButtonPressed);
        });
		
        rightBottom.setOnMouseExited((MouseEvent e) -> {
            if (currentPlayer.getPhaseOfTurn() == 1
                    && board.isHelperMode()
                    && !board.getLastMove().equals(new Point2D(0, 5))) {
                Point2D lastMove = board.getLastMove();
                board.insertTile(board.oppositeLoc(new Point2D(6, 5)));
                changeExtraTile();
                board.setLastMove(lastMove);
            }
			
			if(rightBottom.getImage() != greyArrowButton)
				rightBottom.setImage(arrowButton);
        });
		
        rightBottom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.isHelperMode()) {
                    Point2D lastMove = board.getLastMove();
                    board.insertTile(board.oppositeLoc(new Point2D(6, 5)));
                    changeExtraTile();
                    board.setLastMove(lastMove);
                }
                currentPlayer.getInsertTile(new Point2D(6, 5));
                changeExtraTile();
                System.out.println("Right Bottom Button Pressed");
            }
        });

        rightButtons.setSpacing(Board.SQUARE_SIZE);
        rightButtons.setPadding(new Insets(Board.SQUARE_SIZE, 0, Board.SQUARE_SIZE, 0));
        rightButtons.getChildren().add(rightTop);
        rightButtons.getChildren().add(rightCenter);
        rightButtons.getChildren().add(rightBottom);
		
		arrowButtons[3][0] = rightTop;
		arrowButtons[3][1] = rightCenter;
		arrowButtons[3][2] = rightBottom;

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
		
		rotateButtons.setPrefWidth(Board.SQUARE_SIZE);
		rotateButtons.setAlignment(Pos.CENTER);
		rotateButtons.setSpacing(15);
		rotateButtons.setPadding(new Insets(0, 0, 10, 0));
		
		Label playerOneName = new Label("Player One");
		playerOneName.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,null)));
		playerOneName.setPrefWidth(Board.SQUARE_SIZE);
		playerOneName.setAlignment(Pos.CENTER);
		
		Label playerTwoName = new Label("Player Two");
		playerTwoName.setBackground(new Background(new BackgroundFill(Color.BLUE,CornerRadii.EMPTY,null)));
		playerTwoName.setPrefWidth(Board.SQUARE_SIZE);
		playerTwoName.setAlignment(Pos.CENTER);
		
        displayPlayerOneTreasure = new Label("0");
		displayPlayerOneTreasure.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,null)));
		displayPlayerOneTreasure.setPrefWidth(Board.SQUARE_SIZE);
		displayPlayerOneTreasure.setAlignment(Pos.CENTER);
		
        displayPlayerTwoTreasure = new Label("0");
		displayPlayerTwoTreasure.setBackground(new Background(new BackgroundFill(Color.BLUE,CornerRadii.EMPTY,null)));
		displayPlayerTwoTreasure.setPrefWidth(Board.SQUARE_SIZE);
		displayPlayerTwoTreasure.setAlignment(Pos.CENTER);

        VBox sidePanel = new VBox();
        sidePanel.setPadding(new Insets(10));
        sidePanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));

        extraTileHolder = new HBox();
        changeExtraTile();

        rotateCCW.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent e) 
			{
                leftOverTile.rotateCCW();
            }
        });

        rotateCW.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent e) 
			{
                leftOverTile.rotateCW();
            }
        });

		Button helper = new Button("Helper mode");
		helper.setPrefWidth(Board.SQUARE_SIZE);
		helper.setAlignment(Pos.CENTER);
		
        helper.setOnAction((ActionEvent e) -> {
            System.out.println("testing: " + board.isHelperMode());
			
            if (board.isHelperMode()) 
			{
                board.setHelperMode(false);
                helper.setText("Helper mode");
            } 
			else 
			{
                board.setHelperMode(true);
                helper.setText("Normal mode");
            }
        });

        Button helpMenu = new Button("Instructions");
		helpMenu.setPrefWidth(Board.SQUARE_SIZE);
		helpMenu.setAlignment(Pos.CENTER);
		
        helpMenu.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent e)
			{
				Alert helpDisplay = new Alert(Alert.AlertType.INFORMATION, "Welcome to Labyrinth! The objective of the game is to gather all 12 treasures before your opponent does. The treasure you are currently pursuing will show up on the board as the same color as your player. In order to play, you first must insert the extra tile displayed on the right into the board. Click one of the arrow buttons to insert the tile there. But be careful! You can’t reverse another player’s move by putting the extra tile back where it just was. Also, if your player is pushed out of the board, they will be transferred to the other side. Next, you can move your piece as far as you choose to anywhere that you currently have a path to. You can do this simply by clicking a square on the board. Make sure you choose a square that you have a path to! Once you reach the square that has your treasure, you will pick it up and receive 10 points. Then, you’ll be assigned a new treasure. You win when you collect all 12 of your treasures.");
				helpDisplay.showAndWait();
            }
        });
		
		Button displayStats = new Button("Statistics");
		displayStats.setPrefWidth(Board.SQUARE_SIZE);
		displayStats.setAlignment(Pos.CENTER);
		
		displayStats.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				displayStats();
			}
		});

        sidePanel.getChildren().add(extraTileHolder);
        sidePanel.getChildren().add(rotateButtons);
        sidePanel.getChildren().add(playerOneName);
        sidePanel.getChildren().add(displayPlayerOneTreasure);
        sidePanel.getChildren().add(playerTwoName);
        sidePanel.getChildren().add(displayPlayerTwoTreasure);

        sidePanel.getChildren().add(helpMenu);
        sidePanel.getChildren().add(helper);
		sidePanel.getChildren().add(displayStats);

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

    public void setUpTileButtons() {
        Tile[][] grid = board.getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int k = 0; k < grid[0].length; k++) {
                Tile tile = grid[i][k];

                tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        currentPlayer.getMoveTile(tile);
                        if (!currentPlayer.isCurrentPlayer()) {
                            changeTurn();
                        }
                        System.out.println("Tile " + tile.getXLocation() + ", " + tile.getYLocation() + " clicked");
                    }
                });
            }
        }

        Tile tile = board.getExtraTile();

        tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                currentPlayer.getMoveTile(tile);
                if (!currentPlayer.isCurrentPlayer()) {
                    changeTurn();
                }
                System.out.println("Tile " + tile.getXLocation() + ", " + tile.getYLocation() + " clicked");
            }
        });
    }

    /**
     * Tells the players to take their turns. Handles conditions for if the game
     * ends.
     */
    public void changeTurn() 
	{
        if(currentPlayer.checkWin())
		{
			boolean winner = false;
			
			if(currentPlayer == playerOne)
			{
				Alert gameOver = new Alert(Alert.AlertType.INFORMATION, "Player One Wins!\nPlease hit the restart button if you want to play again.");
				gameOver.showAndWait();
				winner = true;
			}
			else if(currentPlayer == playerTwo)
			{
				Alert gameOver = new Alert(Alert.AlertType.INFORMATION, "Player Two Wins!\nPlease hit the restart button if you want to play again.");
				gameOver.showAndWait();
				winner = false;
			}
			else
			{
				System.out.println("ERROR: Unknown winner.");
			}
			
			if(playerTwo.getClass().getName().equals("Computer"))
			{
				long gameTime = System.currentTimeMillis()-startTime;
				int playerOneScore = playerOne.getScore();
				int playerTwoScore = playerTwo.getScore();
				
				//Stats.insertScore(playerOneScore,playerTwoScore,turns,gameTime,winner);
			}
		}
        else 
		{
            turns++;

            displayPlayerOneTreasure.setText("" + playerOne.getScore());
            displayPlayerTwoTreasure.setText("" + playerTwo.getScore());

            if (currentPlayer == playerOne) 
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

            if (!currentPlayer.isCurrentPlayer()) 
			{
                changeTurn();
            }
			
			greyOutButton();
        }
    }

    /**
     * Deals the cards to the players.
     */
    public void dealCards() {
        ArrayList<Card> deck = new ArrayList<Card>(24);
        Tile[][] tiles = board.getGrid();
        int numCardsMade = 0, numCornerCardsMade = 0, numCornerTilesSeen = 0;

        deck.add(new Card(0, tiles[0][2]));
        tiles[0][2].setTreasure(deck.get(0));

        deck.add(new Card(1, tiles[0][4]));
        tiles[0][4].setTreasure(deck.get(1));

        deck.add(new Card(2, tiles[2][0]));
        tiles[2][0].setTreasure(deck.get(2));

        deck.add(new Card(3, tiles[2][2]));
        tiles[2][2].setTreasure(deck.get(3));

        deck.add(new Card(4, tiles[2][4]));
        tiles[2][4].setTreasure(deck.get(4));

        deck.add(new Card(5, tiles[2][6]));
        tiles[2][6].setTreasure(deck.get(5));

        deck.add(new Card(6, tiles[4][0]));
        tiles[4][0].setTreasure(deck.get(6));

        deck.add(new Card(7, tiles[4][2]));
        tiles[4][2].setTreasure(deck.get(7));

        deck.add(new Card(8, tiles[4][4]));
        tiles[4][4].setTreasure(deck.get(8));

        deck.add(new Card(9, tiles[4][6]));
        tiles[4][6].setTreasure(deck.get(9));

        deck.add(new Card(10, tiles[6][2]));
        tiles[6][2].setTreasure(deck.get(10));

        deck.add(new Card(11, tiles[6][4]));
        tiles[6][4].setTreasure(deck.get(11));

        numCardsMade = 12;

        LinkedList<Tile> cornerTiles = new LinkedList<Tile>();

        for (int i = 0; i < tiles.length; i++) {
            for (int k = 0; k < tiles[0].length; k++) {
                if (!tiles[i][k].isFixed()) {
                    if (tiles[i][k].getType() == Tile.T_TYPE) {
                        deck.add(new Card(numCardsMade, tiles[i][k]));
                        tiles[i][k].setTreasure(deck.get(numCardsMade));
                        numCardsMade++;
                    } else if (tiles[i][k].getType() == Tile.L_TYPE) {
                        cornerTiles.add(tiles[i][k]);
                    }
                }

            }
        }
		
		if(board.getExtraTile().getType() == Tile.T_TYPE)
		{
			deck.add(new Card(numCardsMade, board.getExtraTile()));
			board.getExtraTile().setTreasure(deck.get(numCardsMade));
			numCardsMade++;
		}

        Collections.shuffle(cornerTiles);

        for (int i = 0; i < 6; i++)
		{
            Tile t = cornerTiles.remove(0);
            deck.add(new Card(numCardsMade, t));
            t.setTreasure(deck.get(numCardsMade));
            numCardsMade++;
        }

        Collections.shuffle(deck);

        int cardDelt;
        ArrayList<Card> playerOneCards = new ArrayList<Card>(12);
        ArrayList<Card> playerTwoCards = new ArrayList<Card>(12);

        for (int slotFilled = 0; slotFilled < 12; slotFilled++) 
		{
            playerOneCards.add(deck.remove(0));
            playerTwoCards.add(deck.remove(0));
        }

        playerOne.setTreasures(playerOneCards);
        playerTwo.setTreasures(playerTwoCards);
    }

	public void displayStats()
	{
		
	}
	
    public void changeExtraTile() 
	{
        if (leftOverTile != null) 
		{
            leftOverTile.removeFromDrawing(extraTileHolder);
        }

        leftOverTile = board.getExtraTile();
        leftOverTile.addToDrawing(extraTileHolder);
        leftOverTile.moveToLocation(0, 0);
    }
	
	public void greyOutButton()
	{
		for(int i=0;i<arrowButtons.length;i++)
		{
			for(int k=0;k<arrowButtons[0].length;k++)
			{
				arrowButtons[i][k].setImage(arrowButton);
			}
		}
		
		Point2D lastMove = board.getLastMove();
		
		if(lastMove.getY() == 0)
		{
			if(lastMove.getX() != 0)
				arrowButtons[1][(lastMove.getX()-1)/2].setImage(greyArrowButton);
		}
		else if(lastMove.getY() == 6)
		{
			arrowButtons[0][(lastMove.getX()-1)/2].setImage(greyArrowButton);
		}
		else if(lastMove.getX() == 0)
		{
			arrowButtons[3][(lastMove.getY()-1)/2].setImage(greyArrowButton);
		}
		else if(lastMove.getX() == 6)
		{
			arrowButtons[2][(lastMove.getY()-1)/2].setImage(greyArrowButton);
		}
	}

    public static void main(String[] args) {
        Application.launch(args);
    }

}
