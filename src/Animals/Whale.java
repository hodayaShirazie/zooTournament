package Animals;

import Graphics.CompetitionPanel;
import Mobility.Point;
import Olympics.Medal;
import Graphics.ZooPanel;



/**
 * Represents a Whale animal.
 */
public class Whale extends WaterAnimal {

    /**
     * The type of food the Whale consumes.
     */
    private String foodType;


    public Whale(String name, int speed, int energyPerMeter, int maxEnergy, int competitionRoute) {
        super(name, speed, energyPerMeter, maxEnergy, competitionRoute);
        super.setLocation(new Point(80, (competitionRoute - 1)*57 + competitionRoute*55));
        this.foodType = "krill";
        loadImages("whale");
    }

    /**
     * Construct a new Whale object with specified values.
     *
     * @param name     Whale name
     * @param gender   Whale gender: Male, Female or Hermaphrodite.
     * @param weight   Whale weight. if the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed    Whale speed. if the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals   Whale medals- an array of medals that the animal has won. duplicates are removed from the array.
     * @param diveDept the dept of Whale diving. if the dept of diving is above or equal to 0, a default value of -200 is used.
     * @param foodType Whale food type.
     */
    public Whale(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc, int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double diveDept, String foodType, int competitionRoute) {
        super(name, gender, weight, speed, medals, loc, size, id, orientation, maxEnergy, energyPerMeter, pan, diveDept, competitionRoute);
        this.foodType = foodType;
        loadImages("whale");
    }

    /**
     * Constructs a new Whale with the specified attributes.
     *
     * @param name           The name of the whale.
     * @param speed          The speed of the whale.
     * @param energyPerMeter The amount of energy consumed per meter.
     * @param maxEnergy      The maximum energy of the whale.
     * @param competitionRoute The competition route where the whale is located.
     * @param panel          The competition panel on which the whale is displayed.
     */
    public Whale(String name, int speed, int energyPerMeter, int maxEnergy, int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy, competitionRoute,panel);
        this.foodType = "krill";
        loadImages("whale");
    }

    /**
     * Construct a new Snake with Whale values.
     */
    public Whale() {
        super();
        this.foodType = "krill";
        loadImages("whale");
    }

    /**
     * Produces the sound characteristic of Whale.
     */
    protected void sound() {
        System.out.println("Splash");
    }

    /**
     * @return a string which describes the Whale object, including its name, gender, weight, speed, medals, position, total distance, dive dept, food type.
     * the function calls WaterAnimal toString function.
     */
    public String toString() {
        return super.toString() + "\nfood type: " + foodType + "\ntype of animal: Whale";
    }

    /**
     * Check if whale is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if whale is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Whale)
            isEqual = super.equals(obj) && foodType.equals(((Whale) obj).foodType);
        return isEqual;
    }

    /**
     * Returns the type of the animal.
     *
     * @return The type of the animal, which is "Whale".
     */
    public String getType() {
        return "Whale";
    }
}
