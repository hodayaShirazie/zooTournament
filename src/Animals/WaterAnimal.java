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
     * Constructs a {@code WaterAnimal} with the specified parameters.
     *
     * @param name the name of the water animal
     * @param gender the gender of the water animal
     * @param weight the weight of the water animal
     * @param speed the speed of the water animal
     * @param medals an array of medals the water animal has
     * @param loc the initial location of the water animal
     * @param size the size of the water animal
     * @param id the unique identifier for the water animal
     * @param orientation the orientation of the water animal
     * @param maxEnergy the maximum energy of the water animal
     * @param energyPerMeter the energy consumed per meter traveled
     * @param pan the competition panel where the water animal participates
     * @param diveDept the dive depth of the water animal
     * @param competitionRoute the route used in the competition
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
     * Constructs a {@code WaterAnimal} with the specified parameters.
     * This constructor is used for creating a water animal with specific attributes, including competition route and panel.
     *
     * @param name the name of the water animal
     * @param speed the speed of the water animal
     * @param energyPerMeter the energy consumed per meter traveled
     * @param maxEnergy the maximum energy of the water animal
     * @param competitionRoute the route used in the competition
     * @param panel the competition panel where the water animal participates
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
     * Starts moving the water animal towards the specified destination point.
     * This implementation sets the destination based on the current Y-coordinate.
     */
//    @Override
//    public void startMoving() {
//
//        startMoving(new Point((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)), getLocationY()));
//
//    }

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

    public void setLocation(int width, int height){

        double x = (double) getLocationX()/width;
        int newX = (int) (x * getZooPanel().getWidth());

        super.setLocation(new Point(newX, (getZooPanel().getHeight()/9)* (2*getCompetitionRoute() - 1)));

    }

    @Override
    public double getDistance() {
        return super.calcDistancePoint(new Point((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)), getLocationY()));
    }

    public void setInitialLocation(){
        super.setLocation(new Point((int)(getZooPanel().getWidth()*0.085), (getZooPanel().getHeight()/9)* (2*getCompetitionRoute() - 1)));

    }

    public void setDestination(){

        super.setDestination(new Point((getZooPanel().getWidth() - 65 - (int)(getZooPanel().getWidth()*0.085)), getLocationY()));

    }

    public int getLenOfRoute(){//Todo match to every size os screen
        return 755;
    }

    public int getXinit(){
        return (int)(getZooPanel().getWidth()*0.085);
    }


}
