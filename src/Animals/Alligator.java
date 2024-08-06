package Animals;

import Mobility.Point;
import Olympics.Medal;
import Graphics.CompetitionPanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


/**
 * The Alligator class represents an alligator with specific attributes and behaviors.
 * It extends the capabilities of its superclass and includes the ability to walk and dive.
 */
public class Alligator extends WaterAnimal implements IReptile, ICanWalk {

    /**
     * The ability of the alligator to walk, represented by the ICanWalk interface.
     */
    private ICanWalk canWalk;

    /**
     * The area where the alligator lives.
     */
    private String areaOfLiving;



    /**
     * Constructs a new Alligator object with specified values.
     *
     * @param name the alligator's name.
     * @param gender the alligator's gender: Male, Female, or Hermaphrodite.
     * @param weight the alligator's weight. If the weight is less than or equal to 0, a default value of 10 is used.
     * @param speed the alligator's speed. If the speed is less than or equal to 0, a default value of 10 is used.
     * @param medals an array of medals that the alligator has won. Duplicates are removed from the array.
     * @param diveDept the depth of the alligator's diving. If the depth of diving is below or equal to 0, a default value of -200 is used.
     * @param areaOfLiving the area where the alligator lives.
     * @param loc the location of the alligator.
     * @param size the size of the alligator.
     * @param id the ID of the alligator.
     * @param orientation the orientation of the alligator.
     * @param maxEnergy the maximum energy of the alligator.
     * @param energyPerMeter the energy consumed per meter by the alligator.
     * @param pan the competition panel.
     * @param competitionRoute the competition route.
     */
    public Alligator(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, CompetitionPanel pan, double diveDept, String areaOfLiving, int competitionRoute) {

        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan, diveDept, competitionRoute);
        canWalk = new CanWalk(6);
        this.areaOfLiving = areaOfLiving;
        loadImages("alligator");

    }

    /**
     * Constructs a new Alligator object with specified values.
     *
     * @param name the alligator's name.
     * @param speed the alligator's speed.
     * @param energyPerMeter the energy consumed per meter by the alligator.
     * @param maxEnergy the maximum energy of the alligator.
     * @param competitionRoute the competition route.
     * @param panel the competition panel.
     */
    public Alligator(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute, CompetitionPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy, competitionRoute, panel);
        canWalk = new CanWalk(6);
        this.areaOfLiving = "desert";

        super.setLocation(new Point(80, (competitionRoute - 1)*57 + competitionRoute*55));
        loadImages("alligator");

    }

    /**
     * Construct a new Alligator with default values.
     */
    public Alligator() {
        super();
        canWalk = new CanWalk(6);
        this.areaOfLiving = "desert";
        loadImages("alligator");
    }

    /**
     * Sets the number of legs to the default value.
     *
     * @return true when the number of legs is set to the default value.
     */
    public boolean setNoLegsToDefault()
    {
        return canWalk.setNoLegsToDefault();
    }

    /**
     * Produces the sound characteristic of Alligator.
     */
    protected void sound(){
        System.out.println("Roar");
    }

    /**
     * @return a string which describes the Alligator object, including its name, gender, weight, speed, medals, position, total distance, dive dept, area of living.
     * the function calls WaterAnimal toString function.
     */
    public String toString() {
        return super.toString() + "\narea of living: " + areaOfLiving + canWalk.toString() + "\ntype of animal: Alligator";
    }

    /**
     * Check if alligator is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if alligator is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Alligator)
            isEqual =  super.equals(obj) && areaOfLiving.equals(((Alligator)obj).areaOfLiving);
        return isEqual;
    }

    /**
     * Increases the current speed of the animal by a specified amount.
     *
     * @param speed the amount to increase the current speed by. must be greater than 0.
     * @return true if the speed was successfully increased and false otherwise.
     */
    public boolean speedUp(int speed) {
        if(speed <= 0)
            return false;

        this.setSpeed(super.getSpeed() + speed);
        return true;
    }

    /**
     * Gets the type of the animal.
     *
     * @return the type of the animal.
     */
    public String getType()
    {
        return "Alligator";
    }

    /**
     * Gets the category of the animal.
     *
     * @return the category of the animal.
     */
    public String getCategory()
    {
        return "Terrestrial+Water";
    }

    /**
     * Makes the alligator dive to a specified depth.
     *
     * @param dept the depth to which the alligator should dive.
     * @return true if the alligator successfully dives, false otherwise.
     */
    public boolean Dive(double dept)
    {
        return super.Dive(dept);
    }

    /**
     * Gets the competition route.
     *
     * @return the competition route.
     */
    public int getCompetitionRoute(){
        return super.getCompetitionRoute();
    }

    /**
     * Sets the competition route.
     *
     * @param competitionRoute the new competition route.
     */
    public void setCompetitionRoute(int competitionRoute){
        super.setCompetitionRoute(competitionRoute);

    }
}
