package Graphics;

import Mobility.Point;


/**
 * This interface represents a movable object, typically an animal.
 * It includes methods for getting the animal's name, speed, and moving the animal to a new location.
 */
public interface IMovable {

    /**
     * Gets the name of the animal.
     *
     * @return the name of the animal.
     */
    String getAnimalName();

    /**
     * Gets the speed of the animal.
     *
     * @return the speed of the animal.
     */
    double getSpeed();

    /**
     * Moves the animal to a new location.
     *
     * @param p the new location as a Point object.
     * @return the distance moved as a double.
     */
    double move(Point p);


}
