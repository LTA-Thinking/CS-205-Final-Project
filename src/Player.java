
import javafx.geometry.Point2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author neal
 */
public abstract class Player {

    private Point2D location;
    private Card[] treasures = new Card[24];

    /**
     * set player's location to the input
     *
     * @param location
     */
    public Player(Point2D location) {
        this.location = location;
    }

    /**
     *
     */
    public abstract void takeTurn();

    /**
     * take in a location, return true if a path exist from the current location
     * to the input location, otherwise return false
     *
     * @param location
     * @return
     */
    public boolean pathExists(Point2D location){
        boolean[][] visited = new boolean[7][7];
        return pathExistsHelper(visited, this.location, location);
    }
    
    /**
     * recursive method help pathExists to find if a path exists from one location 
     * to another. 
     * @param visited
     * @param start
     * @param end
     * @return 
     */
    public boolean pathExistsHelper(boolean[][] visited, Point2D start, Point2D end) {
        int x1 = (int) start.getX();
        int y1 = (int) start.getY();
        int x2 = (int) end.getX();
        int y2 = (int) end.getY();

        visited[x1][y1] = true;
        if (y1 < 6) {
            if (!visited[x1][y1 + 1] && board.canMove(start, 'N')) {
                if (x1 == x2 && y2 == y1 + 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (y1 > 1) {
            if (!visited[x1][y1 - 1] && board.canMove(start, 'S')) {
                if (x1 == x2 && y2 == y1 + 1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1, y1 - 1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (x1 > 1) {
            if (!visited[x1][y1 - 1] && board.canMove(start, 'W')) {
                if (x2 == x1 - 1 && y2 == y1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1 - 1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }
        if (y1 < 6) {
            if (!visited[x1 + 1][y1] && board.canMove(start, 'E')) {
                if (x2 == x1 + 1 && y2 == y1) {
                    return true;
                }
                if (pathExistsHelper(visited, new Point2D(x1 + 1, y1), new Point2D(x2, y2))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * get the location of the player
     *
     * @return the location
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     * set the location of the player
     *
     * @param location the location to set
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

}
