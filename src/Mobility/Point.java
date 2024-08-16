package Mobility;

/**
 * Represents a point.
 */

public class Point implements Cloneable{

    /**
     * The x-coordinate of the point.
     */
    private int x;

    /**
     * The y-coordinate of the point.
     */
    private int y;

    /**
     * Construct a new point with specified values.
     * If either coordinate is negative, it sets the coordinates to (0, 0).
     *
     * @param x value on x-axis.
     * @param y value on y-axis.
     */
    public Point(int x, int y) {
        if(x>= 0 && y>= 0) {
            this.x = x;
            this.y = y;
        }
        else
        {
            this.x = 0;
            this.y = 0;
        }
    }

    /**
     * Construct a new point with default values of (0, 0).
     */
    public Point() {
        x = 0;
        y = 0;
    }

    /**
     * Constructs a new Point with values copied from the specified point.
     *
     * @param point the point to duplicate.
     */
    public Point(Point point) {
        this.x = point.x ;
        this.y = point.y;
    }

    /**
     * Retrieves the current value on the x-axis.
     *
     * @return the value on x-axis.
     */
    public int getX(){return x;}

    /**
     * Retrieves the current value on the y-axis.
     *
     * @return the value on y-axis.
     */
    public int getY(){return y;}

    /**
     *
     * @return a string which describes the point, including its x value and y value.
     */
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    /**
     * Check if point is equal to origin.
     *
     * @param obj- the object to compare with.
     * @return true if point is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Point)

            isEqual = x == ((Point)obj).x && y == ((Point)obj).y;
        return isEqual;
    }

    /**
     * Creates a copy of the current {@code Point} instance.
     *
     * @return A cloned {@code Point} object.
     * @throws AssertionError If cloning fails.
     */
    @Override
    public Point clone() throws AssertionError{
        try{
            return (Point) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }
}