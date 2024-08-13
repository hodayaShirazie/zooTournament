package Mobility;

/**
 * Represents object that can move.
 */
public abstract class Mobile implements ILocatable {

    /**
     * The current location of the mobile object.
     */
    private Point location;

    /**
     * The total distance traveled by the mobile object.
     */
    private double totalDistance;

    /**
     * Construct a new Mobile object with specified values.
     *
     * @param location location of the object, represented by a Point. if the point x or y values are negative, sets the coordinates to (0, 0).
     * @param totalDistance distance the object has traveled so far. if distance is less than 0, sets distance to 0.
     */
    public Mobile(Point location, double totalDistance) {
        this.location = new Point(location);
        if(totalDistance < 0)
            totalDistance = 0;
        this.totalDistance = totalDistance;
    }

    /**
     * Construct a new Mobile object with default values.
     */
    public Mobile() {
        this.location = new Point();
        this.totalDistance = 0;
    }

    /**
     * Update the total distance the object has traveled so far.
     *
     * @param movement- the distance the object traveled.
     * @return true if the distance was added successfully, otherwise false.
     */
    private boolean addTotalDistance(double movement) {
        if(movement>0)
        {
            totalDistance += movement;
            return true;
        }
        return false;
    }

    /**
     * Calculate the distance between point and the origin location.
     *
     * @param point the point to calculate the distance to.
     * @return the distance between the object's location and the given point.
     *
     * distance will be non-negative.
     */
    public double calcDistancePoint(Point point) {
        return (Math.sqrt(Math.pow(location.getX()-point.getX(),2) + Math.pow(location.getY()-point.getY(),2)));
    }

    /**
     * Update current location to be the new point and update total distance accordingly.
     *
     * @param point the point to move to.
     * @return the new updated distance.
     *
     * call calcDistancePoint function.
     */
    public double move(Point point) {
        double distance = calcDistancePoint(point);
        addTotalDistance(distance);
        setLocation(point);
        return distance;
    }

    /**
     * @return current location.
     */
    public Point getLocation(){return new Point(location);}

    /**
     * Returns the X coordinate of the location.
     *
     * @return The X coordinate of the location.
     */
    protected int getLocationX(){return location.getX();}

    /**
     * Returns the Y coordinate of the location.
     *
     * @return The Y coordinate of the location.
     */
    protected int getLocationY(){return location.getY();}

    /**
     * Sets the location of the object to the given location.
     *
     * @param location- the point to set as the new location.
     * @return true if the point was successfully set, otherwise false.
     *
     * call setLocation from Point class function.
     */
    public Boolean setLocation(Point location){
        this.location = new Point(location);
        return true;
    }

    /**
     * Retrieves the total distance traveled by the object.
     *
     * @return the total distance the object has traveled.
     */
    public double getTotalDistance(){return totalDistance;}

    /**
     * @return a string which describes the Mobile object, including its location and total distance traveled.
     */
    public String toString() {
        return "location: " + location.toString() + "\ntotal distance: " + totalDistance;
    }

    /**
     * Check if mobile is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if mobile is equal to origin and false otherwise.
     */
    public boolean equals(Object obj){
        if(obj instanceof Mobile) {
            if (location != null && ((Mobile) obj).location != null)
                return location.equals(((Mobile) obj).getLocation()) && totalDistance == ((Mobile) obj).totalDistance;

            else if ((location != null && ((Mobile) obj).location == null) || (location == null && ((Mobile) obj).location != null))
                return false;
            else if (totalDistance != ((Mobile) obj).totalDistance)
                return false;
            return true;
        }
        return false;
    }
}
