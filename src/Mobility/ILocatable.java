package Mobility;

/**
 * Interface for object that have location
 */
public interface ILocatable {

    /**
     * Retrieves the current location of the object.
     *
     * @return Point-the current location of the object
     */
    Point getLocation();

    /**
     * Sets the location of the object to the given location.
     *
     * @param point the point to set as the new location.
     * @return true if the location was successfully set, otherwise false.
     */
    Boolean setLocation(Point point);

}
