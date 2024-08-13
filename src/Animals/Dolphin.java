package Animals;

import Graphics.CompetitionPanel;
import Graphics.ZooPanel;
import Mobility.Point;
import Olympics.Medal;

/**
 * Represents a Dolphin animal.
 */
public class Dolphin extends WaterAnimal{

    /**
     * The type of water the dolphin lives in.
     */
    private  WaterType waterType;


    public Dolphin(String name, int speed,int energyPerMeter, int maxEnergy,int competitionRoute) {
        super(name, speed, energyPerMeter, maxEnergy,competitionRoute);
        super.setLocation(new Point(80, (competitionRoute - 1)*53 + competitionRoute*60));
        this.waterType = WaterType.Sea;

        loadImages("dolphin");
    }

    /**
     * Construct a new Dolphin object with specified values.
     *
     * @param name Dolphin name.
     * @param gender Dolphin gender: Male, Female or Hermaphrodite.
     * @param weight Dolphin weight. if the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed Dolphin speed. if the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals Dolphin medals- an array of medals that the animal has won. duplicates are removed from the array.
     * @param diveDept the dept of Whale diving. if the dept of diving is above or equal to 0, a default value of -200 is used.
     * @param waterType Dolphin gender: Sea or Sweet.
     */
    public Dolphin(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double diveDept, WaterType waterType, int competitionRoute) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan, diveDept,competitionRoute);
        this.waterType = waterType;
        loadImages("dolphin");
    }

    /**
     * Constructs a new Dolphin object with the specified parameters.
     *
     * @param name            The name of the dolphin.
     * @param speed           The speed of the dolphin.
     * @param energyPerMeter  The amount of energy consumed per meter traveled.
     * @param maxEnergy       The maximum energy the dolphin can have.
     * @param competitionRoute The competition route the dolphin participates in.
     * @param panel           The panel on which the dolphin will be displayed.
     */
    public Dolphin(String name, int speed,int energyPerMeter, int maxEnergy,int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,competitionRoute,panel);
        this.waterType = WaterType.Sea;

        loadImages("dolphin");
    }

    /**
     * Construct a new Dolphin with default values.
     */
    public Dolphin() {
        super();
        this.waterType = WaterType.Sea;
        loadImages("dolphin");
    }

    /**
     * Produces the sound characteristic of Dolphin.
     */
    protected void sound(){
        System.out.println("Click-click");
    }

    /**
     * @return a string which describes the Dolphin object, including its name, gender, weight, speed, medals, position, total distance, dive dept, water type.
     * the function calls WaterAnimal toString function.
     */
    public String toString()
    {
        return super.toString() + "\nwater type: " + waterType + "\ntype of animal: Dolphin";
    }

    /**
     * Check if dolphin is equal to origin.
     *
     * @param obj- the object to compare with.
     * @return true if dolphin is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Dolphin)
            isEqual = super.equals(obj) && waterType == ((Dolphin) obj).waterType;
        return isEqual;
    }

    /**
     * Returns the type of the animal.
     *
     * @return The type of the animal, which is "Dolphin".
     */
    public String getType()
    {
        return "Dolphin";
    }

}
