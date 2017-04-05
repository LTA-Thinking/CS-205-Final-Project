import javafx.scene.layout.Pane;

public class Board extends Pane{

	
    // The size of the side of a square
    public final int SQUARE_SIZE;
    // The number of squares that fit on the screen in the x and y dimensions
    public final int X_DIM_SQUARES;
    public final int Y_DIM_SQUARES;
    protected Tile grid[][];
    
    public Board(int s,int x,int y) 
    {
    	SQUARE_SIZE = 20;
    	X_DIM_SQUARES = 7;
    	Y_DIM_SQUARES = 7;
    	
    	grid = new Tile[X_DIM_SQUARES][Y_DIM_SQUARES];
    	
        this.setPrefHeight(Y_DIM_SQUARES*SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES*SQUARE_SIZE);
        
        
        for (int row = 0; row < X_DIM_SQUARES; row++)
		{
			for (int col = 0; col < Y_DIM_SQUARES; col++)
			{
				// create new GameSquare object for this column and row.  Pass in
				// the panel and action listener so the button can be linked up!
				grid[col][row] = new Tile(this, 1,col,row,3);
			}
		}
    }
    
	// can I move from square at x, y one space in the direction specified? 
	// direction can be specified as NORTH, SOUTH, EAST, or WEST
    
	public boolean CanMove(int x, int y, int z){
		return false;
	
	}
	
	//insert a tile into a specific coordinate
	public void InsertTile(int x, int y){
		
	}
	
	//construct board graphics

	
	//Gets & Sets
	
	//gets the piece that the player will use next
	//set is a public void
	//get is public (return)
	
	
	//show the last move made
	
	//public int LastMove(){
		
		
	//}
	
	//highlight all possible moves
	public void HighlightMoves(int x, int y){
		
	}
	
}
