package Animals;


/**
 * The CanWalk class represents an animal that can walk.
 * It implements the ICanWalk interface and provides functionality
 * to manage the number of legs the animal has.
 */
public class CanWalk implements ICanWalk {

    /**
     * The number of legs the animal has.
     */
    private double noLegs;

    /**
     * Constructs a CanWalk object with the specified number of legs.
     * If the number of legs is less than 0, it sets the number of legs to the default value.
     *
     * @param noLegs the number of legs the animal has
     */
    public CanWalk(double noLegs) {
        if (noLegs < 0)
            setNoLegsToDefault();
        else
            this.noLegs = noLegs;
    }

    /**
     * Sets the number of legs to the default value.
     *
     * @return true when the number of legs is set to the default value.
     */
    public boolean setNoLegsToDefault() {
        noLegs = 0;
        return true;
    }

    /**
     * Gets the number of legs the animal has.
     *
     * @return the number of legs
     */
    public double getNoLegs() {
        return noLegs;
    }

    /**
     * Compares this CanWalk object to the specified object.
     * The result is true if and only if the argument is not null and is a CanWalk object
     * that has the same number of legs as this object.
     *
     * @param obj the object to compare this CanWalk against
     * @return true if the given object represents a CanWalk object with the same number of legs, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CanWalk) {

            return ((CanWalk) obj).getNoLegs() == ((CanWalk) obj).getNoLegs();
        }
        return false;
    }

    /**
     * Returns a string representation of the CanWalk object.
     *
     * @return a string representation of the CanWalk object
     */
    @Override
    public String toString()
    {
        return "\nnumber of legs: " + getNoLegs();
    }


}

