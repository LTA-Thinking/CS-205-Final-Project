import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
/**
 * This class contains the information for the board of the tile game.
 * It creates a board based on the tile class and fills it in according to the game rules 
 *
 * @author Ivan Spizizen
 */
public class Board extends Pane{

	
	/**
	 * @param SQUARE_SIZE is the size of each tile
	 * @param X_DIM_SQUARES is the amount of x cords on grid
	 * @param Y_DIM_SQUARES is the amount of y cords on grid
	 * @param Tile grid [][] is a 2 dimensional array that holds tiles
	 * @param counter is used to fill the board with random tiles from the shuffled array
	 * @param Tile extraTile is the remaining tile when the board is created
	 * @param Tile t1 & t2 are generic tiles used for the canMove method
	 * @param startX & startY are used in the highlightMoves method to avoid an endless loop going back and forth from the starting tile location to the first direction
	 */
    public static int SQUARE_SIZE;
    public final int X_DIM_SQUARES;
    public final int Y_DIM_SQUARES;
    protected static Tile grid[][];
	private int counter = 0;
	private static Tile extraTile;
	private static Tile t1;
	private static Tile t2;
	private static int startX;
	private static int startY;
	private Point2D lastMove;
	
	/**
	 * @return the lastMove
	 */
	public Point2D getLastMove() {
		return lastMove;
	}

	/**
	 * @param lastMove the lastMove to set
	 */
	public void setLastMove(Point2D lastMove) {
		this.lastMove = lastMove;
	}

	/**
	 * Sets up the board filling it with tiles based on X/Y_DIM_SQUARES.
	 * First it fills in the 16 pieces of the board that do not move
	 * Then, it creates a randomized array to fill in the remaining 33 pieces
	 * 
	 * @param s is the size of each square
	 * @param x is the amount of x coordinates
	 * @param y is the amount of y coordines
	 */
	public Board(int s,int x,int y) 
    {
		SQUARE_SIZE = s;
    	X_DIM_SQUARES = x;
    	Y_DIM_SQUARES = y;
    	
    	grid = new Tile[X_DIM_SQUARES][Y_DIM_SQUARES];
    	
        this.setPrefHeight(Y_DIM_SQUARES*SQUARE_SIZE);
        this.setPrefWidth(X_DIM_SQUARES*SQUARE_SIZE);
        
        grid[0][0] = new Tile(this, 1,0,0,1);
        grid[2][0] = new Tile(this, 0,2,0,1);
        grid[4][0] = new Tile(this, 0,4,0,1);
        grid[6][0] = new Tile(this, 1,6,0,2);
        grid[0][2] = new Tile(this, 0,0,2,0);
        grid[2][2] = new Tile(this, 0,2,2,0);
        grid[4][2] = new Tile(this, 0,4,2,1);
        grid[6][2] = new Tile(this, 0,6,2,2);
        grid[0][4] = new Tile(this, 0,0,4,0);
        grid[2][4] = new Tile(this, 0,2,4,3);
        grid[4][4] = new Tile(this, 0,4,4,2);
        grid[6][4] = new Tile(this, 0,6,4,2);
        grid[0][6] = new Tile(this, 1,0,6,0);
        grid[2][6] = new Tile(this, 0,2,6,3);
        grid[4][6] = new Tile(this, 0,4,6,3);
        grid[6][6] = new Tile(this, 1,6,6,3);
        
        int[] tilearrs = new int[] {0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
        		2,2,2,2,2,2,2,2,2,2,2,2};
        List<Integer> tList = new ArrayList<Integer>();
        for (int index = 0; index < tilearrs.length; index++)
        {
            tList.add(tilearrs[index]);
        }
        Collections.shuffle(tList);
        
        
        for (int row = 0; row < X_DIM_SQUARES; row+=2)
		{
			for (int col = 1; col < Y_DIM_SQUARES; col+=2)
			{
				Random rand = new Random();
				int  n = rand.nextInt(3) + 1;
				
				grid[col][row] = new Tile(this, tList.get(counter),col,row,n);
				counter ++;
				
			}
		}
        for (int row = 1; row < X_DIM_SQUARES; row+=2)
		{
			for (int col = 0; col < Y_DIM_SQUARES; col++)
			{
				// create new GameSquare object for this column and row.  Pass in
				// the panel and action listener so the button can be linked up!
				Random rand = new Random();
				int  n = rand.nextInt(3) + 1;				
				grid[col][row] = new Tile(this, tList.get(counter),col,row,n);
				counter++;			
			}
		}       
        //gets the extra tile
        Random rand = new Random();
		int  n = rand.nextInt(3) + 1;
		Tile extraTile = new Tile(this, tList.get(33), -1, -1, n);
		setExtraTile(extraTile);	
    }
    
	/**
	 * Returns the tile.
	 */
	public Tile getTile(Point2D t){
		return grid[t.getX()][t.getY()];
	}
	/**
	 * gets the extra tile which is the leftover gamepiece
	 */
	public Tile getExtraTile() {
		return extraTile;
	}
	/**
	 * sets the extra tile which is the leftover gamepiece
	 * @return 
	 */
	public Tile setExtraTile(Tile extraTile) {
		return this.extraTile = extraTile;
	}
	
	public void insertTile(Point2D insertLoc){	
            
//                        grid[6][insertLoc.getY()].removeFromDrawing(this);
//                        grid[5][insertLoc.getY()].moveToLocation(6,insertLoc.getY());
//                        grid[6][insertLoc.getY()] = grid[5][insertLoc.getY()];
		//get tile
		t1 = getExtraTile();
		System.out.println(t1);
//                int opposTileX = 999;
//                int opposTileY = 999;
                
                if(insertLoc.getX() == 0){
                    System.out.println("1");
//                    opposTileX = 6;
//                    opposTileY = insertLoc.getY();
//                    setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
//                    
                    
                    grid[insertLoc.getX()][insertLoc.getY()].removeFromDrawing(this);
                    t1.addToDrawing(this);
                    t1.moveToLocation(insertLoc.getX(),insertLoc.getY());
                    grid[insertLoc.getX()][insertLoc.getY()] = t1;
                    
//                    
//                    for(int i = 6; i > 1; --i){
//                        grid[i][insertLoc.getY()].removeFromDrawing(this);
//                        grid[i - 1][insertLoc.getY()].moveToLocation(i,insertLoc.getY());
//                        grid[i][insertLoc.getY()] = grid[i - 1][insertLoc.getY()];
//                        
//                    }
                }
//                else if(insertLoc.getX() == 6){
//                    System.out.println("2");
//                    opposTileX = 0;
//                    opposTileY = insertLoc.getY();
//                    setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
//                    for(int i = 0; i > 5; ++i){
//                        grid[i][insertLoc.getY()].removeFromDrawing(this);
//                        grid[i + 1][insertLoc.getY()] = grid[i][insertLoc.getY()];
//                        grid[i][insertLoc.getY()].moveToLocation(i + 1,insertLoc.getY());
//                    }
//                }else if(insertLoc.getY() == 0){
//                    System.out.println("3");
//                    opposTileY = 6;
//                    opposTileX = insertLoc.getX();
//                    setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
//                    for(int i = 6; i > 1; --i){
//                        grid[i][insertLoc.getY()].removeFromDrawing(this);
//                        grid[insertLoc.getX()][i - 1] = grid[insertLoc.getX()][i];
//                        grid[insertLoc.getX()][i].moveToLocation(insertLoc.getX(),i - 1);
//                    }
//                }else if(insertLoc.getY() == 6){
//                    System.out.println("4");
//                    opposTileY = 0;
//                    opposTileX = insertLoc.getX();
//                    setExtraTile(getTile(new Point2D(opposTileX, opposTileY)));
//                    for(int i = 0; i > 5; ++i){
//                        grid[i][insertLoc.getY()].removeFromDrawing(this);
//                        grid[insertLoc.getX()][i + 1] = grid[insertLoc.getX()][i];
//                        grid[insertLoc.getX()][i].moveToLocation(insertLoc.getX(),i + 1);
//                    }
//                }
//                
//		
//		//set extra tile to last on grid and move location to -1,-1 which is extra tile location
////		t2 = grid[1][6];
////		System.out.println(t2);
//		
//		//set the extra tile to the spot where we inserted and change the coordinates
//		grid[insertLoc.getX()][insertLoc.getY()] = t1;
//		t1.moveToLocation(insertLoc.getX(),insertLoc.getY());	
////		System.out.println(grid[1][0]);
////		if (x==1 && y==0){
////			for (int i = 0; i <=5; i+=1){
////				grid[x][y] = grid[x][i+1];
////				System.out.println(grid[x][y]);
////			}
////		}
////		t2.moveToLocation(-1,-1);
////		setExtraTile(t2);
////		System.out.println(extraTile);
//		
//		 
	}
	
	/**
	 * Specifies if t1 can move to t2 in all 4 directs
	 * this is a one position move, not a full path move
	 * EX: can tile 0,0 (top left corner) move to tile 1,0
	 * 
	 * @param x1 x cord1
	 * @param y1 y cord1
	 * @param x2 x cord2
	 * @param y2 y cord2
	 */
	 public boolean canMove(Point2D start, int dir){
		int x1 = start.getX();
		int y1 = start.getY();
		int x2 =0;
		int y2 =0;
		if(dir == 0){
			x2 = x1;
			y2 = y1-1;
		}
		if(dir == 1){
			x2 = x1+1;
			y2 = y1;
		}
		if(dir == 2){
			x2 = x1;
			y2 = y1+1;
		}
		if(dir == 3){
			x2 = x1-1;
			y2 = y1;
		}
		if(y2<0||x2<0){
                    return false;
                }else{
		
		t1 = grid[x1][y1];
		t2 = grid[x2][y2];		
		if (y1-y2 ==-1 && t1.isConnectedInDirection(2) && t2.isConnectedInDirection(0)){
			System.out.println("Can Move South");
			return true;
		}
		else if (x1-x2 == 1 && t1.isConnectedInDirection(3) && t2.isConnectedInDirection(1)){
			System.out.println("Can Move West");	
			return true;
		}		
		
		else if (y1-y2 == 1 && t1.isConnectedInDirection(0) && t2.isConnectedInDirection(2)){
			System.out.println("Can Move North");
			return true;
		}		
		
		else if (x1-x2 ==-1 && t1.isConnectedInDirection(1) && t2.isConnectedInDirection(3)){
			System.out.println("Can Move East");	
			return true;
		}
		else{
			return false;
		}	
                }
	}
	
	
	/**
	 * Highlight all the possible moves you can make from the starting tile
	 * 
	 * @param x x cord
	 * @param y y cord
	 */
	
	/*
	public static void highlightMoves(int x, int y){
		t1 = getTile(grid[x][y]);	
		
			if(canMove(x,y,x+1,y) ==true && x-1 != startX){
				highlightMoves(x+1,y);
				startX = x;
			};
			
			if(canMove(x,y,x,y+1) ==true && y-1 != startY){
				highlightMoves(x,y+1);
				startY = y;
			};
			
			if(x-1 >=0){
				if(canMove(x,y,x-1,y) ==true && x+1 != startX){
					highlightMoves(x-1,y);
					startX = x;
				};
			}
			
			if(y-1 >=0){
				if(canMove(x,y,x,y-1) ==true && y-1 != startY){
					highlightMoves(x,y-1);
					startY = y;
				};
			}
			
	}
	*/
         
	
	/**
	 *returns grid
	 */
	public Tile[][] getGrid(){
		return grid;
	}

	
    
}
