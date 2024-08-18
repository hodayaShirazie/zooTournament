package Animals;

import Graphics.CompetitionPanel;
import Mobility.Point;
import Olympics.Medal;
import Graphics.ZooPanel;


/**
 * Represents a Pigeon animal.
 */
public class Pigeon extends AirAnimal {

    /**
     * The family to which the pigeon belongs.
     */
    private String family;

    /**
     * Constructs a Pigeon object with the specified attributes.
     * Initializes the Pigeon's family and loads the images associated with the Pigeon.
     *
     * @param name            The name of the Pigeon.
     * @param speed           The speed of the Pigeon.
     * @param energyPerMeter  The energy consumption per meter of the Pigeon.
     * @param maxEnergy       The maximum energy the Pigeon can have.
     * @param competitionRoute The competition route number, which determines the Pigeon's initial position.
     */
    public Pigeon(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute) {
        super(name, speed, energyPerMeter, maxEnergy, competitionRoute);
        this.family = "Rock dove";
        loadImages("pigeon");

    }

    /**
     * Construct a new Pigeon object with specified values.
     *
     * @param name Pigeon name.
     * @param gender Pigeon gender: Male, Female or Hermaphrodite.
     * @param weight Pigeon weight. if the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed Pigeon speed. if the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals Pigeon medals- an array of medals that the animal has won. duplicates are removed from the array.
     * @param wingspan length of the wingspan of Pigeon. if the wingspan is less than or equal to 0, a default value of 30 is used.
     * @param family Pigeon family.
     */
    public Pigeon(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double wingspan, String family, int competitionRoute) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan,wingspan, competitionRoute);
        this.family = family;
        loadImages("pigeon");
    }

    /**
     * Constructs a new Pigeon instance with the specified attributes.
     *
     * @param name             The name of the pigeon.
     * @param speed            The speed of the pigeon.
     * @param energyPerMeter   The energy consumed by the pigeon per meter of movement.
     * @param maxEnergy        The maximum energy the pigeon can hold.
     * @param competitionRoute The competition route the pigeon will follow.
     * @param panel            The zoo panel where the pigeon is displayed.
     */
    public Pigeon(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy, competitionRoute,panel);
        this.family = "Rock dove";
        loadImages("pigeon");

    }

    /**
     * Construct a new Pigeon with default values.
     */
    public Pigeon() {
        super();
        this.family = "Rock dove";
        loadImages("pigeon");
    }

    /**
     * Produces the sound characteristic of Pigeon.
     */
    protected void sound(){
        System.out.println("Arr-rar-rar-rar-raah");
    }

    /**
     * @return a string which describes the Pigeon object, including its name, gender, weight, speed, medals, position, total distance, wingspan, family.
     * the function calls AirAnimal toString function.
     */
    public String toString()
    {
        return super.toString() + "\nfamily: " + family + "\ntype of animal: Pigeon";
    }

    /**
     * Check if pigeon is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if pigeon is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Pigeon)
            isEqual = super.equals(obj) && family.equals (((Pigeon) obj).family);
        return isEqual;
    }

    /**
     * Returns the type of the animal.
     *
     * @return The type of the animal, which is "Pigeon".
     */
    public String getType()
    {
        return "Pigeon";
    }
}
