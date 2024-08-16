package Graphics;

/**
 * This interface represents an animal with basic capabilities such as moving, eating, and categorization.
 * It extends the {@link IMovable} interface to include movement functionality.
 */
public interface IAnimal extends IMovable{

    /**
     * Allows the animal to eat and gain energy.
     *
     * @param energy the amount of energy the animal gains.
     * @return true if the animal successfully eats and gains energy, false otherwise.
     */
    boolean eat(int energy);

    /**
     * Gets the type of the animal.
     *
     * @return a string representing the type of the animal.
     */
    String getType();

    /**
     * Gets the category of the animal.
     *
     * @return a string representing the category of the animal.
     */
    String getCategory();
}
