package Animals;

/**
 * Interface for reptiles.
 */
public interface IReptile {

    /**
     * Maximum speed of a reptile.
     */
    static final int MAX_SPEED = 5;

    /**
     * Increases the speed of the reptile.
     *
     * @param speed The amount to increase the speed by.
     * @return true if the speed was successfully increased, false if the maximum speed was exceeded.
     */
    boolean speedUp(int speed);
}
