import javafx.scene.layout.Pane;

public class Board extends Pane{

	
    // The size of the side of a square
    public final static int SQUARE_SIZE = 20;
    // The number of squares that fit on the screen in the x and y dimensions
    public final int X_DIM_SQUARES;
    public final int Y_DIM_SQUARES;
    protected static Tile grid[][];
    
    public Board(int s,int x,int y) 
    {
   
    	X_DIM_SQUARES = 7;
    	Y_DIM_SQUARES = 7;
    	
    	grid = new Tile[X_DIM_SQUARES][Y_DIM_SQUARES];
    	
        this.setPrefHeight(Y_DIM_SQUARES*SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES*SQUARE_SIZE);
        
        
        for (int row = 0; row < X_DIM_SQUARES; row+=2)
		{
			for (int col = 1; col < Y_DIM_SQUARES; col+=2)
			{
				// create new GameSquare object for this column and row.  Pass in
				// the panel and action listener so the button can be linked up!
		
				grid[col][row] = new Tile(this, 1,col,row,3);
				
			}
		}
        for (int row = 1; row < X_DIM_SQUARES; row+=2)
		{
			for (int col = 0; col < Y_DIM_SQUARES; col++)
			{
				// create new GameSquare object for this column and row.  Pass in
				// the panel and action listener so the button can be linked up!
		
				grid[col][row] = new Tile(this, 1,col,row,3);
				
			}
		}
        grid[0][0] = new Tile(this, 1,0,0,1);
        grid[2][0] = new Tile(this, 0,2,0,1);
        grid[4][0] = new Tile(this, 0,4,0,1);
        grid[6][0] = new Tile(this, 1,6,0,2);
        grid[0][2] = new Tile(this, 0,2,0,0);
        grid[2][2] = new Tile(this, 0,2,2,0);
        grid[4][2] = new Tile(this, 0,2,4,1);
        grid[6][2] = new Tile(this, 0,2,6,2);
        grid[0][4] = new Tile(this, 0,4,0,0);
        grid[2][4] = new Tile(this, 0,4,2,3);
        grid[4][4] = new Tile(this, 0,4,4,2);
        grid[6][4] = new Tile(this, 0,4,6,2);
        grid[0][6] = new Tile(this, 1,6,0,0);
        grid[2][6] = new Tile(this, 0,6,2,3);
        grid[4][6] = new Tile(this, 0,6,4,3);
        grid[6][6] = new Tile(this, 1,6,6,3);
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
	
    public static void main(String[] args) {
    	
		new Board(20,7,7);
		
		
		/*
    	Tile tile = new Tile(board, 1,2,3,3);
    	tile.toString();
    	System.out.print(tile);
    	System.out.println("hi");
    	*/
    	
    	
    	
    	for (int i = 0; i<7; i++){
    	    for (int j = 0; j<7; j++){

    	        System.out.print(grid[i][j] + "  ");

    	    }
    	     System.out.println();
    	}
		
}
}
