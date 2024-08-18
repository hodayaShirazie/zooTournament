package Animals;

import Graphics.CompetitionPanel;
import Graphics.ZooPanel;
import Mobility.Point;
import Olympics.Medal;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Represents a Dog animal.
 */
public class Dog extends TerrestrialAnimals {

    /**
     * The breed of the dog.
     */
    private String breed;

    /**
     * Constructs a Dog object with the specified attributes.
     * Initializes the Dog's breed and loads the images associated with the Dog.
     *
     * @param name           The name of the Dog.
     * @param speed          The speed of the Dog.
     * @param energyPerMeter The energy consumption per meter of the Dog.
     * @param maxEnergy      The maximum energy the Dog can have.
     */
    public Dog(String name, int speed,int energyPerMeter, int maxEnergy) {
        super(name, speed, energyPerMeter, maxEnergy);
        this.breed = "German shepherd";

        loadImages("dog");
    }

    /**
     * Construct a new Dog object with specified values.
     *
     * @param name Dog name.
     * @param gender Dog gender: Male, Female or Hermaphrodite.
     * @param weight Dog weight. if the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed Dog speed. if the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals Dog medals- an array of medals that the animal has won. duplicates are removed from the array.
     * @param noLegs number of legs of Dog. if the number of legs  is less than or equal to 0, a default value of 4 is used.
     * @param breed breed of Dog
     */
    public Dog(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double noLegs, String breed) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan, noLegs);
        this.breed = breed;

        loadImages("dog");
    }

    /**
     * Constructs a new Dog instance.
     *
     * @param name          The name of the dog.
     * @param speed         The speed of the dog.
     * @param energyPerMeter The energy consumed per meter traveled.
     * @param maxEnergy     The maximum energy the dog can have.
     * @param panel         The zoo panel where the dog participates.
     */
    public Dog(String name, int speed,int energyPerMeter, int maxEnergy, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,panel);
        this.breed = "German shepherd";

        loadImages("dog");
    }

    /**
     * Construct a new Dog with default values.
     */
    public Dog() {
        super();
        this.breed = "German shepherd";
        loadImages("dog");
    }

    /**
     * Produces the sound characteristic of Dog.
     */
    protected void sound() {
        System.out.println("Woof Woof");
    }

    /**
     * @return a string which describes the Dog object, including its name, gender, weight, speed, medals, position, total distance, number of legs, breed.
     * the function calls TerrestrialAnimals toString function.
     */
    public String toString() {
        return super.toString() + "\nbreed: " + breed + "\ntype of animal: Dog";
    }

    /**
     * Check if dog is equal to origin.
     *
     * @param obj- the object to compare with.
     * @return true if dog is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Dog)
            isEqual = super.equals(obj) && breed.equals (((Dog) obj).breed);
        return isEqual;
    }

    /**
     * Returns the type of the animal.
     *
     * @return The type of the animal, which is "Dog".
     */
    public String getType()
    {
        return "Dog";
    }

}