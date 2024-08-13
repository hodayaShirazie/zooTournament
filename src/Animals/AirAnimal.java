package Animals;

import Graphics.CompetitionPanel;
import Mobility.Point;
import Olympics.Medal;
import Graphics.ZooPanel;

import java.awt.*;

/**
 * Represents an air animal.
 * cannot perform instances from this class.
 */
public abstract class AirAnimal extends Animal {



    /**
     * The wingspan of the air animal.
     */
    private double wingspan;

    /**
     * Represents the route for the competition.
     * This integer value defines the specific route or path that the animal will follow during the competition.
     */
    private int competitionRoute;


    /**
     * Constructs a new AirAnimal with the specified attributes.
     *
     * @param name            the name of the animal
     * @param gender          the gender of the animal
     * @param weight          the weight of the animal; if non-positive, defaults to 10
     * @param speed           the speed of the animal; if non-positive, defaults to 10
     * @param medals          an array of medals awarded to the animal; duplicates are removed
     * @param loc             the initial location of the animal
     * @param size            the size of the animal
     * @param id              the unique identifier for the animal
     * @param orientation     the orientation of the animal
     * @param maxEnergy       the maximum energy the animal can have
     * @param energyPerMeter  the energy consumed by the animal per meter of movement
     * @param pan             the competition panel associated with the animal
     * @param wingspan        the wingspan of the animal; if non-positive, defaults to 30
     * @param competitionRoute the route for the competition
     */
    public AirAnimal(String name, Gender gender, double weight, double speed, Medal[] medals  , Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double wingspan, int competitionRoute) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan);
        setLocation(0,0);
        if(wingspan <= 0)
        {
            this.wingspan = 30;
        }
        else
            this.wingspan = wingspan;
        this.competitionRoute = competitionRoute;
    }

    /**
     * Constructs a new AirAnimal with default values for certain attributes.
     *
     * @param name            the name of the animal
     * @param speed           the speed of the animal; if non-positive, defaults to 10
     * @param energyPerMeter  the energy consumed by the animal per meter of movement
     * @param maxEnergy       the maximum energy the animal can have
     * @param competitionRoute the route for the competition
     * @param panel           the competition panel associated with the animal
     */
    public AirAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,panel);

        this.competitionRoute = competitionRoute;
        super.setLocation(new Point(0, 2*(getPanel().getHeight()/9) * (competitionRoute - 1)));
        this.wingspan = 30;

    }

    public AirAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute ) {
        super(name, speed, energyPerMeter, maxEnergy);

        setLocation(0,0);
        this.wingspan = 30;
        this.competitionRoute = competitionRoute;

    }

    /**
     * Construct a new AirAnimal with default values.
     */
    public AirAnimal() {
        super();
        setLocation(0, 0);
        this.wingspan = 30;
        this.competitionRoute = 1;

    }

    /**
     * @return a string which describes the AirAnimal object, including its name, gender, weight, speed, medals, position, total distance, wingspan
     * the function calls Animal toString function.
     */
    public String toString()
    {
        return super.toString() + "\nwingspan: " + wingspan;
    }

    /**
     * Check if airAnimal is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if airAnimal is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof AirAnimal)
            isEqual =  (super.equals(obj) && wingspan == ((AirAnimal)obj).wingspan);
        return isEqual;
    }

    /**
     * Returns the category of the animal, which is "Air" for this class.
     *
     * @return the category of the animal
     */
    public String getCategory() {
        return "Air";
    }

    /**
     * Starts moving the air animal towards the right edge of the screen.
     * The animal moves towards a point at x-coordinate 900 and its current y-coordinate.
     */
    public void startMoving() {

        startMoving(new Point(getPanel().getWidth() - 65, getLocationY()));

    }

    public void setLocation(int width, int height){

        System.out.println("old width: " + width);
        System.out.println("New width: " + getPanel().getWidth());

        System.out.println("current x" + getLocationX());

        double x = (double) getLocationX()/width;

        System.out.println("x/width:  " + x);

        int newX = (int) (x * getPanel().getWidth());

        System.out.println("new x" + newX);

        super.setLocation(new Point(newX, 2*(getPanel().getHeight()/9) * (competitionRoute - 1)));

    }

}

