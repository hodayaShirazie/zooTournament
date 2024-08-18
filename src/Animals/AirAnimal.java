package Animals;

import Mobility.Point;
import Olympics.Medal;
import Graphics.ZooPanel;

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
     * Constructs a new AirAnimal with specified attributes.
     *
     * @param name             the name of the air animal
     * @param gender           the gender of the air animal
     * @param weight           the weight of the air animal; if non-positive, defaults to 10
     * @param speed            the speed of the air animal; if non-positive, defaults to 10
     * @param medals           an array of medals awarded to the air animal; duplicates are removed
     * @param loc              the initial location of the air animal
     * @param size             the size of the air animal
     * @param id               the unique identifier for the air animal
     * @param orientation      the orientation of the air animal
     * @param maxEnergy        the maximum energy the air animal can have
     * @param energyPerMeter   the energy consumed by the air animal per meter of movement
     * @param pan              the competition panel associated with the air animal
     * @param wingspan         the wingspan of the air animal; defaults to 30 if non-positive
     * @param competitionRoute the competition route for the air animal
     */
    public AirAnimal(String name, Gender gender, double weight, double speed, Medal[] medals  , Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double wingspan, int competitionRoute) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan);
//        setLocation(0,0);
        setInitialLocation();

        if(wingspan <= 0)
        {
            this.wingspan = 30;
        }
        else
            this.wingspan = wingspan;
        this.competitionRoute = competitionRoute;
    }

    /**
     * Constructs a new AirAnimal with specified attributes and default wingspan.
     *
     * @param name             the name of the air animal
     * @param speed            the speed of the air animal
     * @param energyPerMeter   the energy consumed by the air animal per meter of movement
     * @param maxEnergy        the maximum energy the air animal can have
     * @param competitionRoute the competition route for the air animal
     * @param panel            the competition panel associated with the air animal
     */
    public AirAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,panel);

        this.competitionRoute = competitionRoute;
        setInitialLocation();
//        super.setLocation(new Point(0, 2*(getPanel().getHeight()/9) * (competitionRoute - 1)));
        this.wingspan = 30;

    }

    /**
     * Constructs a new AirAnimal with specified attributes, default wingspan, and no panel.
     *
     * @param name             the name of the air animal
     * @param speed            the speed of the air animal
     * @param energyPerMeter   the energy consumed by the air animal per meter of movement
     * @param maxEnergy        the maximum energy the air animal can have
     * @param competitionRoute the competition route for the air animal
     */
    public AirAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute ) {
        super(name, speed, energyPerMeter, maxEnergy);

        setInitialLocation();

//        setLocation(0,0);
        this.wingspan = 30;
        this.competitionRoute = competitionRoute;

    }

    /**
     * Construct a new AirAnimal with default values.
     */
    public AirAnimal() {
        super();
//        setLocation(0, 0);
        setInitialLocation();

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
     * Sets the initial location of the AirAnimal on the competition route.
     * This method calculates the starting point of the animal based on the competition route and panel height.
     */
    public void setInitialLocation(){
        super.setLocation(new Point(0, 2*(getZooPanel().getHeight()/9) * (competitionRoute - 1)));

    }

    /**
     * Sets the destination point for the AirAnimal based on the panel width.
     * The destination is the right edge of the panel minus the size of the animal.
     */
    public void setDestination(){

        super.setDestination(new Point(getZooPanel().getWidth() - 65, getLocationY()));

    }

    /**
     * Returns the competition route assigned to the AirAnimal.
     *
     * @return the competition route
     */
    @Override
    public int getCompetitionRoute() {
        return competitionRoute;
    }

    /**
     * Calculates and returns the distance from the AirAnimal's current location to its destination.
     *
     * @return the distance to the destination
     */
    public double getDistance() {

        return super.calcDistancePoint((new Point(getZooPanel().getWidth() - 65, getLocationY())));
    }

    /**
     * Returns the length of the competition route.*
     * @return the length of the route
     */
    public int getLenOfRoute(){
        return getZooPanel().getWidth() - 65;
    }

}

