/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author neal
 */
public class Point2D 
{
    private int x;
    private int y;
	
    public Point2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() 
	{
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) 
	{
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() 
	{
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) 
	{
        this.y = y;
    }
    
    /**
	 * @param e the point2d to compare this to.
	 */
    public boolean equals(Point2D e)
	{
        return (this.getX() == e.getX() && this.getY() == e.getY());
    }
    
	/**
	 * @param e the point2d to find the distance to.
	 */
    public double distance(Point2D e)
	{
        return Math.sqrt((this.getX()-e.getX())*(this.getX()-e.getX()) + (this.getY()-e.getY())*(this.getY()-e.getY()));
    }
	
	/**
	 * Turns the point2d into a string.
	 */
	public String toString()
	{
		return " (X: " + x + ", Y: " + y + ") ";
	}
    
}
