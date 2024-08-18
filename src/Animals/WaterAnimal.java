package Animals;

import Mobility.Point;
import Olympics.Medal;
import Graphics.ZooPanel;


/**
 * Represents a water animal.
 * cannot perform instances from this class.
 */
public abstract class WaterAnimal extends Animal {

    /**
     * The current dive depth of the water animal.
     */
    private double diveDept;

    /**
     * The maximum dive depth allowed for any water animal.
     */
    private static final double MAX_DIVE = -800;

    /**
     * The route or path used for competitions involving this water animal.
     */
    private int competitionRoute;

    /**
     * Constructs a new WaterAnimal with specified attributes and a default diving depth of 0.
     *
     * @param name            the name of the water animal
     * @param speed           the speed of the water animal
     * @param energyPerMeter  the energy consumed by the water animal per meter of movement
     * @param maxEnergy       the maximum energy the water animal can have
     * @param competitionRoute the route number for the competition
     */
    public WaterAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute ) {
        super(name, speed, energyPerMeter, maxEnergy);
        setInitialLocation();
//        setLocation(new Point(80,(competitionRoute)*98));

        setDiveDept(0);
        setCompetitionRoute(competitionRoute);
//        canDive = new CanDive(-200);
//        this.canDive.setCompetitionRoute(competitionRoute);

    }

    /**
     * Constructs a new WaterAnimal with specified attributes.
     *
     * @param name            the name of the water animal
     * @param gender          the gender of the water animal
     * @param weight          the weight of the water animal; defaults to 10 if non-positive
     * @param speed           the speed of the water animal; defaults to 10 if non-positive
     * @param medals          an array of medals awarded to the water animal; duplicates are removed
     * @param loc             the initial location of the water animal
     * @param size            the size of the water animal
     * @param id              the unique identifier for the water animal
     * @param orientation     the orientation of the water animal
     * @param maxEnergy       the maximum energy the water animal can have
     * @param energyPerMeter  the energy consumed by the water animal per meter of movement
     * @param pan             the competition panel associated with the water animal
     * @param diveDept        the depth at which the water animal can dive
     * @param competitionRoute the route number for the competition
     */
    public WaterAnimal(String name, Gender gender, double weight, double speed, Medal[] medals, Point loc , int size, int id, Orientation orientation, int maxEnergy, int energyPerMeter, ZooPanel pan, double diveDept,int competitionRoute ) {
        super(name, gender, weight, speed, medals, loc,size, id, orientation, maxEnergy, energyPerMeter, pan);

        setInitialLocation();

        setDiveDept(diveDept);
        setCompetitionRoute(competitionRoute);
//
//
//        canDive = new CanDive();
//
//        canDive = new CanDive(diveDept);
//        this.canDive.setCompetitionRoute(competitionRoute);

//        this.diveDept = diveDept;
    }

    /**
     * Constructs a new WaterAnimal with specified attributes and a default diving depth of 0.
     *
     * @param name            the name of the water animal
     * @param speed           the speed of the water animal
     * @param energyPerMeter  the energy consumed by the water animal per meter of movement
     * @param maxEnergy       the maximum energy the water animal can have
     * @param competitionRoute the route number for the competition
     * @param panel           the competition panel associated with the water animal
     */
    public WaterAnimal(String name, int speed,int energyPerMeter, int maxEnergy, int competitionRoute, ZooPanel panel) {
        super(name, speed, energyPerMeter, maxEnergy,panel);
        setCompetitionRoute(competitionRoute);

        setInitialLocation();

        setDiveDept(0);
//        canDive = new CanDive(-200);
//        this.canDive.setCompetitionRoute(competitionRoute);

    }

    /**
     * Construct a new WaterAnimal with default values.
     */
    public WaterAnimal() {
        super();
        setInitialLocation();
        setDiveDept(0);
        setCompetitionRoute(1);
//        canDive = new CanDive(-200);
//        this.canDive.setCompetitionRoute(1);

//        this.diveDept = -200;
    }

    /**
     * @return a string which describes the WaterAnimal object, including its name, gender, weight, speed, medals, position, total distance, dept of dive.
     * the function calls Animal toString function.
     */
    public String toString() {
        return super.toString() +  "\ndive dept: " + getDiveDept() + "\ncompetition root: " + getCompetitionRoute();
    }

    /**
     * Check if waterAnimal is equal to origin.
     *
     * @param obj the object to compare with.
     * @return true if waterAnimal is equal to origin and false otherwise.
     */
    public boolean equals(Object obj) {
        if(obj instanceof WaterAnimal) {
            return (super.equals(obj) && diveDept ==(((WaterAnimal) obj).diveDept) && competitionRoute == ((WaterAnimal) obj).competitionRoute);

//               return (super.equals(obj) && canDive.equals(((WaterAnimal) obj).canDive));
        }
        return false;
    }

    /**
     * Returns the current competition route for the water animal.
     *
     * @return the competition route
     */
    public int getCompetitionRoute() {
        return competitionRoute;
    }

    /**
     * Sets the competition route for the water animal.
     *
     * @param competitionRoute the new competition route
     */
    public void setCompetitionRoute(int competitionRoute) {
        this.competitionRoute = competitionRoute;
    }

    /**
     * Returns the current dive depth of the water animal.
     *
     * @return the dive depth
     */
    protected double getDiveDept() {
        return diveDept;
    }

    /**
     * Sets the dive depth of the water animal.
     * Adjusts the dive depth if it is within allowed limits.
     *
     * @param diveDept the new dive depth
     * @return {@code true} if the dive depth was set successfully, {@code false} otherwise
     */
    protected boolean setDiveDept(double diveDept) {

        if (diveDept < -800) {
            this.diveDept = -800;
            return false;
        }
        else if(diveDept >= 0) {
            this.diveDept = -200;
            return false;
        }
        else {
            this.diveDept = diveDept;
            return true;}
    }

    /**
     * Adjusts the dive depth of the water animal by adding the specified depth.
     * Ensures the dive depth does not exceed allowed limits.
     *
     * @param dept the depth to add
     * @return {@code true} if the dive depth was set successfully, {@code false} otherwise
     */
    public boolean Dive(double dept)
    {
        return setDiveDept(dept+getDiveDept());
    }

    /**
     * Returns the category of the animal.
     *
     * @return the category "Water"
     */
    @Override
    public String getCategory()
    {
        return "Water";
    }

    /**
     * Calculates the distance from the current location of the water animal
     * to the destination point, which is based on the width of the ZooPanel.
     * The destination is adjusted by subtracting 65 and a percentage of the ZooPanel's width.
     *
     * @return the calculated distance to the destination point
     */
    @Override
    public double getDistance() {
        return super.calcDistancePoint(new Point((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)), getLocationY()));
    }

    /**
     * Sets the initial location of the water animal based on the ZooPanel's dimensions.
     * The initial X-coordinate is a percentage of the panel's width.
     * The Y-coordinate is calculated based on the competition route.
     */
    public void setInitialLocation(){
        super.setLocation(new Point((int)(getZooPanel().getWidth()*0.085), (getZooPanel().getHeight()/9)* (2*getCompetitionRoute() - 1)));

    }

    /**
     * Sets the destination point for the water animal based on the ZooPanel's width.
     * The destination X-coordinate is calculated by subtracting 65 and a percentage of the panel's width.
     * The Y-coordinate remains the same as the current location.
     */
    public void setDestination(){

        super.setDestination(new Point((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)), getLocationY()));

    }

    /**
     * Returns the length of the route for the water animal.
     * @return the length of the route
     */
    public int getLenOfRoute(){
        return (int) ((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)) - (getZooPanel().getWidth()*0.085));
    }

    /**
     * Returns the initial X-coordinate for the water animal based on the ZooPanel's width.
     * The value is a percentage of the panel's width.
     *
     * @return the initial X-coordinate
     */
    public int getXinit(){
        return (int)(getZooPanel().getWidth()*0.085);
    }


}
