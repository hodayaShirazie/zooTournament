package Animals;

import Graphics.CompetitionPanel;
import Mobility.Point;
import Olympics.Medal;

/**
 * Represents a Cat animal.
 */
public class Cat  extends TerrestrialAnimals{

    /**
     * Indicates whether the cat is castrated.
     */
    private boolean castrated;


    /**
     * Construct a new Cat object with specified values.
     *
     * @param name- Cat name.
     * @param gender- Cat gender: Male, Female or Hermaphrodite.
     * @param weight- Cat weight. if the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed- Cat speed. if the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals- Cat medals- an array of medals that the animal has won. duplicates are removed from the array.
     * @param noLegs- number of legs of Cat. if the number of legs  is less than or equal to 0, a default value of 4 is used.
     * @param castrated- true/false if cat is castrated.
     */
    public Cat(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, CompetitionPanel pan, double noLegs, boolean castrated) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan, noLegs);
        this.castrated = castrated;
        loadImages("cat");
    }

    /**
     * Constructs a new Cat instance with the specified parameters.
     *
     * @param name           The name of the cat.
     * @param speed          The speed of the cat.
     * @param energyPerMeter The energy consumption per meter traveled.
     * @param maxEnergy      The maximum energy the cat can have.
     * @param panel          The competition panel where the cat participates.
     */
    public Cat(String name, int speed,int energyPerMeter, int maxEnergy, CompetitionPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,panel);
        this.castrated = false;

        loadImages("cat");

    }

    /**
     * Construct a new Cat with default values.
     */
    public Cat() {
        super();
        this.castrated = false;
        loadImages("cat");
    }

    /**
     * Produces the sound characteristic of Cat.
     */
    protected void sound()
    {
        System.out.println("Meow");
    }

    /**
     * @return a string which describes the Cat object, including its name, gender, weight, speed, medals, position, total distance, number of legs, is castrated.
     * the function calls TerrestrialAnimals toString function.
     */
    public String toString() {
        if(castrated)
            return super.toString() + "\nis castrated: yes" + "\ntype of animal: Cat";
        return super.toString() + "\nis castrated: no" + "\ntype of animal: Cat";
    }

    /**
     * Check if cat is equal to origin.
     *
     * @param obj- the object to compare with.
     * @return true if cat is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Cat)
            isEqual = super.equals(obj) && castrated == ((Cat)obj).castrated;
        return isEqual;
    }

    /**
     * Returns the type of the animal.
     *
     * @return The type of the animal, which is "Cat".
     */
    public String getType()
    {
        return "Cat";
    }
}
